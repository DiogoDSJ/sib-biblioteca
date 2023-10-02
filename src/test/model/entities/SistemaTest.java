package test.model.entities;

import dao.DAO;
import exceptions.*;
import model.entities.Administrador;
import model.entities.Leitor;
import model.entities.Livro;
import model.entities.Sistema;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class SistemaTest {

    @BeforeEach
    void setUp() {
        DAO.getLeitorDAO().create(new Leitor("Owen Henderson", "Ap #995-7066 Enim. St.", "1-467-895-4705", "Lacota", "XCP46YPN4UG"));
        DAO.getLeitorDAO().create(new Leitor("Breanna Roy", "8718 Neque Avenue", "1-785-316-5008", "Jerry", "GPI21GUN3KD"));
        DAO.getLeitorDAO().create(new Leitor("Ann Serrano", "P.O. Box 324, 1213 Ligula. Road", "1-600-588-1240", "Tara", "ENP57UMR8YS"));
        DAO.getLeitorDAO().create(new Leitor("Lydia Small", "136-1059 In Road", "(441) 366-3492", "Brenna", "CKP12MZT6GD"));
        DAO.getLeitorDAO().create(new Leitor("Rigel Figueroa", "P.O. Box 933, 4116 Molestie Avenue", "1-489-122-8316", "Harper", "RFM71SCI7IA"));
        DAO.getLivroDAO().create(new Livro("7598", "Beau Barrera", "ullamcorper", "Aenean Massa Limited", "Ficção", "2020"));
        DAO.getLivroDAO().create(new Livro("8555", "Jeremy Conner", "posuere", "Proin Mi Industries", "Terror", "2020"));
        DAO.getLivroDAO().create(new Livro("7494", "Hyacinth Carey", "non", "Ligula Donec PC", "Romance", "2020"));
        DAO.getLivroDAO().create(new Livro("4797", "Thaddeus Chang", "Fusce", "Diam Incorporated", "Sci-fi", "2020"));
        DAO.getLivroDAO().create(new Livro("4418", "Brielle Chavez", "placerat", "Rhoncus Nullam LLP", "Comédia", "2020"));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void aplicarMulta() throws usuarioBloqueadoException, objetoInexistenteException, livroReservadoException, foraDeEstoqueException, naoEncontradoException, objetoDuplicadoException {
        Livro livro1 = DAO.getLivroDAO().findByPk("1");
        Livro livro2 = DAO.getLivroDAO().findByPk("2");
        Leitor leitor1 = DAO.getLeitorDAO().findByPk("1");
        Administrador administrador = new Administrador("Lydia Small","136-1059 In Road","(441) 366-3492","Brenna","CKP12MZT6GD");
        assertNotNull(DAO.getReservaDAO().findByIdReservante(leitor1.getId()));
        administrador.fazerEmprestimo(leitor1.getId(), livro1.getIsbn());

        assertNotNull(DAO.getEmprestimoDAO().findByIdMutuario(leitor1.getId()).get(0));
        assertNull(DAO.getMultaDAO().findByIdMutuario(leitor1.getId()));
        DAO.getEmprestimoDAO().findByIdMutuario(leitor1.getId()).get(0).setDataFim(LocalDate.now().plusDays(-5));
        assertNull(DAO.getMultaDAO().findByIdMutuario(leitor1.getId()));
        assertNotNull(DAO.getEmprestimoDAO().findByIdMutuario(leitor1.getId()).get(0));
        Sistema.devolverLivro(leitor1, livro1);
        assertNotNull(DAO.getMultaDAO().findByIdMutuario(leitor1.getId()));

        System.out.println(DAO.getMultaDAO().findByIdMutuario(leitor1.getId()).getDataFim().toString());
        DAO.getMultaDAO().findByIdMutuario(leitor1.getId()).setDataFim(LocalDate.now());
        Sistema.updateMultas();
        assertNull(DAO.getMultaDAO().findByIdMutuario(leitor1.getId()));

        leitor1.fazerReserva(livro1.getIsbn());
        assertNull(DAO.getReservaDAO().findByIdReservante(leitor1.getId()).get(0).getDataFimReserva());
        Sistema.ativarReservasLivros();
        assertEquals(LocalDate.now().plusDays(2), DAO.getReservaDAO().findByIdReservante(leitor1.getId()).get(0).getDataFimReserva());
        System.out.println(DAO.getReservaDAO().findByIdReservante(leitor1.getId()).get(0).getDataFimReserva());
        DAO.getReservaDAO().findByIdReservante(leitor1.getId()).get(0).setDataFimReserva(LocalDate.now().plusDays(-1));
        assertFalse(DAO.getReservaDAO().findByIdReservante(leitor1.getId()).isEmpty());
        Sistema.atualizarReservas();
        assertTrue(DAO.getReservaDAO().findByIdReservante(leitor1.getId()).isEmpty());
    }




}
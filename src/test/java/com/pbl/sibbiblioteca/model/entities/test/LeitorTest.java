package com.pbl.sibbiblioteca.model.entities.test;

import com.pbl.sibbiblioteca.dao.DAO;
import com.pbl.sibbiblioteca.exceptions.*;
import com.pbl.sibbiblioteca.model.entities.Administrador;
import com.pbl.sibbiblioteca.model.entities.Leitor;
import com.pbl.sibbiblioteca.model.entities.Livro;
import com.pbl.sibbiblioteca.model.entities.Sistema;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LeitorTest {

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
        Sistema.updateMultas();
    }

    @Test
    void fazerReserva() throws usuarioBloqueadoException, objetoInexistenteException, foraDeEstoqueException, naoEncontradoException, objetoDuplicadoException, cargoInvalidoException, livroReservadoException, usuarioPendenciasException {
        Livro livro1 = DAO.getLivroDAO().findByPk("1");
        Livro livro2 = DAO.getLivroDAO().findByPk("2");
        Leitor leitor1 = DAO.getLeitorDAO().findByPk("1");

        leitor1.fazerReserva("7598");
        Administrador administrador = new Administrador("Lydia Small","136-1059 In Road","(441) 366-3492","Brenna","CKP12MZT6GD");
        assertNotNull(DAO.getReservaDAO().findByIdReservante(leitor1.getId()));
        administrador.multarLeitor(leitor1, 10);
        assertThrows(usuarioBloqueadoException.class, () -> leitor1.fazerReserva(livro2.getIsbn()));
        assertThrows(objetoDuplicadoException.class, () -> leitor1.fazerReserva("7598"));
        assertNotNull(DAO.getReservaDAO().findByIdReservante(leitor1.getId()));
        leitor1.removerReserva("7598");
        assertTrue(DAO.getReservaDAO().findByIdReservante(leitor1.getId()).isEmpty());
        administrador.fazerEmprestimo(leitor1.getId(), livro1.getIsbn());
        DAO.getEmprestimoDAO().findByIdMutuario(leitor1.getId()).get(0).setDataFim(LocalDate.now().plusDays(1));
        System.out.println(DAO.getEmprestimoDAO().findByIdMutuario(leitor1.getId()).get(0).getDataFim().toString());
        leitor1.renovarEmprestimo(livro1.getIsbn());
        System.out.println(DAO.getEmprestimoDAO().findByIdMutuario(leitor1.getId()).get(0).getDataFim().toString()); // correto pq é sempre o dia atual + 7 dias
        assertEquals(DAO.getEmprestimoDAO().findByIdMutuario(leitor1.getId()).get(0).getDataFim(), LocalDate.now().plusDays(7));

    }

}
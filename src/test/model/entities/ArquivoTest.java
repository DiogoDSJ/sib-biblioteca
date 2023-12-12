package test.model.entities;

import dao.DAO;
import exceptions.*;
import model.entities.Administrador;
import model.entities.Leitor;
import model.entities.Livro;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * O teste é baseado na função TestarDados usado para verificar se há o salvamento de dados, quando a função
 * TestarDados é executada logo após a de LimparDados o teste deve falhar e quando a funcão SalvarDados é executada após
 * CriarDadosArquivo o teste TestarDados deve passar.
 *
 */
class ArquivoTest {

    Administrador administrador;
    Leitor leitor;
    Livro livro;

    @Test
    void CriarDadosArquivo() throws usuarioBloqueadoException, objetoInexistenteException, livroReservadoException, foraDeEstoqueException, naoEncontradoException, objetoDuplicadoException {
        DAO.getBibliotecarioDAO().deleteMany();
        DAO.getLeitorDAO().deleteMany();
        DAO.getAdministradorDAO().deleteMany();
        DAO.getLivroDAO().deleteMany();
        DAO.getEmprestimoDAO().deleteMany();
        DAO.getReservaDAO().deleteMany();
        DAO.getMultaDAO().deleteMany();
        administrador = new Administrador("Fae Das","136-1059 In Road","(441) 366-3492","Faeda","CKP12MZT6GD");
        leitor = new Leitor("Gaewq Figueroa", "P.O. Box 933, 4116 Molestie Avenue", "1-489-122-8316", "Gaeq", "RFM71SCI7IA");
        livro = new Livro("3123", "Caspoq", "ullamcorper", "Aenean Massa Limited", "Ficção", "2020");
        DAO.getAdministradorDAO().create(administrador);
        DAO.getLeitorDAO().create(leitor);
        DAO.getLivroDAO().create(livro);
        administrador.fazerEmprestimo(leitor.getId(), livro.getIsbn());

    }

    @Test
    void TestarDados() {
        assertNotNull(DAO.getAdministradorDAO().findByUsuario("Faeda"));
        assertNotNull(DAO.getLeitorDAO().findByUsuario("Gaeq"));
        assertNotNull(DAO.getLivroDAO().findByIsbn("3123"));
    }

    @Test
    void LimparDados() {
        DAO.getBibliotecarioDAO().deleteMany();
        DAO.getLeitorDAO().deleteMany();
        DAO.getAdministradorDAO().deleteMany();
        DAO.getLivroDAO().deleteMany();
        DAO.getEmprestimoDAO().deleteMany();
        DAO.getReservaDAO().deleteMany();
        DAO.getMultaDAO().deleteMany();
    }
}

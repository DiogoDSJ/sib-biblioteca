package test.model.entities;

import dao.DAO;
import exceptions.naoEncontradoException;
import model.entities.Convidado;
import model.entities.Livro;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConvidadoTest {
    Convidado convidado;
    Livro livro;
    @BeforeEach
    void setUp() {
        convidado = new Convidado();
        this.livro = new Livro("7598", "Beau Barrera", "ullamcorper", "Aenean Massa Limited", "Ficção", "2020");
        DAO.getLivroDAO().create(livro);
    }
    @AfterEach
    void EndUp() {
        DAO.getLivroDAO().deleteMany();
    }

    @Test
    void findByIsbn() throws naoEncontradoException {
        assertEquals(livro, convidado.findByIsbn("7598"));
    }

    @Test
    void findByTitulo() throws naoEncontradoException {
        assertEquals(livro, convidado.findByTitulo("ullamcorper").get(0));
    }

    @Test
    void findByAutor() throws naoEncontradoException {
        assertEquals(livro, convidado.findByAutor("Beau Barrera").get(0));
    }

    @Test
    void findByCategoria() throws naoEncontradoException {
        assertEquals(livro, convidado.findByCategoria("Ficção").get(0));
    }

    @Test
    void findByAnoDePublicao() throws naoEncontradoException {
        assertEquals(livro, convidado.findByAnoDePublicao("2020").get(0));
    }

    @Test
    void findByEditora() throws naoEncontradoException {
        assertEquals(livro, convidado.findByEditora("Aenean Massa Limited").get(0));
    }
}
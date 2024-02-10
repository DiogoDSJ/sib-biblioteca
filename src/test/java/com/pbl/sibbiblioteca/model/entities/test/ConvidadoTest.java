package com.pbl.sibbiblioteca.model.entities.test;

import com.pbl.sibbiblioteca.dao.DAO;
import com.pbl.sibbiblioteca.exceptions.naoEncontradoException;
import com.pbl.sibbiblioteca.model.entities.Convidado;
import com.pbl.sibbiblioteca.model.entities.Livro;
import com.pbl.sibbiblioteca.model.entities.Sistema;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConvidadoTest {
    Livro livro;
    @BeforeEach
    void setUp() {
        DAO.getLivroDAO().deleteMany();
        this.livro = new Livro("7598", "Beau Barrera", "ullamcorper", "Aenean Massa Limited", "Ficção", "2020");
        DAO.getLivroDAO().create(livro);
    }
    @AfterEach
    void EndUp() {
        DAO.getLivroDAO().deleteMany();
    }

    @Test
    void findByIsbn() throws naoEncontradoException {
        assertEquals(livro, Sistema.findByIsbn("7598"));
    }

    @Test
    void findByTitulo() throws naoEncontradoException {
        assertEquals(livro, Sistema.findByTitulo("ullamcorper").get(0));
    }

    @Test
    void findByAutor() throws naoEncontradoException {
        assertEquals(livro, Sistema.findByAutor("Beau Barrera").get(0));
    }

    @Test
    void findByCategoria() throws naoEncontradoException {
        assertEquals(livro, Sistema.findByCategoria("Ficção").get(0));
    }

    @Test
    void findByAnoDePublicao() throws naoEncontradoException {
        assertEquals(livro, Sistema.findByAnoDePublicao("2020").get(0));
    }

    @Test
    void findByEditora() throws naoEncontradoException {
        assertEquals(livro, Sistema.findByEditora("Aenean Massa Limited").get(0));
    }
}
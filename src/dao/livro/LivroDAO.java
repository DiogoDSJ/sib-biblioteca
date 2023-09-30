package dao.livro;

import dao.CRUD;
import model.entities.Livro;

import java.util.List;

public interface LivroDAO extends CRUD<Livro> {

    Livro findByIsbn(String isbn);

    List<Livro> findByTitulo(String titulo);

    List<Livro> findByAutor(String autor);

    List<Livro> findByCategoria(String categoria);

    List<Livro> findByAnoDePublicao(String anoDePublicacao);

    List<Livro> findByEditora(String editora);
}
package dao.livro;

import dao.CRUD;
import model.entities.Livro;

public interface LivroDAO extends CRUD<Livro> {

    public Livro findByIsbn(String isbn);
    public Livro findByTitulo(String titulo);

}

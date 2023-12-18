package com.pbl.sibbiblioteca.dao.livro;

import com.pbl.sibbiblioteca.dao.CRUD;
import com.pbl.sibbiblioteca.model.entities.Livro;

import java.util.List;

/**
 * Interface criada para implementar o armazenamento dos objetos livro, usando a interface CRUD.
 */
public interface LivroDAO extends CRUD<Livro> {
    /**
     * Encontra um livro pelo seu ISBN
     * @param isbn isbn do livro.
     * @return retorna o livro encontrado.
     */
    Livro findByIsbn(String isbn);

    /**
     * Encontra todos os livros que contenham no seu atributo de titulo a string passada como parametro.
     * @param titulo Titulo do livro que é usada como paramêtro de busca.
     * @return Lista de todos os livros encontrados.
     */
    List<Livro> findByTitulo(String titulo);

    /**
     * Encontra todos os livros que contenham no seu atributo de autor a string passada como parametro.
     * @param autor autor do livro que é usado como paramêtro de busca.
     * @return Lista de todos os livros encontrados.
     */
    List<Livro> findByAutor(String autor);

    /**
     * Encontra todos os livros que contenham no seu atributo de categoria a string passada como parametro.
     * @param categoria categoria do livro que é usada como paramêtro de busca.
     * @return Lista de todos os livros encontrados.
     */
    List<Livro> findByCategoria(String categoria);

    /**
     * Encontra todos os livros que contenham no seu atributo de anoDePublicacao a string passada como parametro.
     * @param anoDePublicacao anoDePublicacao do livro que é usada como paramêtro de busca.
     * @return Lista de todos os livros encontrados.
     */
    List<Livro> findByAnoDePublicao(String anoDePublicacao);

    /**
     * Encontra todos os livros que contenham no seu atributo de editora a string passada como parametro.
     * @param editora editora do livro que é usada como paramêtro de busca.
     * @return Lista de todos os livros encontrados.
     */
    List<Livro> findByEditora(String editora);
}
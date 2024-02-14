package com.pbl.sibbiblioteca.model.entities;

import com.pbl.sibbiblioteca.dao.DAO;
import com.pbl.sibbiblioteca.exceptions.foraDeEstoqueException;

import java.io.Serializable;

/**
 * Classe livro que guarda as informações do id do livro no DAO, seu isbn, autor, título, editora, categoria,
 * ano de publicação e quantidade do livro no estoque.
 */
public class Livro implements Serializable {

    private String id;
    private String isbn;
    private String autor;
    private String titulo;
    private String editora;
    private String categoria;
    private String anoDePublicacao;
    private String quantidade;

    /**
     * Construtor padrão da classe livro, o id é definido pelo DAO e a quantidade inicial é sempre 1.
     * @param isbn Isbn do livro a ser implementado.
     * @param autor Autor do livro a ser implementado.
     * @param titulo Titulo do livro a ser implementado.
     * @param editora Editora do livro a ser implementado.
     * @param categoria Categoria do livro a ser implementado.
     * @param anoDePublicacao Ano de publicacao do livro a ser implementado.
     */
    public Livro(String isbn, String autor, String titulo, String editora, String categoria, String anoDePublicacao) {
        this.isbn = isbn;
        this.autor = autor;
        this.titulo = titulo;
        this.editora = editora;
        this.categoria = categoria;
        this.anoDePublicacao = anoDePublicacao;
        this.quantidade = "1";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getQuantidade() {
        return quantidade;
    }

    /**
     * Adiciona uma quantidade de livro no estoque.
     */
    public void adicionarUmaUnidade() {
        int quantidadeInt = Integer.parseInt(this.quantidade);
        quantidadeInt++;
        this.quantidade = Integer.toString(quantidadeInt);
        DAO.getLivroDAO().update(DAO.getLivroDAO().findByIsbn(this.getIsbn()));
    }

    /**
     * Remove uma quantidade de livro no estoque.
     * @throws foraDeEstoqueException caso alguem tente remover uma unidade com o estoque já em zero.
     */
    public void removerUmaUnidade() throws foraDeEstoqueException {
        int quantidadeInt = Integer.parseInt(this.quantidade);
        if (quantidadeInt == 0) {
            throw new foraDeEstoqueException("Não há unidades desse livro");
        }
        quantidadeInt--;
        this.quantidade = Integer.toString(quantidadeInt);
        DAO.getLivroDAO().update(DAO.getLivroDAO().findByIsbn(this.getIsbn()));
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getAnoDePublicacao() {
        return anoDePublicacao;
    }

    public void setAnoDePublicacao(String anoDePublicacao) {
        this.anoDePublicacao = anoDePublicacao;
    }
}

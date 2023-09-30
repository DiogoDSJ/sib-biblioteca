package model.entities;

import exceptions.foraDeEstoqueException;

public class Livro {

    private String id;
    private String isbn;
    private String autor;
    private String titulo;
    private String editora;
    private String categoria;

    private String quantidade;

    public Livro() {
    }

    public Livro(String isbn, String autor, String titulo, String editora, String categoria) {
        this.isbn = isbn;
        this.autor = autor;
        this.titulo = titulo;
        this.editora = editora;
        this.categoria = categoria;
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
    public void adicionarUmaUnidade() {
        int quantidadeInt = Integer.parseInt(this.quantidade);
        quantidadeInt++;
        this.quantidade = Integer.toString(quantidadeInt);
    }

    public void removerUmaUnidade() throws foraDeEstoqueException {
        int quantidadeInt = Integer.parseInt(this.quantidade);
        if(quantidadeInt == 0) {
            throw new foraDeEstoqueException("Não há unidades desse livro");
        }
        quantidadeInt --;
        this.quantidade = Integer.toString(quantidadeInt);
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
}

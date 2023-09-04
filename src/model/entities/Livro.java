package model.entities;

public class Livro {

    private String isbn;
    private String autor;
    private String titulo;
    private String editora;
    private String categoria;

    public Livro() {
    }

    public Livro(String isbn, String autor, String titulo, String editora, String categoria) {
        this.isbn = isbn;
        this.autor = autor;
        this.titulo = titulo;
        this.editora = editora;
        this.categoria = categoria;
    }


}

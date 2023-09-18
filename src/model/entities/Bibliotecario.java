package model.entities;

import dao.DAO;
import model.entities.enums.Cargo;


public class Bibliotecario extends Usuario {

    public Bibliotecario(String nome, String endereco, String telefone, String usuario, String senhaDeAcesso) {
        super(nome, endereco, telefone, usuario, senhaDeAcesso);
        this.setCargo(Cargo.BIBLIOTECARIO);
    }

    public void adicionarLivro(String isbn, String autor, String titulo, String editora, String categoria){
        if(DAO.getLivroDAO().findByIsbn(isbn) == null && DAO.getLivroDAO().findByTitulo(titulo) == null)
        {
            DAO.getLivroDAO().create(new Livro(isbn, autor, titulo, editora, categoria));
        }
        else if(DAO.getLivroDAO().findByIsbn(isbn) == DAO.getLivroDAO().findByTitulo(titulo)) // verificar se todas as informaçoes do livro sao iguais ao qual ja existe, se for, adicionar mais um na quantidade
        {
            DAO.getLivroDAO().findByIsbn(isbn).adicionarUmaUnidade();
        }
    }

    public void removerLivro(String isbn){
        if(DAO.getEmprestimoDAO().findByIsbn(isbn).isEmpty()) {
            DAO.getLivroDAO().delete(DAO.getLivroDAO().findByIsbn(isbn));
        }
    }

    public void fazerEmprestimo(String idMutuario, String isbnLivro){
        if(DAO.getLeitorDAO().findByPk(idMutuario).getNumeroDeEmprestimos() > 0 && (DAO.getLivroDAO().findByIsbn(isbnLivro) != null && !DAO.getLivroDAO().findByIsbn(isbnLivro).getQuantidade().equals("0"))) {
            DAO.getEmprestimoDAO().create(new Emprestimo(idMutuario, isbnLivro));
        }
        else{
            System.out.println("Falha");
        }
    }

}

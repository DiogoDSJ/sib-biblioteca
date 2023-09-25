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

    public boolean fazerEmprestimo(String idMutuario, String isbnLivro){
        Leitor leitor = DAO.getLeitorDAO().findByPk(idMutuario);
        Livro livro = DAO.getLivroDAO().findByIsbn(isbnLivro);
        Sistema.updateMultas();
        if(Sistema.checarSeHaAtrasoLeitor(leitor) || DAO.getMultaDAO().findByIdMutuario(idMutuario) != null){
            Sistema.aplicarMulta(leitor);
            return false;
        }

        if(leitor.getNumeroDeEmprestimos() > 0 && (livro != null && !livro.getQuantidade().equals("0"))) {
            Emprestimo emprestimo = new Emprestimo(idMutuario, isbnLivro);
            DAO.getEmprestimoDAO().create(emprestimo);
            DAO.getLeitorDAO().findByPk(idMutuario).adicionarEmprestimoNoHistorico(emprestimo);
            return true;
        }
        else{
            return false;
            //System.out.println("Falha");
        }
    }

}

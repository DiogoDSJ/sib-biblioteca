package model.entities;

import dao.DAO;
import exceptions.*;
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

    public void removerLivro(String isbn) throws livroEmprestadoException{
        if(DAO.getEmprestimoDAO().findByIsbn(isbn).isEmpty()) {
            DAO.getLivroDAO().delete(DAO.getLivroDAO().findByIsbn(isbn));
        }
        else throw new livroEmprestadoException("Livro está emprestado.");
    }

    public void fazerEmprestimo(String idMutuario, String isbnLivro) throws naoEncontradoException, foraDeEstoqueException, usuarioBloqueadoException, livroReservadoException {
        Leitor leitor = DAO.getLeitorDAO().findByPk(idMutuario);
        Livro livro = DAO.getLivroDAO().findByIsbn(isbnLivro);
        if (leitor == null) {
            throw new naoEncontradoException("Leitor não existe.");
        } else if (livro == null) {
            throw new naoEncontradoException("Livro não existe.");
        }
        Sistema.updateMultas(); // colocar no beforeEach || Atualizo as multas para remover multas que já foram pagas e não atrapalha na checagem de atraso
        if (Sistema.checarSeHaAtrasoLeitor(leitor)) { // dois casos : ele já esta multado ou precisa ser multado.
            throw new usuarioBloqueadoException("Usuário em atraso.");
        } else if (livro.getQuantidade().equals("0")) {
            throw new foraDeEstoqueException("Não há estoque disponível para esse livro.");
        } else if (leitor.getNumeroDeEmprestimos() == 0) {
            throw new foraDeEstoqueException("Usuário alcançou o máximo de livros.");
        } else if (!Sistema.checarSeAReservaDoUsuarioOPermitePegarOLivro(leitor, livro)) { // aqui eu tenho o livro em estoque e checo se o usuário tem reserva
            throw new livroReservadoException("Livro está reservado para outro usuário.");
        }
        Emprestimo emprestimo = new Emprestimo(idMutuario, isbnLivro);
        DAO.getEmprestimoDAO().create(emprestimo);
        leitor.adicionarEmprestimoNoHistorico(emprestimo);
        leitor.removerUmEmprestimo();
    }

    public void fazerReserva(String idReservante, String isbnLivro) throws naoEncontradoException, usuarioBloqueadoException, foraDeEstoqueException{
        Leitor leitor = DAO.getLeitorDAO().findByPk(idReservante);
        Livro livro = DAO.getLivroDAO().findByIsbn(isbnLivro);
        if (leitor == null) {
            throw new naoEncontradoException("Leitor não existe.");
        }
        else if (livro == null) {
            throw new naoEncontradoException("Livro não existe.");
        }
        else if(leitor.getNumeroDeReservas() == 0){
            throw new foraDeEstoqueException("Usuário alcançou o máximo de reservas.");
        }
        Sistema.updateMultas(); // colocar no beforeEach || Atualizo as multas para remover multas que já foram pagas e não atrapalha na checagem de atraso
        if (Sistema.checarSeHaAtrasoLeitor(leitor)) { // dois casos : ele já esta multado ou precisa ser multado.
            throw new usuarioBloqueadoException("Usuário em atraso.");
        }
        Reserva reserva = new Reserva(idReservante, isbnLivro);
        DAO.getReservaDAO().create(reserva);
        leitor.adicionarUmaReserva();
    }

    public void trocarIsbnLivro(String isbn, String novoisbn) throws objetoInexistenteException {
        Livro livro = DAO.getLivroDAO().findByIsbn(isbn);
        if (livro == null) throw new objetoInexistenteException("Livro não existe.");
        livro.setIsbn(novoisbn);
        DAO.getLivroDAO().update(livro);
    }

    public void trocarAutorLivro(String isbn, String novoautor) throws objetoInexistenteException {
        Livro livro = DAO.getLivroDAO().findByIsbn(isbn);
        if (livro == null) throw new objetoInexistenteException("Livro não existe.");
        livro.setAutor(novoautor);
        DAO.getLivroDAO().update(livro);
    }

    public void trocarTituloLivro(String isbn, String novotitulo) throws objetoInexistenteException {
        Livro livro = DAO.getLivroDAO().findByIsbn(isbn);
        if (livro == null) throw new objetoInexistenteException("Livro não existe.");
        livro.setTitulo(novotitulo);
        DAO.getLivroDAO().update(livro);
    }

    public void trocarEditoraLivro(String isbn, String novaeditora) throws objetoInexistenteException {
        Livro livro = DAO.getLivroDAO().findByIsbn(isbn);
        if (livro == null) throw new objetoInexistenteException("Livro não existe.");
        livro.setEditora(novaeditora);
        DAO.getLivroDAO().update(livro);
    }

    public void trocarCategoriaLivro(String isbn, String novacategoria) throws objetoInexistenteException {
        Livro livro = DAO.getLivroDAO().findByIsbn(isbn);
        if (livro == null) throw new objetoInexistenteException("Livro não existe.");
        livro.setCategoria(novacategoria);
        DAO.getLivroDAO().update(livro);
    }

}

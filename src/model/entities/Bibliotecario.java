package model.entities;

import dao.DAO;
import exceptions.*;
import model.entities.enums.Cargo;


public class Bibliotecario extends Usuario {

    public Bibliotecario(String nome, String endereco, String telefone, String usuario, String senhaDeAcesso) {
        super(nome, endereco, telefone, usuario, senhaDeAcesso);
        this.setCargo(Cargo.BIBLIOTECARIO);
    }

    public void adicionarLivro(String isbn, String autor, String titulo, String editora, String categoria, String ano){
        if(DAO.getLivroDAO().findByIsbn(isbn) == null && DAO.getLivroDAO().findByTitulo(titulo) == null)
        {
            DAO.getLivroDAO().create(new Livro(isbn, autor, titulo, editora, categoria, ano));
        }
        else if(DAO.getLivroDAO().findByIsbn(isbn) == DAO.getLivroDAO().findByTitulo(titulo)) // verificar se todas as informaçoes do livro sao iguais ao qual ja existe, se for, adicionar mais um na quantidade
        {
            DAO.getLivroDAO().findByIsbn(isbn).adicionarUmaUnidade();
        }
    }

    public void removerLivro(String isbn) throws livroEmprestadoException, naoEncontradoException{
        if(DAO.getLivroDAO().findByIsbn(isbn) == null) throw new naoEncontradoException("Livro não existe");
        else if(!DAO.getEmprestimoDAO().findByIsbn(isbn).isEmpty()) {
            throw new livroEmprestadoException("Livro está emprestado.");
        }
        else if(Sistema.checarSeOLivroFoiReservado(isbn)) {
            throw new livroEmprestadoException("Livro está reservado.");
        }
        else{
            DAO.getLivroDAO().delete(DAO.getLivroDAO().findByIsbn(isbn));
        }
    }

    public void fazerEmprestimo(String idMutuario, String isbnLivro) throws naoEncontradoException, objetoInexistenteException, foraDeEstoqueException, usuarioBloqueadoException, livroReservadoException, objetoDuplicadoException {
        Leitor leitor = DAO.getLeitorDAO().findByPk(idMutuario);
        Livro livro = DAO.getLivroDAO().findByIsbn(isbnLivro);
        if (leitor == null) {
            throw new naoEncontradoException("Leitor não existe.");
        }
        else if (livro == null) {
            throw new naoEncontradoException("Livro não existe.");
        }
        Sistema.updateMultas(); // colocar no beforeEach || Atualizo as multas para remover multas que já foram pagas e não atrapalha na checagem de atraso
        if (Sistema.checarSeHaAtrasoLeitor(leitor)) { // dois casos : ele já esta multado ou precisa ser multado.
            throw new usuarioBloqueadoException("Usuário em atraso.");
        }
        else if (livro.getQuantidade().equals("0")) {
            throw new foraDeEstoqueException("Não há estoque disponível para esse livro.");
        }
        else if (leitor.getNumeroDeEmprestimos() == 0) {
            throw new foraDeEstoqueException("Usuário alcançou o máximo de livros.");
        }
        else if(Sistema.checarSeOUsuarioTemOLivro(leitor, isbnLivro)) throw new objetoDuplicadoException("Usuário não pode ter dois livros iguais.");
        else if (!Sistema.checarSeAReservaDoUsuarioOPermitePegarOLivro(leitor, livro)) { // aqui eu tenho o livro em estoque e checo se o usuário tem reserva
            throw new livroReservadoException("Livro está reservado para outro usuário.");
        }
        Emprestimo emprestimo = new Emprestimo(idMutuario, isbnLivro);
        DAO.getEmprestimoDAO().create(emprestimo);
        leitor.adicionarEmprestimoNoHistorico(emprestimo);
        leitor.removerUmEmprestimo();
    }

    public void fazerReserva(String idReservante, String isbnLivro) throws objetoInexistenteException, objetoDuplicadoException, naoEncontradoException, usuarioBloqueadoException, foraDeEstoqueException{
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
        else if(Sistema.checarSeOUsuarioTemOLivro(leitor, isbnLivro)) throw new objetoDuplicadoException("Usuário não pode ter dois livros iguais.");
        else if(Sistema.checarSeOUsuarioReservouOLivro(leitor, isbnLivro)) throw new objetoDuplicadoException("Usuário não pode reservar outro livro igual.");
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

    public void trocarAnoLivro(String isbn, String novoano) throws objetoInexistenteException {
        Livro livro = DAO.getLivroDAO().findByIsbn(isbn);
        if (livro == null) throw new objetoInexistenteException("Livro não existe.");
        livro.setCategoria(novoano);
        DAO.getLivroDAO().update(livro);
    }
}

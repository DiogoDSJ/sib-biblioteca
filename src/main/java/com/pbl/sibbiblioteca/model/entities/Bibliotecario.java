package com.pbl.sibbiblioteca.model.entities;

import com.pbl.sibbiblioteca.dao.DAO;
import com.pbl.sibbiblioteca.exceptions.*;
import com.pbl.sibbiblioteca.model.entities.enums.Cargo;

/**
 * Sub-Classe de usuário, o bibliotecário que será usado para fazer as solicitações de empréstimo e de manipulação de
 * livros.
 */
public class Bibliotecario extends Usuario {

    /**
     * Construtor padrão de bibliotecário.
     *
     * Aqui é utilizado o construtor padrão de usúario, tendo somente a diferença do cargo em que está sendo colocado.
     *
     * @param nome Nome do bibliotecário
     * @param endereco Endereço do bibliotecário
     * @param telefone Telefone do bibliotecário
     * @param usuario Usuário do bibliotecário
     * @param senhaDeAcesso Senha do bibliotecário
     */
    public Bibliotecario(String nome, String endereco, String telefone, String usuario, String senhaDeAcesso) {
        super(nome, endereco, telefone, usuario, senhaDeAcesso);
        this.setCargo(Cargo.BIBLIOTECARIO);
    }

    /**
     * Função que recebe os dados de um livro e cria um objeto no DAO, caso o livro não exista ele é lançado no DAO,
     * caso exista ele é adicionado um na sua quantidade.
     * @param isbn ISBN do livro.
     * @param autor Autor do livro.
     * @param titulo Título do livro.
     * @param editora Editora do livro.
     * @param categoria Categoria do livro.
     * @param ano Ano do livro.
     */
    public int adicionarLivro(String isbn, String autor, String titulo, String editora, String categoria, String ano){
        if(DAO.getLivroDAO().findByIsbn(isbn) == null)
        {
            DAO.getLivroDAO().create(new Livro(isbn, autor, titulo, editora, categoria, ano));
            return 1;
        }
        else if(DAO.getLivroDAO().findByIsbn(isbn)!= null)
        {
            DAO.getLivroDAO().findByIsbn(isbn).adicionarUmaUnidade();
            return 0;
        }
        return -1;
    }

    /**
     * Função que tenta remover o livro do DAO.
     * @param isbn ISBN do livro que será removido.
     * @throws livroEmprestadoException Sé o livro estiver emprestado ou reservado à um leitor.
     * @throws naoEncontradoException Sé o livro não existir.
     */
    public void removerLivro(String isbn) throws livroEmprestadoException, livroReservadoException, naoEncontradoException, foraDeEstoqueException {
        Livro livro = DAO.getLivroDAO().findByIsbn(isbn);
        if(livro == null) throw new naoEncontradoException("Livro não existe");
        else if(!DAO.getEmprestimoDAO().findByIsbn(isbn).isEmpty()) {
            throw new livroEmprestadoException("Livro está emprestado.");
        }
        else if(Sistema.checarSeOLivroFoiReservado(isbn)) {
            throw new livroReservadoException("Livro está reservado.");
        }
        else{
            DAO.getLivroDAO().delete(DAO.getLivroDAO().findByIsbn(isbn));
            throw new foraDeEstoqueException("O livro foi removido");
        }
    }

    /**
     * Aqui é função de empréstimo que lança no DAO de emprestimo o objeto empréstimo se ele for bem-sucedido na sua
     * criação.
     * @param idMutuario ID do leitor que pegar o livro emprestado.
     * @param isbnLivro ISBN do livro que será emprestado.
     * @throws naoEncontradoException Se o livro não existir ou não for encontrado.
     * @throws objetoInexistenteException Se o leitor não existir.
     * @throws foraDeEstoqueException Se o usuário alcançou o máximo de livro ou está sem estoque de empréstimos.
     * @throws usuarioBloqueadoException Se o usuário estiver em atraso ou multado.
     * @throws livroReservadoException Se o livro foi reservado para outra pessoa.
     * @throws objetoDuplicadoException Se o usuário já estiver com outra unidade do livro em outro empréstimo.
     */
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
        else if (leitor.getNumeroDeEmprestimos().equals("0")) {
            throw new foraDeEstoqueException("Usuário alcançou o máximo de livros.");
        }
        else if(Sistema.checarSeOUsuarioTemOLivro(leitor, isbnLivro)) throw new objetoDuplicadoException("Usuário não pode ter dois livros iguais.");
        else if (Sistema.checarSeAReservaDoUsuarioOPermitePegarOLivro(leitor, livro)) { // aqui eu tenho o livro em estoque e checo se o usuário tem reserva
            throw new livroReservadoException("Livro está reservado para outro usuário.");
        }
        Emprestimo emprestimo = new Emprestimo(idMutuario, isbnLivro);
        DAO.getEmprestimoDAO().create(emprestimo);
        leitor.adicionarEmprestimoNoHistorico(emprestimo);
        if (Sistema.checarSeOUsuarioReservouOLivro(leitor, livro.getIsbn())){
            leitor.removerReserva(livro.getIsbn());
        }
        DAO.getLeitorDAO().update(leitor);
    }

    /**
     * Aqui o ISBN de um livro é trocado para outro novo ISBN.
     * @param isbn ISBN atual que será usado na busca do livro.
     * @param novoisbn Novo ISBN que será colocado no livro.
     * @throws objetoInexistenteException Se o livro não existir ou não for encontrado.
     */
    public void trocarIsbnLivro(String isbn, String novoisbn) throws objetoInexistenteException, objetoDuplicadoException {
        Livro livro = DAO.getLivroDAO().findByIsbn(isbn);
        if (livro == null) throw new objetoInexistenteException("Livro não existe.");
        else if (DAO.getLivroDAO().findByIsbn(novoisbn) != null) throw new objetoDuplicadoException("Já existe um livro com esse ISBN.");
        livro.setIsbn(novoisbn);
        DAO.getLivroDAO().update(livro);
    }

    /**
     * Aqui o autor de um livro é trocado para outro novo autor.
     * @param isbn ISBN atual que será usado na busca do livro.
     * @param novoautor Novo autor que será colocado no livro.
     * @throws objetoInexistenteException Se o livro não existir ou não for encontrado.
     */
    public void trocarAutorLivro(String isbn, String novoautor) throws objetoInexistenteException {
        Livro livro = DAO.getLivroDAO().findByIsbn(isbn);
        if (livro == null) throw new objetoInexistenteException("Livro não existe.");
        livro.setAutor(novoautor);
        DAO.getLivroDAO().update(livro);
    }

    /**
     * Aqui o titulo de um livro é trocado para outro novo titulo.
     * @param isbn ISBN atual que será usado na busca do livro.
     * @param novotitulo Novo titulo que será colocado no livro.
     * @throws objetoInexistenteException Se o livro não existir ou não for encontrado.
     */
    public void trocarTituloLivro(String isbn, String novotitulo) throws objetoInexistenteException {
        Livro livro = DAO.getLivroDAO().findByIsbn(isbn);
        if (livro == null) throw new objetoInexistenteException("Livro não existe.");
        livro.setTitulo(novotitulo);
        DAO.getLivroDAO().update(livro);
    }

    /**
     * Aqui o editora de um livro é trocado para outro nova editora.
     * @param isbn ISBN atual que será usado na busca do livro.
     * @param novaeditora Nova editora que será colocado no livro.
     * @throws objetoInexistenteException Se o livro não existir ou não for encontrado.
     */
    public void trocarEditoraLivro(String isbn, String novaeditora) throws objetoInexistenteException {
        Livro livro = DAO.getLivroDAO().findByIsbn(isbn);
        if (livro == null) throw new objetoInexistenteException("Livro não existe.");
        livro.setEditora(novaeditora);
        DAO.getLivroDAO().update(livro);
    }

    /**
     * Aqui o categoria de um livro é trocado para outro nova categoria.
     * @param isbn ISBN atual que será usado na busca do livro.
     * @param novacategoria Nova categoria que será colocado no livro.
     * @throws objetoInexistenteException Se o livro não existir ou não for encontrado.
     */
    public void trocarCategoriaLivro(String isbn, String novacategoria) throws objetoInexistenteException {
        Livro livro = DAO.getLivroDAO().findByIsbn(isbn);
        if (livro == null) throw new objetoInexistenteException("Livro não existe.");
        livro.setCategoria(novacategoria);
        DAO.getLivroDAO().update(livro);
    }

    /**
     * Aqui o ano de um livro é trocado para outro ano.
     * @param isbn ISBN atual que será usado na busca do livro.
     * @param novoano Nova ano que será colocado no livro.
     * @throws objetoInexistenteException Se o livro não existir ou não for encontrado.
     */
    public void trocarAnoLivro(String isbn, String novoano) throws objetoInexistenteException {
        Livro livro = DAO.getLivroDAO().findByIsbn(isbn);
        if (livro == null) throw new objetoInexistenteException("Livro não existe.");
        livro.setCategoria(novoano);
        DAO.getLivroDAO().update(livro);
    }
}

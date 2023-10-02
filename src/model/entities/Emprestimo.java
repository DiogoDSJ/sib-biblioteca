package model.entities;

import dao.DAO;
import exceptions.foraDeEstoqueException;
import exceptions.objetoInexistenteException;
import exceptions.usuarioBloqueadoException;

import java.time.LocalDate;

/**
 * Classe empréstimo que ira guardar as informações um empréstimo de um livro para o leitor.
 *
 * Aqui a classe guarda as datas de inicio e fim do empréstimo, o id do leitor que solicitou o empréstimo, o isbn do
 * livro que foi emprestado e o id do empréstimo que foi gerado pelo DAO.
 */
public class Emprestimo {
    private final LocalDate dataInicio;
    private LocalDate dataFim;
    private final String idMutuario;
    private final String isbnLivro;
    private String idEmprestimo;

    /**
     * Construtor padrão da classe empréstimo, aqui é criado o empréstimo já com a dataInicio que é a momento que foi
     * criado e a data fim predefinida para daqui a 7 dias da data inicio, aqui o leitor perde uma unidade de emprés-
     * timo e o livro perde uma quantidade no estoque.
     * @param idMutuario id do usuário que dono do empréstimo.
     * @param isbnLivro isbn do livro do empréstimo.
     * @throws foraDeEstoqueException Caso o livro não haja estoque.
     */
    public Emprestimo(String idMutuario, String isbnLivro) throws foraDeEstoqueException {
        this.dataInicio = LocalDate.now();
        this.dataFim = this.dataInicio.plusDays(7);
        this.idMutuario = idMutuario;
        this.isbnLivro = isbnLivro;
        DAO.getLeitorDAO().findByPk(idMutuario).removerUmEmprestimo();
        DAO.getLivroDAO().findByIsbn(isbnLivro).removerUmaUnidade();
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setIdEmprestimo(String idEmprestimo) {
        this.idEmprestimo = idEmprestimo;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public String getIdMutuario() {
        return idMutuario;
    }

    public String getIsbnLivro() {
        return isbnLivro;
    }

    public String getIdEmprestimo() {
        return idEmprestimo;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }
}

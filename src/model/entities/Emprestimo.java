package model.entities;

import dao.DAO;
import exceptions.foraDeEstoqueException;
import exceptions.objetoInexistenteException;
import exceptions.usuarioBloqueadoException;

import java.time.LocalDate;

public class Emprestimo {
    private final LocalDate dataInicio;
    private LocalDate dataFim;
    private final String idMutuario;
    private final String isbnLivro;
    private String idEmprestimo;

    public Emprestimo(String idMutuario, String isbnLivro) throws foraDeEstoqueException {
        this.dataInicio = LocalDate.now();
        this.dataFim = dataInicio.plusDays(7);
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

    public void renovarEmprestimo(Leitor leitor, String isbnLivro) throws usuarioBloqueadoException, objetoInexistenteException {
        if(leitor == null)
            throw new objetoInexistenteException("Usuário não existe.");
        Emprestimo emprestimo = Sistema.buscarEmprestimoDoLeitor(leitor, isbnLivro);
        if(emprestimo == null)
            throw new objetoInexistenteException("Empréstimo não existe.");
        if (Sistema.checarSeHaAtrasoLeitor(leitor))
            throw new usuarioBloqueadoException("Usuário em atraso, não é possivel renovar.");
        else if(!emprestimo.getIsbnLivro().equals(this.isbnLivro))
            throw new objetoInexistenteException("Não há emprestimo com esse livro.");
        else if ((getDataFim().compareTo(dataInicio)) <= 7) {
            this.dataFim = LocalDate.now().plusDays(7);
        }
    }
}

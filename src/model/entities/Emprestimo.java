package model.entities;

import dao.DAO;

import java.time.LocalDate;

public class Emprestimo {
    private final LocalDate dataInicio;
    private final LocalDate dataFim;
    private final String idMutuario;
    private final String isbnLivro;
    private String idEmprestimo;

    public Emprestimo(String idMutuario, String isbnLivro) {
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
}

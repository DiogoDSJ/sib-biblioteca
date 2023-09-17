package model.entities;

import dao.DAO;

import java.util.Date;

public class Emprestimo {
    private final Date dataInicio;
    private final Date dataFim;
    private final String idMutuario;
    private final String isbnLivro;
    private String idEmprestimo;

    public Emprestimo(Date dataInicio, Date dataFim, String idMutuario, String isbnLivro) {
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.idMutuario = idMutuario;
        this.isbnLivro = isbnLivro;
        DAO.getLeitorDAO().findByPk(idMutuario).removerUmEmprestimo();
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setIdEmprestimo(String idEmprestimo) {
        this.idEmprestimo = idEmprestimo;
    }

    public Date getDataFim() {
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

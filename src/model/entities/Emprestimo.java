package model.entities;

import java.util.Date;

public class Emprestimo {
    private Date dataInicio;
    private Date dataFim;
    private int idMutuario;
    private String isbnLivro;
    private int idEmprestimo;

    public Emprestimo(Date dataInicio, Date dataFim, int idMutuario, String isbnLivro) {
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.idMutuario = idMutuario;
        this.isbnLivro = isbnLivro;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setIdEmprestimo(int idEmprestimo) {
        this.idEmprestimo = idEmprestimo;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public int getIdMutuario() {
        return idMutuario;
    }

    public String getIsbnLivro() {
        return isbnLivro;
    }

    public int getIdEmprestimo() {
        return idEmprestimo;
    }
}

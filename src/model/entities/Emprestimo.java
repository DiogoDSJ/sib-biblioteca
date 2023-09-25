package model.entities;

import dao.DAO;
import exceptions.foraDeEstoqueException;

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

    public boolean renovarEmprestimo() {
        if((getDataFim().compareTo(dataInicio)) <= 7){
            this.dataFim = LocalDate.now().plusDays(7);
            return true;
        }
        else{
            return false;
        }
    }
}

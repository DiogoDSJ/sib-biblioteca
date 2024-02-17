package com.pbl.sibbiblioteca.model.entities;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Classe reserva, que será utilizado para guardar as reservas dos livros do usuário.
 * Aqui será guardado o id do reservante, data da reserva, dia que em que a reserva foi ativada,
 * dia em que a reserva será desativada caso o usuário não pegue o livro, isbn do livro reservado, id do
 * leitor que reservou.
 */
public class Reserva implements Serializable {


    private String idReservante;
    private final LocalDate dataReserva;
    private LocalDate dataInicioReserva;
    private LocalDate dataFimReserva;
    private String isbnLivro;
    private String idReserva;

    /**
     * Construtor da classe reserva,
     * @param idReservante ID do leitor que reservou o livro.
     * @param isbnLivro ISBN do livro à ser reservado.
     */
    public Reserva(String idReservante, String isbnLivro) {
        this.idReservante = idReservante;
        this.dataReserva = LocalDate.now();
        this.isbnLivro = isbnLivro;
    }

    public String getIdReservante() {
        return idReservante;
    }

    public void setIdReservante(String idReservante) {
        this.idReservante = idReservante;
    }

    public LocalDate getDataReserva() {
        return dataReserva;
    }

    public String getIsbnLivro() {
        return isbnLivro;
    }

    public void setIsbnLivro(String isbnLivro) {
        this.isbnLivro = isbnLivro;
    }

    public String getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(String idReserva) {
        this.idReserva = idReserva;
    }

    public LocalDate getDataInicioReserva() {
        return dataInicioReserva;
    }

    public void setDataInicioReserva(LocalDate dataInicioReserva) {
        this.dataInicioReserva = dataInicioReserva;
    }

    public LocalDate getDataFimReserva() {
        return dataFimReserva;
    }

    public void setDataFimReserva(LocalDate dataFimReserva) {
        this.dataFimReserva = dataFimReserva;
    }
}

package model.entities;

import java.time.LocalDate;

public class Reserva {


    private String idReservante;
    private LocalDate dataReserva;
    private LocalDate dataInicioReserva;
    private LocalDate dataFimReserva;
    private String isbnLivro;
    private String idReserva;

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

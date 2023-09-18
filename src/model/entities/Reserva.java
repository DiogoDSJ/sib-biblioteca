package model.entities;

import java.time.LocalDate;

public class Reserva {


    private String idReservante;
    private LocalDate dataReserva;
    private String idLivro;
    private String idReserva;

    public Reserva(String idReservante, LocalDate dataReserva, String idLivro, String idReserva) {
        this.idReservante = idReservante;
        this.dataReserva = dataReserva;
        this.idLivro = idLivro;
        this.idReserva = idReserva;
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

    public void setDataReserva(LocalDate dataReserva) {
        this.dataReserva = dataReserva;
    }

    public String getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(String idLivro) {
        this.idLivro = idLivro;
    }

    public String getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(String idReserva) {
        this.idReserva = idReserva;
    }
}

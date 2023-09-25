package model.entities;

import java.time.LocalDate;

public class Reserva {


    private String idReservante;
    private LocalDate dataReserva;
    private LocalDate dataInicioReserva;
    private LocalDate dataFimReserva;
    private String idLivro;
    private String idReserva;

    public Reserva(String idReservante, String idLivro) {
        this.idReservante = idReservante;
        this.dataReserva = LocalDate.now();
        this.idLivro = idLivro;
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

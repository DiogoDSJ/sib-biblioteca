package model.entities;

import java.util.Date;

public class Reserva {


    private String idReservante;
    private Date dataReserva;
    private String idLivro;
    private String idReserva;

    public Reserva(String idReservante, Date dataReserva, String idLivro, String idReserva) {
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

    public Date getDataReserva() {
        return dataReserva;
    }

    public void setDataReserva(Date dataReserva) {
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

package com.pbl.sibbiblioteca.dao.reserva;

import com.pbl.sibbiblioteca.model.entities.Reserva;

import java.util.ArrayList;
import java.util.List;

public class ReservaDAOList implements ReservaDAO{
    // ordenar por ordem de chegada e chave será isbn do livro
    private List<Reserva> lista;

    private String proximoID;

    public ReservaDAOList() {
        this.lista = new ArrayList<>();
        this.proximoID = "0";
    }
    private String getProximoID() {
        int proxID = Integer.parseInt(proximoID);
        proxID++;
        return this.proximoID = Integer.toString(proxID);
    }
    @Override
    public Reserva create(Reserva obj) {
        obj.setIdReserva(this.getProximoID());
        this.lista.add(obj);
        return obj;
    }

    @Override
    public void delete(Reserva obj) {
        this.lista.remove(obj);
    }

    @Override
    public Reserva findByPk(String obj) {
        for (Reserva objReserva: this.lista) {
            if(obj.equals(objReserva.getIdReserva())){
                return objReserva;
            }
        }
        return null;
    }

    @Override
    public void deleteMany() {
        this.lista = new ArrayList<>();
        this.proximoID = "0";
    }

    @Override
    public Reserva update(Reserva obj) {
        this.lista.set(this.lista.indexOf(obj), obj);
        return obj;
    }

    @Override
    public List<Reserva> findMany() {
        return this.lista;
    }

    @Override
    public List<Reserva> findByIdReservante(String obj) {
        List<Reserva> reservaList = new ArrayList<>();
        for (Reserva objReserva: this.lista) {
            if(obj.equals(objReserva.getIdReservante())){
                reservaList.add(objReserva);
            }
        }
        return reservaList;
    }
}

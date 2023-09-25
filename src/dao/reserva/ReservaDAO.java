package dao.reserva;

import dao.CRUD;
import model.entities.Reserva;

public interface ReservaDAO extends CRUD<Reserva> {


    public Reserva findByIdReservante(String obj);

}

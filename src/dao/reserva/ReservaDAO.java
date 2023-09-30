package dao.reserva;

import dao.CRUD;
import model.entities.Reserva;

import java.util.List;

public interface ReservaDAO extends CRUD<Reserva> {


    public List<Reserva> findByIdReservante(String obj);

}

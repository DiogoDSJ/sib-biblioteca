package com.pbl.sibbiblioteca.dao.reserva;

import com.pbl.sibbiblioteca.dao.CRUD;
import com.pbl.sibbiblioteca.model.entities.Reserva;

import java.util.List;

/**
 * Interface criada para implementar o armazenamento dos objetos reservas, usando a interface CRUD.
 */
public interface ReservaDAO extends CRUD<Reserva> {

    /**
     * Encontra as reservas do leitor a partir do seu ID.
     * @param obj id do leitor
     * @return Reservas do leitor
     */
    public List<Reserva> findByIdReservante(String obj);

}

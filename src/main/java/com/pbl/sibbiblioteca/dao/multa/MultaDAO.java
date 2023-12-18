package com.pbl.sibbiblioteca.dao.multa;

import com.pbl.sibbiblioteca.dao.CRUD;
import com.pbl.sibbiblioteca.model.entities.Multa;

/**
 * Interface criada para implementar o armazenamento dos objetos multa, usando a interface CRUD.
 */
public interface MultaDAO extends CRUD<Multa> {
    /**
     * Encontra a multa de um leitor a partir de seu id.
     * @param obj Id do leitor.
     * @return Multa do leitor.
     */
    public Multa findByIdMutuario(String obj);


}

package dao.multa;

import dao.CRUD;
import model.entities.Multa;

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

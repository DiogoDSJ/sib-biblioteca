package dao.multa;

import dao.CRUD;
import model.entities.Multa;

public interface MultaDAO extends CRUD<Multa> {

    public Multa findByIdMutuario(String obj);


}

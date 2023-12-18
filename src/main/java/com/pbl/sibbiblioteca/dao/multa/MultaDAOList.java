package com.pbl.sibbiblioteca.dao.multa;

import com.pbl.sibbiblioteca.model.entities.Multa;

import java.util.ArrayList;
import java.util.List;

public class MultaDAOList implements MultaDAO {

    private List<Multa> lista;
    private String proximoID;

    public MultaDAOList() {
        this.lista = new ArrayList<>();
        this.proximoID = "0";
    }

    private String getProximoID(){
        int proxID = Integer.parseInt(this.proximoID);
        proxID++;
        return this.proximoID = Integer.toString(proxID);
    }

    @Override
    public Multa create(Multa obj) {
        obj.setIdMulta(this.getProximoID());
        this.lista.add(obj);
        return obj;
    }

    @Override
    public void delete(Multa obj) {
        this.lista.remove(obj);

    }

    @Override
    public Multa findByPk(String obj) {
        for (Multa objIterator: lista) {
            if(obj.equals(objIterator.getIdMulta())) {
                return objIterator;
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
    public Multa update(Multa obj) {
        this.lista.set(lista.indexOf(obj), obj);
        return obj;
    }

    @Override
    public List<Multa> findMany() {
        return this.lista;
    }

    @Override
    public Multa findByIdMutuario(String obj){
        for (Multa objIterator: lista) {
            if(obj.equals(objIterator.getIdUsuario())) {
                return objIterator;
            }
        }
        return null;
    }


}

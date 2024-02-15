package com.pbl.sibbiblioteca.dao.leitor;

import com.pbl.sibbiblioteca.model.entities.Leitor;

import java.util.ArrayList;
import java.util.List;

public class LeitorDAOList implements LeitorDAO {

    private List<Leitor> lista;
    private String proximoID;

    public LeitorDAOList() {
        this.lista = new ArrayList<>();
        this.proximoID = "0";
    }

    private String getProximoID() {
        int proxID = Integer.parseInt(proximoID);
        proxID++;
        this.proximoID = Integer.toString(proxID);
        return this.proximoID;
    }

    @Override
    public Leitor create(Leitor obj) {
        obj.setId(this.getProximoID());
        this.lista.add(obj);
        return obj;
    }

    @Override
    public void delete(Leitor obj) {
        this.lista.remove(obj);
    }

    @Override
    public Leitor findByPk(String obj) {
        for (Leitor objIterator: this.lista) {
            if(obj.equals(objIterator.getId())) {
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
    public Leitor update(Leitor obj) {
        this.lista.set(this.lista.indexOf(obj), obj);
        return obj;
    }

    @Override
    public List<Leitor> findMany() {
        return this.lista;
    }

    @Override
    public Leitor findByUsuario(String usuario){
        for (Leitor leitor: lista) {
            if(leitor.getUsuario().equals(usuario)){
                return leitor;
            }
        }
        return null;
    }
    public List<Leitor> findByUsuarioList(String usuario) {
        List<Leitor> usuariosEncontrados = new ArrayList<>();
        for (Leitor objIterator : this.lista) {
            if (objIterator.getUsuario().toLowerCase().contains(usuario.toLowerCase())) {
                usuariosEncontrados.add(objIterator);
            }
        }
        return usuariosEncontrados;
    }
}

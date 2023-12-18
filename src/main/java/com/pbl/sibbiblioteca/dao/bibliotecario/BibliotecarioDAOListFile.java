package com.pbl.sibbiblioteca.dao.bibliotecario;

import com.pbl.sibbiblioteca.model.entities.Bibliotecario;
import com.pbl.sibbiblioteca.utils.ControleArmazenamentoArquivoDAO;

import java.util.ArrayList;
import java.util.List;

public class BibliotecarioDAOListFile implements BibliotecarioDAO {

    private List<Bibliotecario> lista;
    private String proximoID;

    private String getProximoID() {
        int proxID = Integer.parseInt(proximoID);
        proxID++;
        return this.proximoID = Integer.toString(proxID);
    }

    public BibliotecarioDAOListFile() {
        this.lista = ControleArmazenamentoArquivoDAO.carregarDados(Bibliotecario.class);
        if(!lista.isEmpty()){
            this.proximoID = lista.get(lista.size() - 1).getId();
        }
        else {
            this.proximoID = "0";
        }
    }

    @Override
    public Bibliotecario create(Bibliotecario obj) {
        obj.setId(this.getProximoID());
        this.lista.add(obj);
        ControleArmazenamentoArquivoDAO.guardarDados(this.lista, Bibliotecario.class);
        return obj;
    }

    @Override
    public void delete(Bibliotecario obj) {
        this.lista.remove(obj);
        ControleArmazenamentoArquivoDAO.guardarDados(this.lista, Bibliotecario.class);
    }

    @Override
    public Bibliotecario findByPk(String obj) {
        for (Bibliotecario objIterator : this.lista) {
            if (obj.equals(objIterator.getId())) {
                return objIterator;
            }
        }
        return null;
    }

    @Override
    public void deleteMany() {
        this.lista = new ArrayList<>();
        this.proximoID = "0";
        ControleArmazenamentoArquivoDAO.guardarDados(this.lista, Bibliotecario.class);
    }

    @Override
    public Bibliotecario update(Bibliotecario obj) {
        this.lista.set(lista.indexOf(obj), obj);
        ControleArmazenamentoArquivoDAO.guardarDados(this.lista, Bibliotecario.class);
        return obj;
    }

    @Override
    public List<Bibliotecario> findMany() {
        return this.lista;
    }

    @Override
    public Bibliotecario findByUsuario(String usuario) {
        for (Bibliotecario bibliotecario : lista) {
            if (bibliotecario.getUsuario().equals(usuario)) {
                return bibliotecario;
            }
        }
        return null;
    }
}

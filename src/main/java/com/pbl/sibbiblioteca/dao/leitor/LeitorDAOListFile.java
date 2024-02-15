package com.pbl.sibbiblioteca.dao.leitor;

import com.pbl.sibbiblioteca.model.entities.Bibliotecario;
import com.pbl.sibbiblioteca.model.entities.Leitor;
import com.pbl.sibbiblioteca.utils.ControleArmazenamentoArquivoDAO;

import java.util.ArrayList;
import java.util.List;

public class LeitorDAOListFile implements LeitorDAO {

    private List<Leitor> lista;
    private String proximoID;

    public LeitorDAOListFile() {
        this.lista = ControleArmazenamentoArquivoDAO.carregarDados(Leitor.class);
        if(!lista.isEmpty()){
            this.proximoID = lista.get(lista.size() - 1).getId();
        }
        else {
            this.proximoID = "0";
        }
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
        ControleArmazenamentoArquivoDAO.guardarDados(lista, Leitor.class);
        return obj;
    }

    @Override
    public void delete(Leitor obj) {
        this.lista.remove(obj);
        ControleArmazenamentoArquivoDAO.guardarDados(lista, Leitor.class);
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
        ControleArmazenamentoArquivoDAO.guardarDados(lista, Leitor.class);
    }

    @Override
    public Leitor update(Leitor obj) {
        this.lista.set(this.lista.indexOf(obj), obj);
        ControleArmazenamentoArquivoDAO.guardarDados(lista, Leitor.class);
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

package com.pbl.sibbiblioteca.dao.emprestimo;

import com.pbl.sibbiblioteca.model.entities.Emprestimo;
import com.pbl.sibbiblioteca.utils.ControleArmazenamentoArquivoDAO;

import java.util.ArrayList;
import java.util.List;

public class EmprestimoDAOListFile implements EmprestimoDAO {

    private List<Emprestimo> lista;
    private String proximoID;

    private String getProximoID() {
        int proxID = Integer.parseInt(proximoID);
        proxID++;
        this.proximoID = Integer.toString(proxID);
        return this.proximoID;
    }

    public EmprestimoDAOListFile() {
        this.lista = ControleArmazenamentoArquivoDAO.carregarDados(Emprestimo.class);
        if(!lista.isEmpty()){
            this.proximoID = lista.get(lista.size() - 1).getIdEmprestimo();
        }
        else {
            this.proximoID = "0";
        }
    }

    @Override
    public Emprestimo create(Emprestimo obj) {
        obj.setIdEmprestimo(this.getProximoID());
        this.lista.add(obj);
        ControleArmazenamentoArquivoDAO.guardarDados(this.lista, Emprestimo.class);
        return obj;
    }

    @Override
    public void delete(Emprestimo obj) {
        this.lista.remove(obj);
        ControleArmazenamentoArquivoDAO.guardarDados(this.lista, Emprestimo.class);
    }

    @Override
    public Emprestimo findByPk(String obj) {
        for (Emprestimo objIterator: this.lista) {
            if(obj.equals(objIterator.getIdEmprestimo())) {
                return objIterator;
            }
        }
        return null;
    }

    @Override
    public void deleteMany() {
        this.lista = new ArrayList<>();
        this.proximoID = "0";
        ControleArmazenamentoArquivoDAO.guardarDados(this.lista, Emprestimo.class);
    }

    @Override
    public Emprestimo update(Emprestimo obj) {
        this.lista.set(lista.indexOf(obj), obj);
        ControleArmazenamentoArquivoDAO.guardarDados(this.lista, Emprestimo.class);
        return obj;
    }

    @Override
    public List<Emprestimo> findMany() {
        return this.lista;
    }

    @Override
    public List<Emprestimo> findByIdMutuario(String idMutuario) {
        List<Emprestimo> emprestimoList = new ArrayList<>();
        for (Emprestimo objIterator: this.lista) {
            if(idMutuario.equals(objIterator.getIdMutuario())) {
                emprestimoList.add(objIterator);
            }
        }
        return emprestimoList;
    }
    @Override
    public List<Emprestimo> findByIsbn(String isbn) {
        List<Emprestimo> emprestimoList = new ArrayList<>();
        for (Emprestimo objIterator: this.lista) {
            if(isbn.equals(objIterator.getIdMutuario())) {
                emprestimoList.add(objIterator);
            }
        }
        return emprestimoList;
    }



}

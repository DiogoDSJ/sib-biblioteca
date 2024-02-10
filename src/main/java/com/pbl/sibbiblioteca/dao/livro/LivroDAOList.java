package com.pbl.sibbiblioteca.dao.livro;

import com.pbl.sibbiblioteca.model.entities.Livro;

import java.util.ArrayList;
import java.util.List;

public class LivroDAOList implements LivroDAO {

    private List<Livro> lista;
    private String proximoID;

    public LivroDAOList() {
        this.lista = new ArrayList<>();
        this.proximoID = "0";
    }

    private String getProximoID() {
        int proxID = Integer.parseInt(this.proximoID);
        proxID++;
        return this.proximoID = Integer.toString(proxID);
    }

    @Override
    public Livro create(Livro obj) {
        obj.setId(this.getProximoID());
        this.lista.add(obj);
        return obj;
    }

    @Override
    public void delete(Livro obj) {
        this.lista.remove(obj);
    }

    @Override
    public Livro findByPk(String obj) {
        for (Livro objIterator: this.lista) {
            if(obj.equals(objIterator.getId())){
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
    public Livro update(Livro obj) {
        this.lista.set(this.lista.indexOf(obj), obj);
        return obj;
    }

    @Override
    public List<Livro> findMany() {
        return this.lista;
    }

    @Override
    public Livro findByIsbn(String isbn){
        for (Livro objIterator: this.lista) {
            if(isbn.equals(objIterator.getIsbn())){
                return objIterator;
            }
        }
        return null;
    }

    @Override
    public List<Livro> findByTitulo(String titulo){
        List<Livro> livrosEncontrados = new ArrayList<>();
        for (Livro objIterator: this.lista) {
            if(objIterator.getTitulo().toLowerCase().contains(titulo.toLowerCase())){
                livrosEncontrados.add(objIterator);
            }
        }
        return livrosEncontrados;
    }

    @Override
    public List<Livro> findByAutor(String autor) {
        List<Livro> livrosEncontrados = new ArrayList<>();
        for (Livro objIterator : this.lista) {
            if (objIterator.getAutor().toLowerCase().contains(autor.toLowerCase())) {
                livrosEncontrados.add(objIterator);
            }
        }
        return livrosEncontrados;
    }

    @Override
    public List<Livro> findByCategoria(String categoria) {
        List<Livro> livrosEncontrados = new ArrayList<>();
        for (Livro objIterator : this.lista) {
            if (objIterator.getCategoria().toLowerCase().contains(categoria.toLowerCase())) {
                livrosEncontrados.add(objIterator);
            }
        }
        return livrosEncontrados;
    }

    @Override
    public List<Livro> findByAnoDePublicao(String anoDePublicacao) {
        List<Livro> livrosEncontrados = new ArrayList<>();
        for (Livro objIterator : this.lista) {
            if (objIterator.getAnoDePublicacao().equals(anoDePublicacao)) {
                livrosEncontrados.add(objIterator);
            }
        }
        return livrosEncontrados;
    }
    @Override
    public List<Livro> findByEditora(String editora){
        List<Livro> livrosEncontrados = new ArrayList<>();
        for(Livro objIterator : this.lista){
            if(objIterator.getEditora().toLowerCase().contains(editora.toLowerCase())){
                livrosEncontrados.add(objIterator);
            }
        }
        return livrosEncontrados;
    }
}

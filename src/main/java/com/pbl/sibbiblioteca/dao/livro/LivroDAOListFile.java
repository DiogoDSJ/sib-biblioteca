package com.pbl.sibbiblioteca.dao.livro;

import com.pbl.sibbiblioteca.model.entities.Livro;
import com.pbl.sibbiblioteca.utils.ControleArmazenamentoArquivoDAO;

import java.util.ArrayList;
import java.util.List;

public class LivroDAOListFile implements LivroDAO {

    private List<Livro> lista;
    private String proximoID;

    public LivroDAOListFile() {
        this.lista = ControleArmazenamentoArquivoDAO.carregarDados(Livro.class);
        if(!lista.isEmpty()){
            this.proximoID = lista.get(lista.size() - 1).getId();
        }
        else {
            this.proximoID = "0";
        }
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
        ControleArmazenamentoArquivoDAO.guardarDados(lista, Livro.class);
        return obj;
    }

    @Override
    public void delete(Livro obj) {
        this.lista.remove(obj);
        ControleArmazenamentoArquivoDAO.guardarDados(lista, Livro.class);
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
        ControleArmazenamentoArquivoDAO.guardarDados(lista, Livro.class);
    }

    @Override
    public Livro update(Livro obj) {
        this.lista.set(this.lista.indexOf(obj), obj);
        ControleArmazenamentoArquivoDAO.guardarDados(lista, Livro.class);
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
            if(objIterator.getTitulo().contains(titulo)){
                livrosEncontrados.add(objIterator);
            }
        }
        return livrosEncontrados;
    }

    @Override
    public List<Livro> findByAutor(String autor) {
        List<Livro> livrosEncontrados = new ArrayList<>();
        for (Livro objIterator : this.lista) {
            if (objIterator.getAutor().contains(autor)) {
                livrosEncontrados.add(objIterator);
            }
        }
        return livrosEncontrados;
    }

    @Override
    public List<Livro> findByCategoria(String categoria) {
        List<Livro> livrosEncontrados = new ArrayList<>();
        for (Livro objIterator : this.lista) {
            if (categoria.equals(objIterator.getCategoria())) {
                livrosEncontrados.add(objIterator);
            }
        }
        return livrosEncontrados;
    }

    @Override
    public List<Livro> findByAnoDePublicao(String anoDePublicacao) {
        List<Livro> livrosEncontrados = new ArrayList<>();
        for (Livro objIterator : this.lista) {
            if (objIterator.getAnoDePublicacao().contains(anoDePublicacao)) {
                livrosEncontrados.add(objIterator);
            }
        }
        return livrosEncontrados;
    }
    @Override
    public List<Livro> findByEditora(String editora){
        List<Livro> livrosEncontrados = new ArrayList<>();
        for(Livro objIterator : this.lista){
            if(objIterator.getEditora().contains(editora)){
                livrosEncontrados.add(objIterator);
            }
        }
        return livrosEncontrados;
    }
}

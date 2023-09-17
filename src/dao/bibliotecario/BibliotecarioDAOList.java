package dao.bibliotecario;

import model.entities.Bibliotecario;

import java.util.ArrayList;
import java.util.List;

public class BibliotecarioDAOList implements BibliotecarioDAO {

    private List<Bibliotecario> lista;
    private String proximoID;

    private String getProximoID() {
        int proxID = Integer.parseInt(proximoID);
        proxID++;
        return this.proximoID = Integer.toString(proxID);
    }

    public BibliotecarioDAOList() {
        this.lista = new ArrayList<>();
        this.proximoID = "0";
    }

    @Override
    public Bibliotecario create(Bibliotecario obj) {
        obj.setId(this.getProximoID());
        this.lista.add(obj);
        return obj;
    }

    @Override
    public void delete(Bibliotecario obj) {
        this.lista.remove(obj);
    }

    @Override
    public Bibliotecario findByPk(String obj) {
        for (Bibliotecario objIterator: this.lista) {
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
    public Bibliotecario update(Bibliotecario obj) {
        this.lista.set(lista.indexOf(obj), obj);
        return obj;
    }

    @Override
    public List<Bibliotecario> findMany() {
        return this.lista;
    }
}

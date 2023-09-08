package dao.administrador;

import model.entities.Administrador;


import java.util.ArrayList;
import java.util.List;

public class AdministradorDAOList implements AdministradorDAO {

    private List<Administrador> lista;
    private int proximoID;

    public AdministradorDAOList() {
        this.lista = new ArrayList<>();
        this.proximoID = 0;
    }


    private int getProximoID(){
        return this.proximoID++;
    }


    @Override
    public Administrador create(Administrador obj) {
        obj.setId(this.getProximoID());
        this.lista.add(obj);
        return obj;
    }

    @Override
    public void delete(Administrador obj) {
        this.lista.remove(obj);
    }

    @Override
    public Administrador findByPk(int obj) {
        for (Administrador objIterator : this.lista) {
            if(obj == objIterator.getId())
            {
                return objIterator;
            }
        }
        return null;
    }

    @Override
    public void deleteMany() {
        this.lista = new ArrayList<>();
        this.proximoID = 0;
    }

    @Override
    public Administrador update(Administrador obj) {
        this.lista.set(this.lista.indexOf(obj), obj);
        return obj;
    }

    @Override
    public List<Administrador> findMany() {
        return this.lista;
    }
}

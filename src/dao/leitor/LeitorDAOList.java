package dao.leitor;

import model.entities.Leitor;

import java.util.ArrayList;
import java.util.List;

public class LeitorDAOList implements LeitorDAO {

    private List<Leitor> lista;
    private int ProximoID;

    public LeitorDAOList() {
        lista = new ArrayList<>();
        this.ProximoID = 0;
    }

    private int getProximoID() {
        return ProximoID++;
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
    public Leitor findByPk(int obj) {
        for (Leitor objIterator: this.lista) {
            if(obj == objIterator.getId())
                return objIterator;
        }
        return null;
    }

    @Override
    public void deleteMany() {
        this.lista = new ArrayList<>();
        this.ProximoID = 0;
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
}

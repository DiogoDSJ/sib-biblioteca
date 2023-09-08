package dao.livro;

import model.entities.Livro;

import java.util.ArrayList;
import java.util.List;

public class LivroDAOList implements LivroDAO {

    private List<Livro> lista;
    private int proximoID;

    public LivroDAOList() {
        this.lista = new ArrayList<>();
        this.proximoID = 0;
    }

    public int getProximoID() {
        return proximoID++;
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
    public Livro findByPk(int obj) {
        for (Livro objIterator: this.lista) {
            if(obj == objIterator.getId()){
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
    public Livro update(Livro obj) {
        this.lista.set(this.lista.indexOf(obj), obj);
        return obj;
    }

    @Override
    public List<Livro> findMany() {
        return this.lista;
    }
}

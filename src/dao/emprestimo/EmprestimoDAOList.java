package dao.emprestimo;

import model.entities.Emprestimo;

import java.util.ArrayList;
import java.util.List;

public class EmprestimoDAOList implements EmprestimoDAO {

    private List<Emprestimo> lista;
    private int proximoID;

    public int getProximoID() {

        return proximoID++;
    }

    public EmprestimoDAOList() {
        this.lista = new ArrayList<>();
        this.proximoID = 0;
    }

    @Override
    public Emprestimo create(Emprestimo obj) {
        obj.setIdEmprestimo(this.getProximoID());
        this.lista.add(obj);
        return obj;
    }

    @Override
    public void delete(Emprestimo obj) {
        this.lista.remove(obj);
    }

    @Override
    public Emprestimo findByPk(int obj) {
        for (Emprestimo objIterator: this.lista) {
            if(obj == objIterator.getIdEmprestimo()) {
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
    public Emprestimo update(Emprestimo obj) {
        this.lista.set(lista.indexOf(obj), obj);
        return obj;
    }

    @Override
    public List<Emprestimo> findMany() {
        return this.lista;
    }
}

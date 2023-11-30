package dao.administrador;

import model.entities.Administrador;
import model.entities.Leitor;
import utils.ControleArmazenamentoArquivoDAO;


import java.util.ArrayList;
import java.util.List;

public class AdministradorDAOListFile implements AdministradorDAO {

    private List<Administrador> lista;
    private String proximoID;

    public AdministradorDAOListFile() {
        this.lista = ControleArmazenamentoArquivoDAO.carregarDados(Administrador.class);
        if(!lista.isEmpty()){
            this.proximoID = lista.get(lista.size() - 1).getId();
        }
        else {
            this.proximoID = "0";
        }
    }


    private String getProximoID(){
        int proxID = Integer.parseInt(proximoID);
        proxID++;
        return this.proximoID = Integer.toString(proxID);
    }


    @Override
    public Administrador create(Administrador obj)  {
        obj.setId(this.getProximoID());
        this.lista.add(obj);
        ControleArmazenamentoArquivoDAO.guardarDados(this.lista, Administrador.class);
        return obj;
    }

    @Override
    public void delete(Administrador obj)  {
        this.lista.remove(obj);
        ControleArmazenamentoArquivoDAO.guardarDados(this.lista, Administrador.class);
    }

    @Override
    public Administrador findByPk(String obj) {
        for (Administrador objIterator : this.lista) {
            if(obj.equals(objIterator.getId()))
            {
                return objIterator;
            }
        }
        return null;
    }

    @Override
    public void deleteMany()  {
        this.lista = new ArrayList<>();
        this.proximoID = "0";
        ControleArmazenamentoArquivoDAO.guardarDados(this.lista, Administrador.class);
    }

    @Override
    public Administrador update(Administrador obj)  {
        this.lista.set(this.lista.indexOf(obj), obj);
        ControleArmazenamentoArquivoDAO.guardarDados(this.lista, Administrador.class);
        return obj;
    }

    @Override
    public List<Administrador> findMany() {
        return this.lista;
    }

    @Override
    public Administrador findByUsuario(String usuario) {
        for (Administrador administrador : lista) {
            if (administrador.getUsuario().equals(usuario)) {
                return administrador;
            }
        }
        return null;
    }
}

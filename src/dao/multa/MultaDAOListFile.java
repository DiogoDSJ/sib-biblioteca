package dao.multa;

import model.entities.Leitor;
import model.entities.Multa;
import utils.ControleArmazenamentoArquivoDAO;

import java.util.ArrayList;
import java.util.List;

public class MultaDAOListFile implements MultaDAO {

    private List<Multa> lista;
    private String proximoID;

    public MultaDAOListFile() {
        this.lista = ControleArmazenamentoArquivoDAO.carregarDados(Multa.class);
        if(!lista.isEmpty()){
            this.proximoID = lista.get(lista.size() - 1).getIdMulta();
        }
        else {
            this.proximoID = "0";
        }
    }

    private String getProximoID(){
        int proxID = Integer.parseInt(this.proximoID);
        proxID++;
        return this.proximoID = Integer.toString(proxID);
    }

    @Override
    public Multa create(Multa obj) {
        obj.setIdMulta(this.getProximoID());
        this.lista.add(obj);
        ControleArmazenamentoArquivoDAO.guardarDados(lista, Multa.class);
        return obj;
    }

    @Override
    public void delete(Multa obj) {
        this.lista.remove(obj);
        ControleArmazenamentoArquivoDAO.guardarDados(lista, Multa.class);
    }

    @Override
    public Multa findByPk(String obj) {
        for (Multa objIterator: lista) {
            if(obj.equals(objIterator.getIdMulta())) {
                return objIterator;
            }
        }
        return null;
    }

    @Override
    public void deleteMany() {
        this.lista = new ArrayList<>();
        this.proximoID = "0";
        ControleArmazenamentoArquivoDAO.guardarDados(lista, Multa.class);
    }

    @Override
    public Multa update(Multa obj) {
        this.lista.set(lista.indexOf(obj), obj);
        ControleArmazenamentoArquivoDAO.guardarDados(lista, Multa.class);
        return obj;
    }

    @Override
    public List<Multa> findMany() {
        return this.lista;
    }

    @Override
    public Multa findByIdMutuario(String obj){
        for (Multa objIterator: lista) {
            if(obj.equals(objIterator.getIdUsuario())) {
                return objIterator;
            }
        }
        return null;
    }


}

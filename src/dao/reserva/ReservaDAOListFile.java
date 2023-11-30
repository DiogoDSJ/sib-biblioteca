package dao.reserva;

import model.entities.Reserva;
import utils.ControleArmazenamentoArquivoDAO;

import java.util.ArrayList;
import java.util.List;

public class ReservaDAOListFile implements ReservaDAO{
    // ordenar por ordem de chegada e chave será isbn do livro
    private List<Reserva> lista;

    private String proximoID;

    public ReservaDAOListFile() {
        this.lista = new ArrayList<>();
        this.proximoID = "0";
    }
    private String getProximoID() {
        int proxID = Integer.parseInt(proximoID);
        proxID++;
        return this.proximoID = Integer.toString(proxID);
    }
    @Override
    public Reserva create(Reserva obj) {
        obj.setIdReserva(this.getProximoID());
        this.lista.add(obj);
        ControleArmazenamentoArquivoDAO.guardarDados(lista, Reserva.class);
        return obj;
    }

    @Override
    public void delete(Reserva obj) {
        this.lista.remove(obj);
        ControleArmazenamentoArquivoDAO.guardarDados(lista, Reserva.class);
    }

    @Override
    public Reserva findByPk(String obj) {
        for (Reserva objReserva: this.lista) {
            if(obj.equals(objReserva.getIdReserva())){
                return objReserva;
            }
        }
        return null;
    }

    @Override
    public void deleteMany() {
        this.lista = new ArrayList<>();
        this.proximoID = "0";
        ControleArmazenamentoArquivoDAO.guardarDados(lista, Reserva.class);
    }

    @Override
    public Reserva update(Reserva obj) {
        this.lista.set(this.lista.indexOf(obj), obj);
        ControleArmazenamentoArquivoDAO.guardarDados(lista, Reserva.class);
        return obj;
    }

    @Override
    public List<Reserva> findMany() {
        return this.lista;
    }

    @Override
    public List<Reserva> findByIdReservante(String obj) {
        List<Reserva> reservaList = new ArrayList<>();
        for (Reserva objReserva: this.lista) {
            if(obj.equals(objReserva.getIdReservante())){
                reservaList.add(objReserva);
            }
        }
        return reservaList;
    }
}

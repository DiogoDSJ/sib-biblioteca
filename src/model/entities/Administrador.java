package model.entities;

import dao.DAO;
import exceptions.cargoInvalidoException;
import exceptions.foraDeEstoqueException;
import exceptions.objetoInexistenteException;
import exceptions.usuarioPendenciasException;
import model.entities.enums.Cargo;

import java.util.List;

public class Administrador extends Bibliotecario {

    public Administrador(String nome, String endereco, String telefone, String usuario, String senhaDeAcesso) {
        super(nome, endereco, telefone, usuario, senhaDeAcesso);
        this.setCargo(Cargo.ADMINISTRADOR);
    }

    public void cadastrarUsuario(String nome, String endereco, String telefone, String id, String usuario, String senhaDeAcesso, Cargo cargo) throws cargoInvalidoException
    {
        if(cargo == Cargo.LEITOR) {
            DAO.getLeitorDAO().create(new Leitor(nome, endereco, telefone, usuario, senhaDeAcesso));
        }
        else if(cargo == Cargo.BIBLIOTECARIO) {
            DAO.getBibliotecarioDAO().create(new Bibliotecario(nome, endereco, telefone, usuario, senhaDeAcesso));
        }
        else if(cargo == Cargo.ADMINISTRADOR) {
            DAO.getAdministradorDAO().create(new Administrador(nome, endereco, telefone, usuario, senhaDeAcesso));
        }
        else{
            throw new cargoInvalidoException("Cargo não existe.");
        }
    }

    public void editarUsuario(String id){

    }
    public void removerUsuario(String id){

    }
}

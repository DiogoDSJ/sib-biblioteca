package model.entities;

import model.entities.enums.Cargo;

public class Administrador extends Bibliotecario {

    public Administrador(String nome, String endereco, String telefone, String id, String usuario, String senhaDeAcesso) {
        super(nome, endereco, telefone, id, usuario, senhaDeAcesso);
        this.setCargo(Cargo.ADMINISTRADOR);
    }

    public Usuario cadastrarUsuario(String nome, String endereco, String telefone, String id, String usuario, String senhaDeAcesso, Cargo cargo)
    {
        if(cargo == Cargo.LEITOR) {
            return new Leitor(nome, endereco, telefone, id, usuario, senhaDeAcesso);
        }
        else if(cargo == Cargo.BIBLIOTECARIO) {
            return new Bibliotecario(nome, endereco, telefone, id, usuario, senhaDeAcesso);
        }
        else if(cargo == Cargo.ADMINISTRADOR) {
            return new Administrador(nome, endereco, telefone, id, usuario, senhaDeAcesso);
        }
        else{
            return null;
        }
    }

    public void editarUsuario(String id){

    }
    public void removerUsuario(String id){

    }
}

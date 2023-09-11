package model.entities;

import model.entities.enums.Cargo;

public class Administrador extends Bibliotecario {

    public Administrador(String nome, String endereco, String telefone, String usuario, String senhaDeAcesso) {
        super(nome, endereco, telefone, usuario, senhaDeAcesso);
        this.setCargo(Cargo.ADMINISTRADOR);
    }

    public Usuario cadastrarUsuario(String nome, String endereco, String telefone, int id, String usuario, String senhaDeAcesso, Cargo cargo)
    {
        if(cargo == Cargo.LEITOR) {
            return new Leitor(nome, endereco, telefone, usuario, senhaDeAcesso);
        }
        else if(cargo == Cargo.BIBLIOTECARIO) {
            return new Bibliotecario(nome, endereco, telefone, usuario, senhaDeAcesso);
        }
        else if(cargo == Cargo.ADMINISTRADOR) {
            return new Administrador(nome, endereco, telefone, usuario, senhaDeAcesso);
        }
        else{
            return null;
        }
    }

    public void editarUsuario(int id){

    }
    public void removerUsuario(int id){

    }
}

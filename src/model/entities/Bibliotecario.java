package model.entities;

import model.entities.enums.Cargo;

public class Bibliotecario extends Usuario {

    public Bibliotecario(String nome, String endereco, String telefone, int id, String usuario, String senhaDeAcesso) {
        super(nome, endereco, telefone, id, usuario, senhaDeAcesso);
        this.setCargo(Cargo.BIBLIOTECARIO);
    }

}

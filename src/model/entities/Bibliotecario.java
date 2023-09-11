package model.entities;

import model.entities.enums.Cargo;

public class Bibliotecario extends Usuario {

    public Bibliotecario(String nome, String endereco, String telefone, String usuario, String senhaDeAcesso) {
        super(nome, endereco, telefone, usuario, senhaDeAcesso);
        this.setCargo(Cargo.BIBLIOTECARIO);
    }

}

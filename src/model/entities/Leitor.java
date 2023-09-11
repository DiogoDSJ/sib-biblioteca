package model.entities;

import model.entities.enums.Cargo;

public class Leitor extends Usuario {

    private int numeroDeEmprestimos;

    public Leitor(String nome, String endereco, String telefone, String usuario, String senhaDeAcesso) {
        super(nome, endereco, telefone, usuario, senhaDeAcesso);
        this.numeroDeEmprestimos = 3;
        this.setCargo(Cargo.LEITOR);
    }

    public Integer getNumeroDeEmprestimos(){
        return numeroDeEmprestimos;
    }
}

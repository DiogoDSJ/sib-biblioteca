package model.entities;

import model.entities.enums.Cargo;

public class Leitor extends Usuario {

    private int numeroDeEmprestimos;

    public Leitor(String nome, String endereco, String telefone, String usuario, String senhaDeAcesso) {
        super(nome, endereco, telefone, usuario, senhaDeAcesso);
        this.numeroDeEmprestimos = 3;
        this.setCargo(Cargo.LEITOR);
    }

    public int getNumeroDeEmprestimos(){
        return numeroDeEmprestimos;
    }

    public void adicionarUmEmprestimo() {
        if(numeroDeEmprestimos <= 2)
            this.numeroDeEmprestimos++ ;
    }

    public void removerUmEmprestimo() {
        if (numeroDeEmprestimos > 0) {
            this.numeroDeEmprestimos--;
        }
    }
}

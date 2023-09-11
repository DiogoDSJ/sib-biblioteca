package model.entities;

import model.entities.enums.Cargo;

public class Leitor extends Usuario {

    public Leitor(String nome, String endereco, String telefone, int id, String usuario, String senhaDeAcesso) {
        super(nome, endereco, telefone, id, usuario, senhaDeAcesso);
    private int numeroDeEmprestimos;
    public Leitor(String nome, String endereco, String telefone, String usuario, String senhaDeAcesso) {
        super(nome, endereco, telefone, usuario, senhaDeAcesso);
        this.numeroDeEmprestimos = 3;
        this.setCargo(Cargo.LEITOR);
    }

    public Integer numeroDeEmprestimos(){
        return 0;
    public Integer getNumeroDeEmprestimos(){
    }
}

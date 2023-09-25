package model.entities;

import model.entities.enums.Cargo;

import java.util.ArrayList;
import java.util.List;

public class Leitor extends Usuario {

    private int numeroDeEmprestimos;
    private final List<Emprestimo> historicoEmprestimos;

    public Leitor(String nome, String endereco, String telefone, String usuario, String senhaDeAcesso) {
        super(nome, endereco, telefone, usuario, senhaDeAcesso);
        this.numeroDeEmprestimos = 3;
        this.setCargo(Cargo.LEITOR);
        this.historicoEmprestimos = new ArrayList<>();
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

    public List<Emprestimo> getHistoricoEmprestimos() {
        return historicoEmprestimos;
    }

    public void adicionarEmprestimoNoHistorico(Emprestimo emprestimo) {
        this.historicoEmprestimos.add(emprestimo);
    }

}

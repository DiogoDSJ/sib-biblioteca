package model.entities;

import exceptions.foraDeEstoqueException;
import model.entities.enums.Cargo;

import java.util.ArrayList;
import java.util.List;

public class Leitor extends Usuario {

    private int numeroDeEmprestimos; // refactor para string mais tarde.

    private int numeroDeReservas;
    private final List<Emprestimo> historicoEmprestimos;

    public Leitor(String nome, String endereco, String telefone, String usuario, String senhaDeAcesso) {
        super(nome, endereco, telefone, usuario, senhaDeAcesso);
        this.numeroDeEmprestimos = 3;
        this.numeroDeReservas = 3;
        this.setCargo(Cargo.LEITOR);
        this.historicoEmprestimos = new ArrayList<>();
    }

    public int getNumeroDeEmprestimos(){
        return numeroDeEmprestimos;
    }

    public int getNumeroDeReservas() {
        return numeroDeReservas;
    }

    public void adicionarUmaReserva() throws foraDeEstoqueException {
        if(numeroDeEmprestimos <= 2)
            this.numeroDeEmprestimos++ ;
        else throw new foraDeEstoqueException("Usuário não pode ter mais de 3 reservas.");
    }

    public void removerUmaReserva() throws foraDeEstoqueException{
        if (numeroDeEmprestimos > 0) {
            this.numeroDeEmprestimos--;
        }
        else throw new foraDeEstoqueException("Usuário não pode ter menos que 3 reservas.");
    }public void adicionarUmEmprestimo() throws foraDeEstoqueException {
        if(numeroDeEmprestimos <= 2)
            this.numeroDeEmprestimos++ ;
        else throw new foraDeEstoqueException("Usuário não pode ter mais de 3 emprestimos.");
    }

    public void removerUmEmprestimo() throws foraDeEstoqueException{
        if (numeroDeEmprestimos > 0) {
            this.numeroDeEmprestimos--;
        }
        else throw new foraDeEstoqueException("Usuário não pode ter menos de 3 emprestimos.");
    }

    public List<Emprestimo> getHistoricoEmprestimos() {
        return historicoEmprestimos;
    }

    public void adicionarEmprestimoNoHistorico(Emprestimo emprestimo) {
        this.historicoEmprestimos.add(emprestimo);
    }

}

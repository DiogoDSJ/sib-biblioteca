package model.entities;

import dao.DAO;
import exceptions.*;
import model.entities.enums.Cargo;

import java.time.LocalDate;
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


    public void fazerReserva(String isbnLivro) throws objetoInexistenteException, objetoDuplicadoException, naoEncontradoException, usuarioBloqueadoException, foraDeEstoqueException{
        Leitor leitor = DAO.getLeitorDAO().findByPk(this.getId());
        Livro livro = DAO.getLivroDAO().findByIsbn(isbnLivro);
        if (livro == null) {
            throw new naoEncontradoException("Livro não existe.");
        }
        else if(this.getNumeroDeReservas() == 0){
            throw new foraDeEstoqueException("Usuário alcançou o máximo de reservas.");
        }
        else if(Sistema.checarSeOUsuarioTemOLivro(leitor, isbnLivro)) throw new objetoDuplicadoException("Usuário não pode ter dois livros iguais.");
        else if(Sistema.checarSeOUsuarioReservouOLivro(leitor, isbnLivro)) throw new objetoDuplicadoException("Usuário não pode reservar outro livro igual.");
        Sistema.updateMultas(); // colocar no beforeEach || Atualizo as multas para remover multas que já foram pagas e não atrapalha na checagem de atraso
        if (Sistema.checarSeHaAtrasoLeitor(leitor)) { // dois casos : ele já esta multado ou precisa ser multado.
            throw new usuarioBloqueadoException("Usuário em atraso.");
        }
        Reserva reserva = new Reserva(this.getId(), isbnLivro);
        DAO.getReservaDAO().create(reserva);
        leitor.removerUmaReserva();
        DAO.getLeitorDAO().update(leitor);
    }

    public void removerReserva(String isbnLivro) throws naoEncontradoException, foraDeEstoqueException{
        Leitor leitor = DAO.getLeitorDAO().findByPk(this.getId());
        if(DAO.getLivroDAO().findByIsbn(isbnLivro) == null) throw new naoEncontradoException("Livro não existe");
        boolean checkvar = false;
        List<Reserva> reservaList = DAO.getReservaDAO().findByIdReservante(this.getId());
        for (Reserva reserva: reservaList) {
            if(reserva.getIsbnLivro().equals(isbnLivro)){
                DAO.getReservaDAO().delete(reserva);
                leitor.adicionarUmaReserva();
                DAO.getLeitorDAO().update(leitor);
                checkvar = true;
                break;
            }
        }
        if (!checkvar) throw new naoEncontradoException("Não há uma reserva com esse livro.");
    }

    public void renovarEmprestimo(String isbnLivro) throws usuarioPendenciasException, objetoInexistenteException {
        Leitor leitor = DAO.getLeitorDAO().findByPk(this.getId());
        Emprestimo emprestimo = Sistema.buscarEmprestimoDoLeitor(leitor, isbnLivro);
        if(emprestimo == null)
            throw new objetoInexistenteException("Não há emprestimo com esse livro.");
        if (Sistema.checarSeHaAtrasoLeitor(leitor))
            throw new usuarioPendenciasException("Usuário em atraso, não é possivel renovar.");
        else if ((emprestimo.getDataFim().compareTo(emprestimo.getDataInicio())) > 7) {
            throw new usuarioPendenciasException("Esse empréstimo já alcançou o limite de renovações.");
        }
        else {
            emprestimo.setDataFim(LocalDate.now().plusDays(7));
        }
    }

}

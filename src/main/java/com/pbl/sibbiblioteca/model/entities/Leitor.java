package com.pbl.sibbiblioteca.model.entities;

import com.pbl.sibbiblioteca.dao.DAO;
import com.pbl.sibbiblioteca.exceptions.*;
import com.pbl.sibbiblioteca.model.entities.enums.Cargo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 * Classe Leitor que é uma sub-classe da classe usuário, ela é responsavel por guardar as informações do leitor, seus a-
 * tributos próprios de numéro de emprestimos, número de reservas e o histórico de empréstimos do leitor.
 */
public class Leitor extends Usuario {

    private String numeroDeEmprestimos; // refactor para string mais tarde.

    private String numeroDeReservas;
    private final List<Emprestimo> historicoEmprestimos;

    /**
     * Construtor padrão
     *
     * Aqui é definido os atributos normais da classe usuário em conjunto com os números de emprestimos e de reservas
     * fixos em um valor do padrão de projeto.
     * @param nome Nome do leitor.
     * @param endereco Endereço do leitor.
     * @param telefone Telefone do leitor.
     * @param usuario Usuario do leitor.
     * @param senhaDeAcesso Senha do leitor.
     */
    public Leitor(String nome, String endereco, String telefone, String usuario, String senhaDeAcesso) {
        super(nome, endereco, telefone, usuario, senhaDeAcesso);
        this.numeroDeEmprestimos = "3";
        this.numeroDeReservas = "3";
        this.setCargo(Cargo.LEITOR);
        this.historicoEmprestimos = new ArrayList<>();
    }

    public String getNumeroDeEmprestimos(){
        return numeroDeEmprestimos;
    }

    public String getNumeroDeReservas() {
        return numeroDeReservas;
    }

    /**
     * Soma um na quantidade de reservas do leitor.
     * @throws foraDeEstoqueException Se o saldo de reservas já alcançou seu máximo.
     */
    public void adicionarUmaReserva() throws foraDeEstoqueException {
        int numeroDeReservasInt = Integer.parseInt(this.numeroDeReservas);
        if(numeroDeReservasInt <= 2)
            numeroDeReservasInt++ ;
        else throw new foraDeEstoqueException("Usuário não pode ter saldo de reservas maior que 3.");
        this.numeroDeReservas = Integer.toString(numeroDeReservasInt);
    }

    /**
     * Subtrai um na quantidade de reservas do leitor.
     * @throws foraDeEstoqueException Se o saldo de reservas já alcançou seu mínimo.
     */
    public void removerUmaReserva() throws foraDeEstoqueException{
        int numeroDeReservasInt = Integer.parseInt(this.numeroDeReservas);
        if (numeroDeReservasInt > 0) {
            numeroDeReservasInt--;
        }
        else throw new foraDeEstoqueException("Usuário não pode ter saldo de reservas menor que 0.");
        this.numeroDeReservas = Integer.toString(numeroDeReservasInt);
    }

    /**
     * Soma um na quantidade de empréstimos do leitor.
     * @throws foraDeEstoqueException Se o saldo de empréstimos já alcançou seu máximo.
     */
    public void adicionarUmEmprestimo() throws foraDeEstoqueException {
        int numeroDeEmprestimosInt = Integer.parseInt(this.numeroDeEmprestimos);
        if(numeroDeEmprestimosInt <= 2)
            numeroDeEmprestimosInt++;
        else throw new foraDeEstoqueException("Usuário não pode ter saldo de emprestimos maior que 3.");
        this.numeroDeEmprestimos = Integer.toString(numeroDeEmprestimosInt);
    }

    /**
     * Subtrai um na quantidade de empréstimos do leitor.
     * @throws foraDeEstoqueException Se o saldo de empréstimos já alcançou seu mínimo.
     */
    public void removerUmEmprestimo() throws foraDeEstoqueException{
        int numeroDeEmprestimosInt = Integer.parseInt(this.numeroDeEmprestimos);
        if (numeroDeEmprestimosInt > 0) {
            numeroDeEmprestimosInt--;
        }
        else throw new foraDeEstoqueException("Usuário não pode ter saldo de emprestimos menor que 0.");
        this.numeroDeEmprestimos = Integer.toString(numeroDeEmprestimosInt);
    }

    public List<Emprestimo> getHistoricoEmprestimos() {
        return historicoEmprestimos;
    }

    /**
     * Adiciona um empréstimo na lista de histórico de emprestimos toda vez que o usuário fizer um empréstimo.
     * @param emprestimo objeto que será adicionado a lista de históricos de empréstimos.
     */
    public void adicionarEmprestimoNoHistorico(Emprestimo emprestimo) {
        this.historicoEmprestimos.add(emprestimo);
    }

    /**
     * Função de fazer reserva de um Livro, o usuário não conseguira fazer a reserva se seu numero de reservas já chegou
     * ao máximo, se ele já tem o livro por empréstimo, se ele já reservou o mesmo livro
     * ou se o leitor estiver em atraso.
     * @param isbnLivro ISBN do livro que será usado para fazer a busca do livro que será usado para reserva.
     * @throws objetoInexistenteException Se o leitor não existir.
     * @throws objetoDuplicadoException Se o leitor já tem ou já reservou esse livro.
     * @throws naoEncontradoException  Se o livro não for encontrado.
     * @throws usuarioBloqueadoException Se o usuário estiver em atraso.
     * @throws foraDeEstoqueException  Se seu número de reservas já alcançou o máximo.
     */

    public void fazerReserva(String isbnLivro) throws objetoInexistenteException, objetoDuplicadoException, naoEncontradoException, usuarioBloqueadoException, foraDeEstoqueException{
        Leitor leitor = DAO.getLeitorDAO().findByPk(this.getId());
        Livro livro = DAO.getLivroDAO().findByIsbn(isbnLivro);
        if (livro == null) {
            throw new naoEncontradoException("Livro não existe.");
        }
        else if(this.getNumeroDeReservas().equals("0")){
            throw new foraDeEstoqueException("Usuário alcançou o máximo de reservas.");
        }
        else if(Sistema.checarSeOUsuarioTemOLivro(leitor, isbnLivro)) throw new objetoDuplicadoException("Usuário não pode ter dois livros iguais.");
        else if(Sistema.checarSeOUsuarioReservouOLivro(leitor, isbnLivro)) throw new objetoDuplicadoException("Usuário não pode reservar outro livro igual.");
        if (Sistema.checarSeHaAtrasoLeitor(leitor)) {
            throw new usuarioBloqueadoException("Usuário em atraso.");
        }
        Reserva reserva = new Reserva(this.getId(), isbnLivro);
        DAO.getReservaDAO().create(reserva);
        leitor.removerUmaReserva();
        DAO.getLeitorDAO().update(leitor);
    }

    /**
     * Função de remover reserva, caso exista uma reserva daquele livro nesse leitor, ela será removida, caso contrário
     * será lançada uma exception.
     * @param isbnLivro ISBN do livro que será usado para fazer a busca do livro que será usado para reserva.
     * @throws naoEncontradoException  Se o livro não for encontrado.
     * @throws foraDeEstoqueException  Se o número de reservas ultrapassar o máximo.
     */
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

    /**
     * Método que renova um livro que o leitor esteja em mãos caso esse não esteja em atraso e o limite de renovações
     * não foi atingido.
     * @param isbnLivro ISBN do livro em que será feito a busca no DAO para ser tentar renovar.
     * @throws usuarioPendenciasException Se o usuário estiver em atraso.
     * @throws objetoInexistenteException Se o empréstimo com esse livro não existir ou não for encontrado.
     */
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
            DAO.getEmprestimoDAO().update(emprestimo);
        }
    }

}

package model.entities;

import model.entities.enums.Cargo;
import model.entities.enums.StatusConta;

/**
 * Classe abstrata que passa os campos de nome, endereço, telefone, id, statusDaConta, usuario, senha e cargo para
 * as sub-classes Administrador, Usuário e Bibliotecário.
 * A classe herda da classe convidado o método de pesquisa de livros.
 */
public abstract class Usuario extends Convidado {

    private String nome;
    private String endereco;
    private String telefone;
    private String id;
    private StatusConta statusDaConta;
    private String usuario;
    private String senhaDeAcesso;
    private Cargo cargo;

    /**
     * Construtor da padrão da classe usuário, o ID é definido pelo DAO e o status da conta vem sempre desbloqueado.
     * @param nome Nome que será vinculado ao usuário.
     * @param endereco endereço que será vinculado ao usuário.
     * @param telefone telefone que será vinculado ao usuário.
     * @param usuario usuário que será vinculado ao usuário.
     * @param senhaDeAcesso senha que será vinculado ao usuário.
     */
    public Usuario(String nome, String endereco, String telefone, String usuario, String senhaDeAcesso) {
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.statusDaConta = StatusConta.DESBLOQUEADA;
        this.usuario = usuario;
        this.senhaDeAcesso = senhaDeAcesso;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public StatusConta getStatusDaConta() {
        return statusDaConta;
    }

    public void bloquearConta(){
        this.statusDaConta = StatusConta.BLOQUEADA;
    }
    public void desbloquearConta(){
        this.statusDaConta = StatusConta.DESBLOQUEADA;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenhaDeAcesso() {
        return senhaDeAcesso;
    }

    public void setSenhaDeAcesso(String senhaDeAcesso) {
        this.senhaDeAcesso = senhaDeAcesso;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }
}

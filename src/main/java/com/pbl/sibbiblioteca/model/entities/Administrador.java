package com.pbl.sibbiblioteca.model.entities;

import com.pbl.sibbiblioteca.dao.DAO;
import com.pbl.sibbiblioteca.exceptions.*;
import com.pbl.sibbiblioteca.model.entities.enums.Cargo;

import java.time.LocalDate;
import java.util.List;

/**
 * Sub-classe Administrador que herda da classe bibliotecario.
 * Classe administradora do sistema, contém todas as funções que um bibliotecário tem.
 * A classe tem funções que envolvem o manipulamento da classe livro e as classes herdadas das classes usuário.
 * A classe manipula esses dados usando o DAO.
 */
public class Administrador extends Bibliotecario {

    /**
     * Construtor do administrador
     *
     * @param nome Nome do administrador.
     * @param endereco Endereço do administrador.
     * @param telefone Telefone do administrador.
     * @param usuario Usuário do administrador.
     * @param senhaDeAcesso Senha do administrador.
     */
    public Administrador(String nome, String endereco, String telefone, String usuario, String senhaDeAcesso) {
        super(nome, endereco, telefone, usuario, senhaDeAcesso);
        this.setCargo(Cargo.ADMINISTRADOR);
    }

    /**
     * Função de cadastrar um usuário*
     * Essa função cria um usuário de acordo com o cargo passado, que pode ser leitor, bibliotecário ou administrador e
     * passa diretamente para o DAO.
     * @param nome Nome do usuário.
     * @param endereco Endereço do usuário.
     * @param telefone Telefone do usuário.
     * @param usuario Usuário que o usuário irá usar para acessar.
     * @param senhaDeAcesso Senha do usuário.
     * @param cargo Cargo do usuário.
     * @throws cargoInvalidoException Caso o cargo passado não esteja implementado.
     */
    public void cadastrarUsuario(String nome, String endereco, String telefone, String usuario, String senhaDeAcesso, Cargo cargo) throws cargoInvalidoException {
        if (cargo == Cargo.LEITOR) {
            DAO.getLeitorDAO().create(new Leitor(nome, endereco, telefone, usuario, senhaDeAcesso));
        } else if (cargo == Cargo.BIBLIOTECARIO) {
            DAO.getBibliotecarioDAO().create(new Bibliotecario(nome, endereco, telefone, usuario, senhaDeAcesso));
        } else if (cargo == Cargo.ADMINISTRADOR) {
            DAO.getAdministradorDAO().create(new Administrador(nome, endereco, telefone, usuario, senhaDeAcesso));
        } else {
            throw new cargoInvalidoException("Cargo não existe.");
        }
    }

    /**
     * Função de atualizar usuário.
     * Classe que recebe um objeto da classe usuário, e atualiza esse objeto no DAO.
     * @param usuario Objeto da classe usuário, podendo esse ser um leitor, bibliotecário ou administrador.
     * @throws objetoInexistenteException Caso o objeto não exista.
     */
    public void atualizarUsuario(Usuario usuario) throws objetoInexistenteException {
        if (usuario == null) throw new objetoInexistenteException("Usuário não existe");
        else if (usuario.getCargo().equals(Cargo.LEITOR)) {
            DAO.getLeitorDAO().update((Leitor) usuario);
        } else if (usuario.getCargo().equals(Cargo.BIBLIOTECARIO)) {
            DAO.getBibliotecarioDAO().update((Bibliotecario) usuario);
        } else if (usuario.getCargo().equals(Cargo.ADMINISTRADOR)) {
            DAO.getAdministradorDAO().update((Administrador) usuario);
        }

    }

    /**
     * Função que remove um leitor do DAO, caso o leitor tenha pendências ele não pode ser removido.
     * @param id ID do leitor.
     * @throws naoEncontradoException Caso o leitor não seja encontrado.
     * @throws usuarioPendenciasException Caso o usuário tenha pendências.
     * @throws foraDeEstoqueException Lançado pelo metodo de atualizar as reservas.
     */
    public void removerLeitor(String id) throws naoEncontradoException, usuarioPendenciasException, foraDeEstoqueException {
        Leitor leitor = DAO.getLeitorDAO().findByPk(id);
        if (leitor == null) throw new naoEncontradoException("Leitor não existe.");
        else if (!DAO.getEmprestimoDAO().findByIdMutuario(id).isEmpty())
            throw new usuarioPendenciasException("Usuário tem pendências, não pode ser removido.");
        else {
            List<Reserva> reservas = DAO.getReservaDAO().findByIdReservante(id);
            for (Reserva reserva : reservas) {
                DAO.getReservaDAO().delete(reserva);
            }
            DAO.getMultaDAO().delete(DAO.getMultaDAO().findByIdMutuario(id));
            DAO.getLeitorDAO().delete(leitor);
            Sistema.atualizarReservas();
        }
    }

    /**
     * Função que remove um bibliotecário do DAO.
     *
     * @param id ID do bibliotecário.
     * @throws naoEncontradoException Caso o bibliotecário não seja encontrado.
     */
    public void removerBibliotecario(String id) throws naoEncontradoException {
        Bibliotecario bibliotecario = DAO.getBibliotecarioDAO().findByPk(id);
        if (bibliotecario == null) throw new naoEncontradoException("Bibliotecário não existe.");
        DAO.getBibliotecarioDAO().delete(bibliotecario);
    }
    /**
     * Função que remove um administrador do DAO.
     *
     * @param id ID do administrador.
     * @throws naoEncontradoException Caso o administrador não seja encontrado.
     */
    public void removerAdministrador(String id) throws naoEncontradoException {
        Administrador administrador = DAO.getAdministradorDAO().findByPk(id);
        if (administrador == null) throw new naoEncontradoException("Administrador não existe.");
        DAO.getAdministradorDAO().delete(administrador);
    }

    public void trocarNomeDoLeitor(String usuario, String novonome) throws naoEncontradoException, objetoInexistenteException {
        Leitor leitor = DAO.getLeitorDAO().findByUsuario(usuario);
        if (leitor == null) throw new naoEncontradoException("Leitor não existe.");
        leitor.setNome(novonome);
        atualizarUsuario(leitor);
    }


    /**
     * Função que recebe o atual usuário de um leitor e troca para um novo.
     * @param usuario Usuário atual do Leitor.
     * @param novousuario Usuário novo do Leitor.
     * @throws naoEncontradoException Caso o leitor não seja encontrado.
     * @throws objetoInexistenteException Lançado pelo método de atualizar usuário.
     */
    public void trocarUsuarioDoLeitor(String usuario, String novousuario) throws naoEncontradoException, objetoInexistenteException {
        Leitor leitor = DAO.getLeitorDAO().findByUsuario(usuario);
        if (leitor == null) throw new naoEncontradoException("Leitor não existe.");
        leitor.setUsuario(novousuario);
        atualizarUsuario(leitor);
    }
    /**
     * Função que recebe o atual usuário para fazer a busca de um leitor e troca a senha desse leitor para um nova.
     * @param usuario Usuário atual do Leitor.
     * @param novasenha Senha nova do Leitor.
     * @throws naoEncontradoException Caso o leitor não seja encontrado.
     * @throws objetoInexistenteException Lançado pelo método de atualizar usuário.
     */
    public void trocarSenhaDoLeitor(String usuario, String novasenha) throws naoEncontradoException, objetoInexistenteException {
        Leitor leitor = DAO.getLeitorDAO().findByUsuario(usuario);
        if (leitor == null) throw new naoEncontradoException("Leitor não existe.");
        leitor.setSenhaDeAcesso(novasenha);
        atualizarUsuario(leitor);
    }
    /**
     * Função que recebe o atual usuário para fazer a busca de um leitor e troca o telefone desse leitor para um novo.
     * @param usuario Usuário atual do Leitor.
     * @param novotelefone Telefone novo do Leitor.
     * @throws naoEncontradoException Caso o leitor não seja encontrado.
     * @throws objetoInexistenteException Lançado pelo método de atualizar usuário.
     */
    public void trocarTelefoneDoLeitor(String usuario, String novotelefone) throws naoEncontradoException, objetoInexistenteException {
        Leitor leitor = DAO.getLeitorDAO().findByUsuario(usuario);
        if (leitor == null) throw new naoEncontradoException("Leitor não existe.");
        leitor.setTelefone(novotelefone);
        atualizarUsuario(leitor);
    }

    /**
     * Função que recebe o atual usuário para fazer a busca de um leitor e troca o endereço desse leitor para um novo.
     * @param usuario Usuário atual do Leitor.
     * @param novoendereco Endereço novo do Leitor.
     * @throws naoEncontradoException Caso o leitor não seja encontrado.
     * @throws objetoInexistenteException Lançado pelo método de atualizar usuário.
     */
    public void trocarEnderecoDoLeitor(String usuario, String novoendereco) throws naoEncontradoException, objetoInexistenteException {
        Leitor leitor = DAO.getLeitorDAO().findByUsuario(usuario);
        if (leitor == null) throw new naoEncontradoException("Leitor não existe.");
        leitor.setEndereco(novoendereco);
        atualizarUsuario(leitor);
    }

    public void trocarNomeDoBibliotecario(String usuario, String novonome) throws naoEncontradoException, objetoInexistenteException {
        Bibliotecario bibliotecario = DAO.getBibliotecarioDAO().findByUsuario(usuario);
        if (bibliotecario == null) throw new naoEncontradoException("Bibliotecário não existe.");
        bibliotecario.setNome(novonome);
        atualizarUsuario(bibliotecario);
    }

    /**
     * Função que recebe o atual usuário de um bibliotecario troca para um novo.
     * @param usuario Usuário atual do bibliotecario.
     * @param novousuario Usuário novo do bibliotecario.
     * @throws naoEncontradoException Caso o bibliotecario não seja encontrado.
     * @throws objetoInexistenteException Lançado pelo método de atualizar usuário.
     */
    public void trocarUsuarioDoBibliotecario(String usuario, String novousuario) throws naoEncontradoException, objetoInexistenteException {
        Bibliotecario bibliotecario = DAO.getBibliotecarioDAO().findByUsuario(usuario);
        if (bibliotecario == null) throw new naoEncontradoException("Bibliotecário não existe.");
        bibliotecario.setUsuario(novousuario);
        atualizarUsuario(bibliotecario);
    }
    /**
     * Função que recebe o atual usuário para fazer a busca de um bibliotecario e troca a senha desse bibliotecario para um nova.
     * @param usuario Usuário atual do bibliotecario.
     * @param novasenha Senha nova do bibliotecario.
     * @throws naoEncontradoException Caso o bibliotecario não seja encontrado.
     * @throws objetoInexistenteException Lançado pelo método de atualizar usuário.
     */
    public void trocarSenhaDoBibliotecario(String usuario, String novasenha) throws naoEncontradoException, objetoInexistenteException {
        Bibliotecario bibliotecario = DAO.getBibliotecarioDAO().findByUsuario(usuario);
        if (bibliotecario == null) throw new naoEncontradoException("Bibliotecário não existe.");
        bibliotecario.setSenhaDeAcesso(novasenha);
        atualizarUsuario(bibliotecario);
    }
    /**
     * Função que recebe o atual usuário para fazer a busca de um bibliotecario e troca o telefone desse bibliotecario para um novo.
     * @param usuario Usuário atual do bibliotecario.
     * @param novotelefone Telefone novo do bibliotecario.
     * @throws naoEncontradoException Caso o bibliotecario não seja encontrado.
     * @throws objetoInexistenteException Lançado pelo método de atualizar usuário.
     */
    public void trocarTelefoneDoBibliotecario(String usuario, String novotelefone) throws naoEncontradoException, objetoInexistenteException {
        Bibliotecario bibliotecario = DAO.getBibliotecarioDAO().findByUsuario(usuario);
        if (bibliotecario == null) throw new naoEncontradoException("Bibliotecário não existe.");
        bibliotecario.setTelefone(novotelefone);
        atualizarUsuario(bibliotecario);
    }
    /**
     * Função que recebe o atual usuário para fazer a busca de um bibliotecario e troca o endereço desse bibliotecario para um novo.
     * @param usuario Usuário atual do bibliotecario.
     * @param novoendereco Endereço novo do bibliotecario.
     * @throws naoEncontradoException Caso o bibliotecario não seja encontrado.
     * @throws objetoInexistenteException Lançado pelo método de atualizar usuário.
     */
    public void trocarEnderecoDoBibliotecario(String usuario, String novoendereco) throws naoEncontradoException, objetoInexistenteException {
        Bibliotecario bibliotecario = DAO.getBibliotecarioDAO().findByUsuario(usuario);
        if (bibliotecario == null) throw new naoEncontradoException("Bibliotecário não existe.");
        bibliotecario.setEndereco(novoendereco);
        atualizarUsuario(bibliotecario);
    }
    public void trocarNomeDoAdministrador(String usuario, String novonome) throws naoEncontradoException, objetoInexistenteException {
        Administrador administrador = DAO.getAdministradorDAO().findByUsuario(usuario);
        if (administrador == null) throw new naoEncontradoException("Administrador não existe.");
        administrador.setNome(novonome);
        atualizarUsuario(administrador);
    }
    /**
     * Função que recebe o atual usuário de um administrador troca para um novo.
     * @param usuario Usuário atual do administrador.
     * @param novousuario Usuário novo do administrador.
     * @throws naoEncontradoException Caso o administrador não seja encontrado.
     * @throws objetoInexistenteException Lançado pelo método de atualizar usuário.
     */
    public void trocarUsuarioDoAdministrador(String usuario, String novousuario) throws naoEncontradoException, objetoInexistenteException {
        Administrador administrador = DAO.getAdministradorDAO().findByUsuario(usuario);
        if (administrador == null) throw new naoEncontradoException("Administrador não existe.");
        administrador.setUsuario(novousuario);
        atualizarUsuario(administrador);
    }
    /**
     * Função que recebe o atual usuário para fazer a busca de um administrador e troca a senha desse administrador para um nova.
     * @param usuario Usuário atual do administrador.
     * @param novasenha Senha nova do administrador.
     * @throws naoEncontradoException Caso o administrador não seja encontrado.
     * @throws objetoInexistenteException Lançado pelo método de atualizar usuário.
     */
    public void trocarSenhaDoAdministrador(String usuario, String novasenha) throws naoEncontradoException, objetoInexistenteException {
        Administrador administrador = DAO.getAdministradorDAO().findByUsuario(usuario);
        if (administrador == null) throw new naoEncontradoException("Administrador não existe.");
        administrador.setSenhaDeAcesso(novasenha);
        atualizarUsuario(administrador);
    }
    /**
     * Função que recebe o atual usuário para fazer a busca de um administrador e troca o telefone desse administrador para um novo.
     * @param usuario Usuário atual do administrador.
     * @param novotelefone Telefone novo do administrador.
     * @throws naoEncontradoException Caso o administrador não seja encontrado.
     * @throws objetoInexistenteException Lançado pelo método de atualizar usuário.
     */
    public void trocarTelefoneDoAdministrador(String usuario, String novotelefone) throws naoEncontradoException, objetoInexistenteException {
        Administrador administrador = DAO.getAdministradorDAO().findByUsuario(usuario);
        if (administrador == null) throw new naoEncontradoException("Administrador não existe.");
        administrador.setTelefone(novotelefone);
        atualizarUsuario(administrador);
    }
    /**
     * Função que recebe o atual usuário para fazer a busca de um administrador e troca o endereço desse administrador para um novo.
     * @param usuario Usuário atual do administrador.
     * @param novoendereco Endereço novo do administrador.
     * @throws naoEncontradoException Caso o administrador não seja encontrado.
     * @throws objetoInexistenteException Lançado pelo método de atualizar usuário.
     */
    public void trocarEnderecoDoAdministrador(String usuario, String novoendereco) throws naoEncontradoException, objetoInexistenteException {
        Administrador administrador = DAO.getAdministradorDAO().findByUsuario(usuario);
        if (administrador == null) throw new naoEncontradoException("Administrador não existe.");
        administrador.setEndereco(novoendereco);
        atualizarUsuario(administrador);
    }

    /**
     * Função que remove a reserva de um livro de um leitor.
     * @param leitor Objeto leitor que fez a reserva.
     * @param isbnLivro ISBN do livro da reserva.
     * @throws naoEncontradoException Caso o livro não exista ou o livro não esteja reservado.
     * @throws foraDeEstoqueException Lançada pelo metódo de adicionar reserva.
     */
    public void removerReserva(Leitor leitor, String isbnLivro) throws naoEncontradoException, foraDeEstoqueException{
        if(leitor == null) throw new naoEncontradoException("Leitor não existe");
        if(DAO.getLivroDAO().findByIsbn(isbnLivro) == null) throw new naoEncontradoException("Livro não existe");
        boolean checkvar = false;
        List<Reserva> reservaList = DAO.getReservaDAO().findByIdReservante(leitor.getId());
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
     * Função que permite o administrador multar um usuário forçadamente, sem necessariamente haver um atraso, por um x
     * especificos dias, em que também é verificado se o usuário tem algum atraso antes de aplicar esse multa, se houver
     * o usuário é punido primeiro pelo atraso e depois os dias da multa são colocados na soma.
     * @param leitor Objeto que será multado.
     * @param diasDeMulta Dias que o leitor será multado.
     * @throws objetoInexistenteException Caso o objeto leitor não exista.
     */
    public void multarLeitor(Leitor leitor, int diasDeMulta) throws objetoInexistenteException {
        if (leitor == null) throw new objetoInexistenteException("Leitor não existe.");
        if (DAO.getMultaDAO().findByIdMutuario(leitor.getId()) == null) {
            DAO.getMultaDAO().create(new Multa(LocalDate.now(), LocalDate.now().plusDays(diasDeMulta), leitor.getId()));
        } else if (DAO.getMultaDAO().findByIdMutuario(leitor.getId()) != null) {
            DAO.getMultaDAO().findByIdMutuario(leitor.getId()).aumentarMulta(diasDeMulta);
        }
        leitor.bloquearConta();
        DAO.getLeitorDAO().update(leitor);
    }

    /**
     * Remove uma multa de um leitor, caso esse esteja com uma multa.
     * @param leitor Objeto leitor que será desbloqueado.
     * @throws objetoInexistenteException Caso o usuário não exista.
     */

    public void desbloquearLeitor(Leitor leitor) throws objetoInexistenteException {
        if(leitor == null) throw new objetoInexistenteException("Leitor não existe.");
        if(retornarMultaLeitor(leitor) == null) throw new objetoInexistenteException("Leitor não está bloqueado");
        DAO.getMultaDAO().delete(DAO.getMultaDAO().findByIdMutuario(leitor.getId()));
        DAO.getLeitorDAO().findByPk(leitor.getId()).desbloquearConta();
        this.atualizarUsuario(leitor);
    }

    public Multa retornarMultaLeitor(Leitor leitor) {
        return DAO.getMultaDAO().findByIdMutuario(leitor.getId());
    }
}

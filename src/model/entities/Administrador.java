package model.entities;

import dao.DAO;
import exceptions.*;
import model.entities.enums.Cargo;

import java.time.LocalDate;
import java.util.List;

public class Administrador extends Bibliotecario {

    public Administrador(String nome, String endereco, String telefone, String usuario, String senhaDeAcesso) {
        super(nome, endereco, telefone, usuario, senhaDeAcesso);
        this.setCargo(Cargo.ADMINISTRADOR);
    }

    public void cadastrarUsuario(String nome, String endereco, String telefone, String id, String usuario, String senhaDeAcesso, Cargo cargo) throws cargoInvalidoException {
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

    public void removerLeitor(String id) throws objetoInexistenteException, usuarioPendenciasException, foraDeEstoqueException {
        Leitor leitor = DAO.getLeitorDAO().findByPk(id);
        if (leitor == null) throw new objetoInexistenteException("Leitor não existe.");
        else if (DAO.getEmprestimoDAO().findByIdMutuario(id).isEmpty())
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

    public void removerBibliotecario(String id) throws objetoInexistenteException {
        Bibliotecario bibliotecario = DAO.getBibliotecarioDAO().findByPk(id);
        if (bibliotecario == null) throw new objetoInexistenteException("Bibliotecário não existe.");
        DAO.getBibliotecarioDAO().delete(bibliotecario);
    }

    public void removerAdministrador(String id) throws objetoInexistenteException {
        Administrador administrador = DAO.getAdministradorDAO().findByPk(id);
        if (administrador == null) throw new objetoInexistenteException("Administrador não existe.");
        DAO.getBibliotecarioDAO().delete(administrador);
    }

    public void trocarUsuarioDoLeitor(String usuario, String novousuario) throws objetoInexistenteException {
        Leitor leitor = DAO.getLeitorDAO().findByUsuario(usuario);
        if (leitor == null) throw new objetoInexistenteException("Leitor não existe.");
        leitor.setUsuario(novousuario);
        atualizarUsuario(leitor);
    }

    public void trocarSenhaDoLeitor(String usuario, String novasenha) throws objetoInexistenteException {
        Leitor leitor = DAO.getLeitorDAO().findByUsuario(usuario);
        if (leitor == null) throw new objetoInexistenteException("Leitor não existe.");
        leitor.setSenhaDeAcesso(novasenha);
        atualizarUsuario(leitor);
    }

    public void trocarTelefoneDoLeitor(String usuario, String novotelefone) throws objetoInexistenteException {
        Leitor leitor = DAO.getLeitorDAO().findByUsuario(usuario);
        if (leitor == null) throw new objetoInexistenteException("Leitor não existe.");
        leitor.setTelefone(novotelefone);
        atualizarUsuario(leitor);
    }

    public void trocarEnderecoDoLeitor(String usuario, String novoendereco) throws objetoInexistenteException {
        Leitor leitor = DAO.getLeitorDAO().findByUsuario(usuario);
        if (leitor == null) throw new objetoInexistenteException("Leitor não existe.");
        leitor.setTelefone(novoendereco);
        atualizarUsuario(leitor);
    }

    public void trocarUsuarioDoBibliotecario(String usuario, String novousuario) throws objetoInexistenteException {
        Bibliotecario bibliotecario = DAO.getBibliotecarioDAO().findByUsuario(usuario);
        if (bibliotecario == null) throw new objetoInexistenteException("Bibliotecário não existe.");
        bibliotecario.setUsuario(novousuario);
        atualizarUsuario(bibliotecario);
    }

    public void trocarSenhaDoBibliotecario(String usuario, String novasenha) throws objetoInexistenteException {
        Bibliotecario bibliotecario = DAO.getBibliotecarioDAO().findByUsuario(usuario);
        if (bibliotecario == null) throw new objetoInexistenteException("Bibliotecário não existe.");
        bibliotecario.setSenhaDeAcesso(novasenha);
        atualizarUsuario(bibliotecario);
    }

    public void trocarTelefoneDoBibliotecario(String usuario, String novotelefone) throws objetoInexistenteException {
        Bibliotecario bibliotecario = DAO.getBibliotecarioDAO().findByUsuario(usuario);
        if (bibliotecario == null) throw new objetoInexistenteException("Bibliotecário não existe.");
        DAO.getLeitorDAO().findByUsuario(usuario).setTelefone(novotelefone);
        bibliotecario.setTelefone(novotelefone);
        atualizarUsuario(bibliotecario);
    }

    public void trocarEnderecoDoBibliotecario(String usuario, String novoendereco) throws objetoInexistenteException {
        Bibliotecario bibliotecario = DAO.getBibliotecarioDAO().findByUsuario(usuario);
        if (bibliotecario == null) throw new objetoInexistenteException("Bibliotecário não existe.");
        bibliotecario.setEndereco(novoendereco);
        atualizarUsuario(bibliotecario);
    }

    public void trocarUsuarioDoAdministrador(String usuario, String novousuario) throws objetoInexistenteException {
        Administrador administrador = DAO.getAdministradorDAO().findByUsuario(usuario);
        if (administrador == null) throw new objetoInexistenteException("Administrador não existe.");
        administrador.setUsuario(novousuario);
        atualizarUsuario(administrador);
    }

    public void trocarSenhaDoAdministrador(String usuario, String novasenha) throws objetoInexistenteException {
        Administrador administrador = DAO.getAdministradorDAO().findByUsuario(usuario);
        if (administrador == null) throw new objetoInexistenteException("Administrador não existe.");
        administrador.setSenhaDeAcesso(novasenha);
        atualizarUsuario(administrador);
    }

    public void trocarTelefoneDoAdministrador(String usuario, String novotelefone) throws objetoInexistenteException {
        Administrador administrador = DAO.getAdministradorDAO().findByUsuario(usuario);
        if (administrador == null) throw new objetoInexistenteException("Administrador não existe.");
        DAO.getLeitorDAO().findByUsuario(usuario).setTelefone(novotelefone);
        administrador.setTelefone(novotelefone);
        atualizarUsuario(administrador);
    }

    public void trocarEnderecoDoAdministrador(String usuario, String novoendereco) throws objetoInexistenteException {
        Administrador administrador = DAO.getAdministradorDAO().findByUsuario(usuario);
        if (administrador == null) throw new objetoInexistenteException("Administrador não existe.");
        administrador.setEndereco(novoendereco);
        atualizarUsuario(administrador);
    }

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
    public static void multarLeitor(Leitor leitor, int diasAtraso) throws objetoInexistenteException {
        if (leitor == null) throw new objetoInexistenteException("Leitor não existe.");
        List<Emprestimo> emprestimoListLeitor = DAO.getEmprestimoDAO().findByIdMutuario(leitor.getId());
        if (emprestimoListLeitor.isEmpty()) throw new objetoInexistenteException("Usuário não tem empréstimos.");
        if(Sistema.checarSeHaAtrasoLeitor(leitor)){
            Sistema.aplicarMulta(leitor);
        }
        if (DAO.getMultaDAO().findByIdMutuario(leitor.getId()) == null) {
            DAO.getMultaDAO().create(new Multa(LocalDate.now(), LocalDate.now().plusDays(diasAtraso), leitor.getId()));
        } else if (DAO.getMultaDAO().findByIdMutuario(leitor.getId()) != null) {
            DAO.getMultaDAO().findByIdMutuario(leitor.getId()).aumentarMulta(diasAtraso);
        }
    }

    public static void desbloquearLeitor(Leitor leitor) throws objetoInexistenteException{
        if(leitor == null) throw new objetoInexistenteException("Leitor não existe.");
        if(DAO.getMultaDAO().findByIdMutuario(leitor.getId()) == null) throw new objetoInexistenteException("Leitor não está bloqueado");
        DAO.getMultaDAO().delete(DAO.getMultaDAO().findByIdMutuario(leitor.getId()));
        DAO.getLeitorDAO().findByPk(leitor.getId()).desbloquearConta();

    }

}

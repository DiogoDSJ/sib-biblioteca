package model.entities;

import dao.DAO;
import exceptions.cargoInvalidoException;
import exceptions.foraDeEstoqueException;
import exceptions.objetoInexistenteException;
import exceptions.usuarioPendenciasException;
import model.entities.enums.Cargo;

import java.util.List;

public class Administrador extends Bibliotecario {

    public Administrador(String nome, String endereco, String telefone, String usuario, String senhaDeAcesso) {
        super(nome, endereco, telefone, usuario, senhaDeAcesso);
        this.setCargo(Cargo.ADMINISTRADOR);
    }

    public void cadastrarUsuario(String nome, String endereco, String telefone, String id, String usuario, String senhaDeAcesso, Cargo cargo) throws cargoInvalidoException
    {
        if(cargo == Cargo.LEITOR) {
            DAO.getLeitorDAO().create(new Leitor(nome, endereco, telefone, usuario, senhaDeAcesso));
        }
        else if(cargo == Cargo.BIBLIOTECARIO) {
            DAO.getBibliotecarioDAO().create(new Bibliotecario(nome, endereco, telefone, usuario, senhaDeAcesso));
        }
        else if(cargo == Cargo.ADMINISTRADOR) {
            DAO.getAdministradorDAO().create(new Administrador(nome, endereco, telefone, usuario, senhaDeAcesso));
        }
        else{
            throw new cargoInvalidoException("Cargo não existe.");
        }
    }

    public void atualizarUsuario(Usuario usuario) throws objetoInexistenteException{
        if(usuario == null) throw new objetoInexistenteException("Usuário não existe");
        else if(usuario.getCargo().equals(Cargo.LEITOR)) {
            DAO.getLeitorDAO().update((Leitor) usuario);
        }
        else if(usuario.getCargo().equals(Cargo.BIBLIOTECARIO)) {
            DAO.getBibliotecarioDAO().update((Bibliotecario) usuario);
        }
        else if(usuario.getCargo().equals(Cargo.ADMINISTRADOR)){
            DAO.getAdministradorDAO().update((Administrador) usuario);
        }

    }
    public void removerLeitor(String id) throws objetoInexistenteException, usuarioPendenciasException, foraDeEstoqueException {
        Leitor leitor = DAO.getLeitorDAO().findByPk(id);
        if(leitor == null) throw new objetoInexistenteException("Usuário não existe.");
        else if(DAO.getEmprestimoDAO().findByIdMutuario(id).isEmpty()) throw new usuarioPendenciasException("Usuário tem pendências, não pode ser removido.");
        else{
            List<Reserva> reservas = DAO.getReservaDAO().findByIdReservante(id);
            for (Reserva reserva: reservas) {
                DAO.getReservaDAO().delete(reserva);
            }
            DAO.getMultaDAO().delete(DAO.getMultaDAO().findByIdMutuario(id));
            DAO.getLeitorDAO().delete(leitor);
            Sistema.atualizarReservas();
        }
    }

    public void trocarUsuarioDoLeitor(String usuario, String novousuario) throws objetoInexistenteException {
        Leitor leitor = DAO.getLeitorDAO().findByUsuario(usuario);
        if (leitor == null) throw new objetoInexistenteException("Leitor não existe.");
        DAO.getLeitorDAO().findByUsuario(usuario).setUsuario(novousuario);
    }

    public void trocarSenhaDoLeitor(String usuario, String novasenha) throws objetoInexistenteException {
        Leitor leitor = DAO.getLeitorDAO().findByUsuario(usuario);
        if (leitor == null) throw new objetoInexistenteException("Leitor não existe.");
        DAO.getLeitorDAO().findByUsuario(usuario).setSenhaDeAcesso(novasenha);
    }

    public void trocarTelefoneDoLeitor(String usuario, String novotelefone) throws objetoInexistenteException {
        Leitor leitor = DAO.getLeitorDAO().findByUsuario(usuario);
        if (leitor == null) throw new objetoInexistenteException("Leitor não existe.");
        DAO.getLeitorDAO().findByUsuario(usuario).setTelefone(novotelefone);
    }

    public void trocarEnderecoDoLeitor(String usuario, String novoendereco) throws objetoInexistenteException {
        Leitor leitor = DAO.getLeitorDAO().findByUsuario(usuario);
        if (leitor == null) throw new objetoInexistenteException("Leitor não existe.");
        DAO.getLeitorDAO().findByUsuario(usuario).setTelefone(novoendereco);
    }
}

package test.model.entities;

import dao.DAO;
import exceptions.*;
import model.entities.*;
import model.entities.enums.Cargo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdministradorTest {
    Administrador administrador;
    @BeforeEach
    void setUp() {
        this.administrador = new Administrador("Lydia Small","136-1059 In Road","(441) 366-3492","Brenna","CKP12MZT6GD");
        DAO.getAdministradorDAO().create(administrador);
    }
    @AfterEach
    void setEnd() {
        DAO.getBibliotecarioDAO().deleteMany();
        DAO.getLeitorDAO().deleteMany();
        DAO.getAdministradorDAO().deleteMany();
        DAO.getLivroDAO().deleteMany();
        DAO.getEmprestimoDAO().deleteMany();
        DAO.getReservaDAO().deleteMany();
        DAO.getMultaDAO().deleteMany();
    }
    @Test
    void cadastrarUsuario() throws cargoInvalidoException {
        administrador.cadastrarUsuario("Diogo", "Feira 6", "7199524438", "Diogo", "tutu", Cargo.ADMINISTRADOR);
        administrador.cadastrarUsuario("Owen Henderson","Ap #995-7066 Enim. St.","1-467-895-4705","Lacota","XCP46YPN4UG", Cargo.LEITOR);
        administrador.cadastrarUsuario("Breanna Roy","8718 Neque Avenue","1-785-316-5008","Jerry","GPI21GUN3KD", Cargo.BIBLIOTECARIO);
        assertEquals(DAO.getAdministradorDAO().findByPk("1"), administrador);
        assertEquals(DAO.getAdministradorDAO().findByPk("2").getNome(), "Diogo");
        assertEquals(DAO.getLeitorDAO().findByPk("1").getNome(), "Owen Henderson");
        assertEquals(DAO.getBibliotecarioDAO().findByPk("1").getNome(), "Breanna Roy");
        assertNull(DAO.getBibliotecarioDAO().findByPk("2"));
    }

    @Test
    void atualizarUsuario() throws cargoInvalidoException, objetoInexistenteException {
        administrador.cadastrarUsuario("Diogo", "Feira 6", "7199524438", "Diogo", "tutu", Cargo.ADMINISTRADOR);
        Usuario qualquer = DAO.getAdministradorDAO().findByUsuario("Diogo");
        assertEquals(qualquer, DAO.getAdministradorDAO().findByUsuario("Diogo"));
        qualquer.setUsuario("Diogao");
        assertNotEquals(qualquer, DAO.getAdministradorDAO().findByUsuario("Diogo"));
        administrador.atualizarUsuario(qualquer);
        assertEquals(qualquer, DAO.getAdministradorDAO().findByUsuario("Diogao"));
    }

    @Test
    void removerLeitor() throws cargoInvalidoException, usuarioPendenciasException, foraDeEstoqueException, naoEncontradoException {
        administrador.cadastrarUsuario("Diogo", "Feira 6", "7199524438", "Diogo", "tutu", Cargo.LEITOR);
        assertNotNull(DAO.getLeitorDAO().findByUsuario("Diogo"));
        administrador.removerLeitor(DAO.getLeitorDAO().findByUsuario("Diogo").getId());
        assertNull(DAO.getLeitorDAO().findByUsuario("Diogo"));
    }

    @Test
    void removerLeitorPendenciasDeEmprestimo() throws cargoInvalidoException, usuarioPendenciasException, foraDeEstoqueException, naoEncontradoException, usuarioBloqueadoException, objetoInexistenteException, livroReservadoException, objetoDuplicadoException {
        administrador.cadastrarUsuario("Diogo", "Feira 6", "7199524438", "Diogo", "tutu", Cargo.LEITOR);
        assertNotNull(DAO.getLeitorDAO().findByUsuario("Diogo"));
        administrador.adicionarLivro("7598","Beau Barrera","ullamcorper","Aenean Massa Limited", "Ficção", "2020");
        administrador.fazerEmprestimo(DAO.getLeitorDAO().findByUsuario("Diogo").getId(), "7598");
        assertThrows(usuarioPendenciasException.class,() -> administrador.removerLeitor(DAO.getLeitorDAO().findByUsuario("Diogo").getId()));
    }
@Test
    void removerLeitorPendenciasDeReserva() throws cargoInvalidoException, usuarioPendenciasException, foraDeEstoqueException, naoEncontradoException, usuarioBloqueadoException, objetoInexistenteException, livroReservadoException, objetoDuplicadoException {
        administrador.cadastrarUsuario("Diogo", "Feira 6", "7199524438", "Diogo", "tutu", Cargo.LEITOR);
        assertNotNull(DAO.getLeitorDAO().findByUsuario("Diogo"));
        administrador.adicionarLivro("7598","Beau Barrera","ullamcorper","Aenean Massa Limited", "Ficção", "2020");
        DAO.getLeitorDAO().findByUsuario("Diogo").fazerReserva("7598");
        assertNotNull(DAO.getReservaDAO().findByIdReservante(DAO.getLeitorDAO().findByUsuario("Diogo").getId()));
        administrador.removerLeitor(DAO.getLeitorDAO().findByUsuario("Diogo").getId());
        assertNull(DAO.getLeitorDAO().findByUsuario("Diogo"));
    }
    @Test
    void removerBibliotecario() throws cargoInvalidoException, naoEncontradoException {
        administrador.cadastrarUsuario("Diogo", "Feira 6", "7199524438", "Diogo", "tutu", Cargo.BIBLIOTECARIO);
        assertNotNull(DAO.getBibliotecarioDAO().findByUsuario("Diogo"));
        administrador.removerBibliotecario(DAO.getBibliotecarioDAO().findByUsuario("Diogo").getId());
        assertNull(DAO.getBibliotecarioDAO().findByUsuario("Diogo"));

    }


    @Test
    void removerAdministrador() throws cargoInvalidoException, naoEncontradoException {
        administrador.cadastrarUsuario("Diogo", "Feira 6", "7199524438", "Diogo", "tutu", Cargo.ADMINISTRADOR);
        assertNotNull(DAO.getAdministradorDAO().findByUsuario("Diogo"));
        administrador.removerAdministrador(DAO.getAdministradorDAO().findByUsuario("Diogo").getId());
        assertNull(DAO.getAdministradorDAO().findByUsuario("Diogo"));
    }

    @Test
    void trocarUsuarioDoLeitor() throws cargoInvalidoException, usuarioPendenciasException, foraDeEstoqueException, naoEncontradoException, objetoInexistenteException {
        administrador.cadastrarUsuario("Diogo", "Feira 6", "7199524438", "Diogo", "tutu", Cargo.LEITOR);
        assertNotNull(DAO.getLeitorDAO().findByUsuario("Diogo"));
        Leitor leitor = DAO.getLeitorDAO().findByUsuario("Diogo");
        administrador.trocarUsuarioDoLeitor(leitor.getUsuario(), "Diogão");
        assertNotNull(leitor);
        assertNotNull(DAO.getLeitorDAO().findByUsuario("Diogão"));
        assertEquals(DAO.getLeitorDAO().findByUsuario("Diogão").getUsuario(), "Diogão");
    }

    @Test
    void trocarSenhaDoLeitor() throws cargoInvalidoException, objetoInexistenteException, naoEncontradoException {
        administrador.cadastrarUsuario("Diogo", "Feira 6", "7199524438", "Diogo", "tutu", Cargo.LEITOR);
        assertNotNull(DAO.getLeitorDAO().findByUsuario("Diogo"));
        Leitor leitor = DAO.getLeitorDAO().findByUsuario("Diogo");
        administrador.trocarSenhaDoLeitor(leitor.getUsuario(), "Diogão");
        assertNotNull(leitor);
        assertNotNull(DAO.getLeitorDAO().findByUsuario("Diogo"));
        assertEquals(DAO.getLeitorDAO().findByUsuario("Diogo").getSenhaDeAcesso(), "Diogão");
    }

    @Test
    void trocarTelefoneDoLeitor() throws objetoInexistenteException, naoEncontradoException, cargoInvalidoException {
        administrador.cadastrarUsuario("Diogo", "Feira 6", "7199524438", "Diogo", "tutu", Cargo.LEITOR);
        assertNotNull(DAO.getLeitorDAO().findByUsuario("Diogo"));
        Leitor leitor = DAO.getLeitorDAO().findByUsuario("Diogo");
        administrador.trocarTelefoneDoLeitor(leitor.getUsuario(), "69878");
        assertNotNull(leitor);
        assertNotNull(DAO.getLeitorDAO().findByUsuario("Diogo"));
        assertEquals(DAO.getLeitorDAO().findByUsuario("Diogo").getTelefone(), "69878");
    }

    @Test
    void trocarEnderecoDoLeitor() throws cargoInvalidoException, objetoInexistenteException, naoEncontradoException {
        administrador.cadastrarUsuario("Diogo", "Feira 6", "7199524438", "Diogo", "tutu", Cargo.LEITOR);
        assertNotNull(DAO.getLeitorDAO().findByUsuario("Diogo"));
        Leitor leitor = DAO.getLeitorDAO().findByUsuario("Diogo");
        administrador.trocarEnderecoDoLeitor(leitor.getUsuario(), "69878");
        assertNotNull(leitor);
        assertNotNull(DAO.getLeitorDAO().findByUsuario("Diogo"));
        assertEquals(DAO.getLeitorDAO().findByUsuario("Diogo").getEndereco(), "69878");
    }

    @Test
    void trocarUsuarioDoBibliotecario() throws cargoInvalidoException, objetoInexistenteException, naoEncontradoException {
        administrador.cadastrarUsuario("Diogo", "Feira 6", "7199524438", "Diogo", "tutu", Cargo.BIBLIOTECARIO);
        assertNotNull(DAO.getBibliotecarioDAO().findByUsuario("Diogo"));
        Bibliotecario bibliotecario = DAO.getBibliotecarioDAO().findByUsuario("Diogo");
        administrador.trocarUsuarioDoBibliotecario(bibliotecario.getUsuario(), "Diogão");
        assertNotNull(bibliotecario);
        assertNotNull(DAO.getBibliotecarioDAO().findByUsuario("Diogão"));
        assertEquals(DAO.getBibliotecarioDAO().findByUsuario("Diogão").getUsuario(), "Diogão");
    }

    @Test
    void trocarSenhaDoBibliotecario() throws cargoInvalidoException, objetoInexistenteException, naoEncontradoException {
        administrador.cadastrarUsuario("Diogo", "Feira 6", "7199524438", "Diogo", "tutu", Cargo.BIBLIOTECARIO);
        assertNotNull(DAO.getBibliotecarioDAO().findByUsuario("Diogo"));
        Bibliotecario bibliotecario = DAO.getBibliotecarioDAO().findByUsuario("Diogo");
        administrador.trocarSenhaDoBibliotecario(bibliotecario.getUsuario(), "Diogão");
        assertNotNull(bibliotecario);
        assertNotNull(DAO.getBibliotecarioDAO().findByUsuario("Diogo"));
        assertEquals(DAO.getBibliotecarioDAO().findByUsuario("Diogo").getSenhaDeAcesso(), "Diogão");
    }

    @Test
    void trocarTelefoneDoBibliotecario() throws objetoInexistenteException, naoEncontradoException, cargoInvalidoException {
        administrador.cadastrarUsuario("Diogo", "Feira 6", "7199524438", "Diogo", "tutu", Cargo.BIBLIOTECARIO);
        assertNotNull(DAO.getBibliotecarioDAO().findByUsuario("Diogo"));
        Bibliotecario bibliotecario = DAO.getBibliotecarioDAO().findByUsuario("Diogo");
        administrador.trocarTelefoneDoBibliotecario(bibliotecario.getUsuario(), "321312");
        assertNotNull(bibliotecario);
        assertNotNull(DAO.getBibliotecarioDAO().findByUsuario("Diogo"));
        assertEquals(DAO.getBibliotecarioDAO().findByUsuario("Diogo").getTelefone(), "321312");
    }

    @Test
    void trocarEnderecoDoBibliotecario() throws objetoInexistenteException, naoEncontradoException, cargoInvalidoException {
        administrador.cadastrarUsuario("Diogo", "Feira 6", "7199524438", "Diogo", "tutu", Cargo.BIBLIOTECARIO);
        assertNotNull(DAO.getBibliotecarioDAO().findByUsuario("Diogo"));
        Bibliotecario bibliotecario = DAO.getBibliotecarioDAO().findByUsuario("Diogo");
        administrador.trocarEnderecoDoBibliotecario(bibliotecario.getUsuario(), "321312");
        assertNotNull(bibliotecario);
        assertNotNull(DAO.getBibliotecarioDAO().findByUsuario("Diogo"));
        assertEquals(DAO.getBibliotecarioDAO().findByUsuario("Diogo").getEndereco(), "321312");
    }

    @Test
    void trocarUsuarioDoAdministrador() throws cargoInvalidoException, objetoInexistenteException, naoEncontradoException {
        administrador.cadastrarUsuario("Diogo", "Feira 6", "7199524438", "Diogo", "tutu", Cargo.ADMINISTRADOR);
        assertNotNull(DAO.getAdministradorDAO().findByUsuario("Diogo"));
        Administrador administrador1 = DAO.getAdministradorDAO().findByUsuario("Diogo");
        administrador.trocarUsuarioDoAdministrador(administrador1.getUsuario(), "Diogão");
        assertNotNull(administrador1);
        assertNotNull(DAO.getAdministradorDAO().findByUsuario("Diogão"));
        assertEquals(DAO.getAdministradorDAO().findByUsuario("Diogão").getUsuario(), "Diogão");
    }

    @Test
    void trocarSenhaDoAdministrador() throws cargoInvalidoException, objetoInexistenteException, naoEncontradoException {
        administrador.cadastrarUsuario("Diogo", "Feira 6", "7199524438", "Diogo", "tutu", Cargo.ADMINISTRADOR);
        assertNotNull(DAO.getAdministradorDAO().findByUsuario("Diogo"));
        Administrador administrador1 = DAO.getAdministradorDAO().findByUsuario("Diogo");
        administrador.trocarSenhaDoAdministrador(administrador1.getUsuario(), "teste");
        assertNotNull(administrador1);
        assertNotNull(DAO.getAdministradorDAO().findByUsuario("Diogo"));
        assertEquals(DAO.getAdministradorDAO().findByUsuario("Diogo").getSenhaDeAcesso(), "teste");
    }

    @Test
    void trocarTelefoneDoAdministrador() throws cargoInvalidoException, objetoInexistenteException, naoEncontradoException {
        administrador.cadastrarUsuario("Diogo", "Feira 6", "7199524438", "Diogo", "tutu", Cargo.ADMINISTRADOR);
        assertNotNull(DAO.getAdministradorDAO().findByUsuario("Diogo"));
        Administrador administrador1 = DAO.getAdministradorDAO().findByUsuario("Diogo");
        administrador.trocarTelefoneDoAdministrador(administrador1.getUsuario(), "teste");
        assertNotNull(administrador1);
        assertNotNull(DAO.getAdministradorDAO().findByUsuario("Diogo"));
        assertEquals(DAO.getAdministradorDAO().findByUsuario("Diogo").getTelefone(), "teste");
    }

    @Test
    void trocarEnderecoDoAdministrador() throws cargoInvalidoException, objetoInexistenteException, naoEncontradoException {
        administrador.cadastrarUsuario("Diogo", "Feira 6", "7199524438", "Diogo", "tutu", Cargo.ADMINISTRADOR);
        assertNotNull(DAO.getAdministradorDAO().findByUsuario("Diogo"));
        Administrador administrador1 = DAO.getAdministradorDAO().findByUsuario("Diogo");
        administrador.trocarEnderecoDoAdministrador(administrador1.getUsuario(), "teste");
        assertNotNull(administrador1);
        assertNotNull(DAO.getAdministradorDAO().findByUsuario("Diogo"));
        assertEquals(DAO.getAdministradorDAO().findByUsuario("Diogo").getEndereco(), "teste");
    }

    @Test
    void removerReserva() throws cargoInvalidoException, usuarioBloqueadoException, objetoInexistenteException, foraDeEstoqueException, naoEncontradoException, objetoDuplicadoException {
        administrador.cadastrarUsuario("Diogo", "Feira 6", "7199524438", "Diogo", "tutu", Cargo.LEITOR);
        assertNotNull(DAO.getLeitorDAO().findByUsuario("Diogo"));
        administrador.adicionarLivro("7598","Beau Barrera","ullamcorper","Aenean Massa Limited", "Ficção", "2020");
        DAO.getLeitorDAO().findByUsuario("Diogo").fazerReserva("7598");
        assertFalse(DAO.getReservaDAO().findByIdReservante(DAO.getLeitorDAO().findByUsuario("Diogo").getId()).isEmpty());
        administrador.removerReserva(DAO.getLeitorDAO().findByUsuario("Diogo"),  "7598");
        assertTrue(DAO.getReservaDAO().findByIdReservante(DAO.getLeitorDAO().findByUsuario("Diogo").getId()).isEmpty());

    }

    @Test
    void multarLeitor() throws cargoInvalidoException, objetoInexistenteException {
        administrador.cadastrarUsuario("Diogo", "Feira 6", "7199524438", "Diogo", "tutu", Cargo.LEITOR);
        assertNotNull(DAO.getLeitorDAO().findByUsuario("Diogo"));
        assertNull(DAO.getMultaDAO().findByIdMutuario(DAO.getLeitorDAO().findByUsuario("Diogo").getId()));
        administrador.multarLeitor(DAO.getLeitorDAO().findByUsuario("Diogo"), 10);
        assertNotNull(DAO.getMultaDAO().findByIdMutuario(DAO.getLeitorDAO().findByUsuario("Diogo").getId()));
        Multa multa = DAO.getMultaDAO().findByIdMutuario(DAO.getLeitorDAO().findByUsuario("Diogo").getId());
        System.out.println(multa.getDataInicio());
        System.out.println(multa.getDataFim());
        assertEquals(multa.getDataInicio().plusDays(10), multa.getDataFim());
    }

    @Test
    void desbloquearLeitor() throws cargoInvalidoException, objetoInexistenteException {
        administrador.cadastrarUsuario("Diogo", "Feira 6", "7199524438", "Diogo", "tutu", Cargo.LEITOR);
        assertNotNull(DAO.getLeitorDAO().findByUsuario("Diogo"));
        assertNull(DAO.getMultaDAO().findByIdMutuario(DAO.getLeitorDAO().findByUsuario("Diogo").getId()));
        administrador.multarLeitor(DAO.getLeitorDAO().findByUsuario("Diogo"), 10);
        assertNotNull(DAO.getMultaDAO().findByIdMutuario(DAO.getLeitorDAO().findByUsuario("Diogo").getId()));
        administrador.desbloquearLeitor(DAO.getLeitorDAO().findByUsuario("Diogo"));
        assertNull(DAO.getMultaDAO().findByIdMutuario(DAO.getLeitorDAO().findByUsuario("Diogo").getId()));
    }
}
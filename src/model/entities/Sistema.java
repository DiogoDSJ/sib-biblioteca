package model.entities;

import dao.DAO;
import exceptions.foraDeEstoqueException;
import exceptions.naoEncontradoException;
import exceptions.objetoInexistenteException;
import model.entities.enums.Cargo;

import java.time.LocalDate;
import java.util.*;

public class Sistema {


    public static int calcularDiasDaMulta(Emprestimo emprestimo) {
        LocalDate dataAtual = LocalDate.now();
        LocalDate dataFim = emprestimo.getDataFim();
        if (dataFim.isAfter(dataAtual)) {
            return dataAtual.compareTo(dataFim);
        }
        return 0;
    }


    public static void aplicarMulta(Leitor leitor) throws objetoInexistenteException {
        if (leitor == null) throw new objetoInexistenteException("Leitor não existe.");
        List<Emprestimo> emprestimoListLeitor = DAO.getEmprestimoDAO().findByIdMutuario(leitor.getId());
        if (emprestimoListLeitor.isEmpty()) throw new objetoInexistenteException("Usuário não tem empréstimos.");
        int diasAtraso = 0;
        for (Emprestimo obj : emprestimoListLeitor) {
            if (checarSeHaAtrasoEmprestimo(obj)) {
                diasAtraso += calcularDiasDaMulta(obj);
                DAO.getEmprestimoDAO().delete(obj);
                leitor.bloquearConta();
            }
        }
        diasAtraso *= 2;
        if (diasAtraso > 0 && DAO.getMultaDAO().findByIdMutuario(leitor.getId()) == null) {
            DAO.getMultaDAO().create(new Multa(LocalDate.now(), LocalDate.now().plusDays(diasAtraso), leitor.getId()));
        } else if (DAO.getMultaDAO().findByIdMutuario(leitor.getId()) != null) {
            DAO.getMultaDAO().findByIdMutuario(leitor.getId()).aumentarMulta(diasAtraso);
        }
    }

    //public List<Emprestimo> checarEmprestimosUsuario(Leitor leitor){}
    public static boolean checarSeHaAtrasoLeitor(Leitor leitor) {
        List<Emprestimo> emprestimoList = DAO.getEmprestimoDAO().findByIdMutuario(leitor.getId());
        for (Emprestimo obj : emprestimoList) {
            if (checarSeHaAtrasoEmprestimo(obj)) {
                return true;
            }
        }
        return DAO.getMultaDAO().findByIdMutuario(leitor.getId()) != null;
    }

    public static boolean checarSeHaAtrasoEmprestimo(Emprestimo emprestimo) {
        return emprestimo.getDataFim().isAfter(LocalDate.now());
    }

    public static void updateMultas() {
        for (Multa objIterator : DAO.getMultaDAO().findMany()) {
            if (!objIterator.getDataFim().isBefore(LocalDate.now())) {
                DAO.getLeitorDAO().findByPk(objIterator.getIdUsuario()).desbloquearConta();
                DAO.getMultaDAO().delete(objIterator);
            }
        }
    }


    public static int getOrdemReserva(Leitor leitor, Livro livro) {
        int contadora = 0;
        for (Reserva obj : DAO.getReservaDAO().findMany()) {
            if (obj.getIsbnLivro().equals(livro.getIsbn())) {
                contadora++;
                if (obj.getIdReservante().equals(leitor.getId())) {
                    break;
                }
            }
        }
        return contadora;
    }

    public static boolean checarSeAReservaDoUsuarioOPermitePegarOLivro(Leitor leitor, Livro livro) {
        int quantidade = Integer.parseInt(livro.getQuantidade());
        int ordemReserva = getOrdemReserva(leitor, livro);
        int numeroReservasLivro = numeroReservasLivro(livro);
        if (ordemReserva == 0) { // usuário não tem reserva
            return quantidade > numeroReservasLivro;
        }
        return (ordemReserva <= quantidade && ordemReserva > numeroReservasLivro);


    }

    public static int numeroReservasLivro(Livro livro) {
        int contadora = 0;
        for (Reserva obj : DAO.getReservaDAO().findMany()) {
            if (obj.getIsbnLivro().equals(livro.getIsbn())) {
                contadora++;
            }
        }
        return contadora;
    }


/*    public void removerEmprestimo(){
        for (Emprestimo obj: DAO.getEmprestimoDAO().findMany()) {
            if(!checarSeHaAtrasoEmprestimo(obj)){
                DAO.getEmprestimoDAO().delete(obj);
            }
            else if(checarSeHaAtrasoLeitor(DAO.getLeitorDAO().findByPk(obj.getIdMutuario()))){
                // aplicar multa
                aplicarMulta(DAO.getLeitorDAO().findByPk(obj.getIdMutuario()));
            }

        }
    }*/

    public static int getQuantidadeLivrosEmprestados() {
        return DAO.getEmprestimoDAO().findMany().size();
    }

    public static int getEstoqueDeLivrosNoAcervo() {
        List<Livro> livroList = DAO.getLivroDAO().findMany();
        int contador = 0;
        for (Livro livro : livroList) {
            contador += Integer.parseInt(livro.getQuantidade());
        }
        return contador;
    }

    public static int getQuantidadeDeLivrosAtrasados() {
        List<Emprestimo> emprestimos = DAO.getEmprestimoDAO().findMany();
        int contador = 0;
        for (Emprestimo emprestimo : emprestimos) {
            if (checarSeHaAtrasoEmprestimo(emprestimo)) {
                contador++;
            }

        }
        return contador;
    }

    public static int getQuantidadeDeLivrosReservados() {
        return DAO.getMultaDAO().findMany().size();
    }

    public static Map<Integer, Livro> getLivrosOrdenadosPorNumeroDeEmprestimo() throws objetoInexistenteException {
        List<Livro> livrosPopulares = DAO.getLivroDAO().findMany();
        Map<Integer, Livro> livrosPopularesDict = new TreeMap<>();
        for (Livro livro : livrosPopulares) {
            livrosPopularesDict.put(getQtdLivroNosEmprestimos(livro.getIsbn()), livro);
        }
        return livrosPopularesDict;
    }

    public static List<Livro> getDezLivrosMaisPopulares() throws objetoInexistenteException {
        Map<Integer, Livro> livrosPopularesDict = getLivrosOrdenadosPorNumeroDeEmprestimo();
        List<Livro> dezLivrosPopulares = (List<Livro>) livrosPopularesDict.values();
        return dezLivrosPopulares.subList(0, 9);
    }

    public static int getQtdLivroNosEmprestimos(String isbn) throws objetoInexistenteException {
        Livro livro = DAO.getLivroDAO().findByIsbn(isbn);
        int contador = 0;
        if (livro == null) throw new objetoInexistenteException("Livro não existe.");
        for (Emprestimo emprestimo : DAO.getEmprestimoDAO().findMany()) {
            if (livro.getIsbn().equals(emprestimo.getIsbnLivro())) {
                contador++;
            }
        }
        return contador;
    }

    /*
    atualizar reservas tem 3 etapas.

    1) ativar as reservas quando um livro novo ser liberado
    2) apagar as reservas esgostadas.

    */

    public static void ativarReservasLivros() {
        for (Livro livro : DAO.getLivroDAO().findMany()) {
            int unidadesLivro = Integer.parseInt(livro.getQuantidade());
            int contador = 0;
            for (Reserva reserva : DAO.getReservaDAO().findMany()) {
                if (contador == unidadesLivro) {
                    break;
                }
                if (reserva.getIsbnLivro().equals(livro.getIsbn())) {
                    reserva.setDataInicioReserva(LocalDate.now());
                    reserva.setDataFimReserva(reserva.getDataInicioReserva().plusDays(2));
                    contador++;
                }
            }
        }

    }

    public static void atualizarReservas() throws foraDeEstoqueException {
        for (Reserva reserva : DAO.getReservaDAO().findMany()) {
            if (reserva.getDataFimReserva().isAfter(LocalDate.now())) {
                DAO.getReservaDAO().delete(reserva);
                DAO.getLeitorDAO().findByPk(reserva.getIdReserva()).adicionarUmaReserva();
            }
        }
    }

    public static void devolverLivro(Leitor leitor, Livro livro) throws objetoInexistenteException, foraDeEstoqueException { // dois casos, o usuário está multado ou ele não está multado
        List<Emprestimo> emprestimosLeitor = DAO.getEmprestimoDAO().findByIdMutuario(leitor.getId());
        int checkvar = 0;
        if (emprestimosLeitor == null) {
            throw new objetoInexistenteException("Usuário não tem empréstimos ativos.");
        }
        for (Emprestimo emprestimo : emprestimosLeitor) {
            if (livro.getIsbn().equals(emprestimo.getIsbnLivro())) {
                checkvar = 1;
                if (checarSeHaAtrasoLeitor(leitor)) {
                    aplicarMulta(leitor);
                }
                DAO.getLivroDAO().findByPk(livro.getId()).adicionarUmaUnidade();
                leitor.adicionarUmEmprestimo();
                DAO.getLeitorDAO().update(leitor);
                DAO.getEmprestimoDAO().delete(emprestimo);
                atualizarReservas();
                ativarReservasLivros();
                break;
            }
        }
        if (checkvar == 0) throw new objetoInexistenteException("Não há um empréstimo com este livro.");
    }

    public static Usuario fazerLogin(String usuario, String senha, Cargo cargo) throws naoEncontradoException { // deve estar legal, checar depois
        int varnomecheck = 0;
        int varsenhacheck = 0;
        Usuario usuarioEncontrado = null;
        if (cargo.equals(Cargo.LEITOR)) { // checar se esses equals vaofuncionar
            List<Leitor> leitores = DAO.getLeitorDAO().findMany();
            for (Leitor leitor : leitores) {
                if (usuario.equals(leitor.getUsuario())) {
                    varnomecheck = 1;
                    if (senha.equals(leitor.getSenhaDeAcesso())) {
                        varsenhacheck = 1;
                        usuarioEncontrado = leitor;
                        break;
                    }
                }
            }
        } else if (cargo.equals(Cargo.BIBLIOTECARIO)) {
            List<Bibliotecario> bibliotecarios = DAO.getBibliotecarioDAO().findMany();
            for (Bibliotecario bibliotecario : bibliotecarios) {
                if (usuario.equals(bibliotecario.getUsuario())) {
                    varnomecheck = 1;
                    if (senha.equals(bibliotecario.getSenhaDeAcesso())) {
                        varsenhacheck = 1;
                        usuarioEncontrado = bibliotecario;
                        break;
                    }
                }
            }
        } else if (cargo.equals(Cargo.ADMINISTRADOR)) {
            List<Administrador> administradores = DAO.getAdministradorDAO().findMany();
            for (Administrador administrador : administradores) {
                if (usuario.equals(administrador.getUsuario())) {
                    varnomecheck = 1;
                    if (senha.equals(administrador.getSenhaDeAcesso())) {
                        varsenhacheck = 1;
                        usuarioEncontrado = administrador;
                        break;
                    }
                }
            }
        }
        if (varnomecheck == 0) throw new naoEncontradoException("Usuário não encontrado.");
        else if (varsenhacheck == 0) throw new naoEncontradoException("Senha incorreta.");
        return usuarioEncontrado;
    }

    public static Emprestimo buscarEmprestimoDoLeitor(Leitor leitor, String isbnLivro) {
        if (DAO.getLeitorDAO().findByPk(leitor.getId()) == null)
            return null;
        List<Emprestimo> emprestimoList = DAO.getEmprestimoDAO().findByIdMutuario(leitor.getId());
        if (emprestimoList == null)
            return null;
        for (Emprestimo emprestimo : emprestimoList) {
            if (isbnLivro.equals(emprestimo.getIsbnLivro())) {
                return emprestimo;
            }
        }
        return null;
    }

    public static boolean checarSeOUsuarioTemOLivro(Leitor leitor, String isbn) throws objetoInexistenteException {
        if (leitor == null) throw new objetoInexistenteException("Leitor não existe.");
        if (DAO.getEmprestimoDAO().findByIdMutuario(leitor.getId()).isEmpty()) {
            return false;
        } else {
            for (Emprestimo emprestimo : DAO.getEmprestimoDAO().findByIdMutuario(leitor.getId())) {
                if (emprestimo.getIsbnLivro().equals(isbn)) {
                    return true;
                }

            }
        }
        return false;
    }

    public static boolean checarSeOUsuarioReservouOLivro(Leitor leitor, String isbnLivro) throws objetoInexistenteException {
        if (leitor == null) throw new objetoInexistenteException("Leitor não existe.");
        if (DAO.getEmprestimoDAO().findByIdMutuario(leitor.getId()).isEmpty()) {
            return false;
        } else {
            for (Reserva reserva : DAO.getReservaDAO().findByIdReservante(leitor.getId())) {
                if (reserva.getIsbnLivro().equals(isbnLivro)) {
                    return true;
                }

            }
        }
        return false;
    }

    public static boolean checarSeOLivroFoiReservado(String isbnLivro) {
        for (Reserva reserva : DAO.getReservaDAO().findMany()) {
            if (reserva.getIsbnLivro().equals(isbnLivro)) {
                return true;
            }
        }
        return false;
    }
}

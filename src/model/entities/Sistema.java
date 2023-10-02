package model.entities;

import dao.DAO;
import exceptions.foraDeEstoqueException;
import exceptions.naoEncontradoException;
import exceptions.objetoInexistenteException;
import model.entities.enums.Cargo;

import java.time.LocalDate;
import java.util.*;

/**
 * Classe sistema, usado para guardar funções que não pertencem a nenhuma classe específica, mas sim do programa em sí.
 */
public class Sistema {

    /**
     * Checa quantos dias de atraso um empréstimo tem.
     * @param emprestimo Objeto empréstimo que será checado.
     * @return Retorna o número de dias de atraso.
     */
    public static int calcularDiasDaMulta(Emprestimo emprestimo) {
        LocalDate dataAtual = LocalDate.now();
        LocalDate dataFim = emprestimo.getDataFim();
        if (dataFim.isBefore(dataAtual)) {
            return dataAtual.compareTo(dataFim);
        }
        return 0;
    }

    /**
     * Método que aplica uma multa no leitor caso este esteja com pendências, que é um emprestimo com atraso ou então
     * uma multa ativa.
     * @param leitor Leitor que toma a multa.
     * @throws objetoInexistenteException Se o leitor não existir.
     */
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

    /**
     * Checa se alguns dos empréstimos do leitor já passou da data de entrega.
     * @param leitor Objeto leitor que terá os empréstimos checados.
     * @return Retorna verdadeiro se há empréstimos em atraso ou uma multa ativa, caso contrário retorna falso.
     */
    public static boolean checarSeHaAtrasoLeitor(Leitor leitor) {
        List<Emprestimo> emprestimoList = DAO.getEmprestimoDAO().findByIdMutuario(leitor.getId());
        for (Emprestimo obj : emprestimoList) {
            if (checarSeHaAtrasoEmprestimo(obj)) {
                return true;
            }
        }
        return DAO.getMultaDAO().findByIdMutuario(leitor.getId()) != null;
    }

    /**
     * Checa se um empréstimo está em atraso.
     * @param emprestimo Empréstimo que é checado.
     * @return retorna verdadeiro se estiver em atraso, caso contrário retorna falso.
     */
    public static boolean checarSeHaAtrasoEmprestimo(Emprestimo emprestimo) {
        return emprestimo.getDataFim().isBefore(LocalDate.now());
    }

    /**
     * Função que atualiza todas as multas do sistema, ou seja, apaga as multas que já venceram e desbloqueia o leitor.
     */
    public static void updateMultas() {
        List<Multa> multas = DAO.getMultaDAO().findMany();
        List<Multa> multasremovidas = new ArrayList<>();
        for (Multa objIterator : multas) {
            if (!objIterator.getDataFim().isBefore(LocalDate.now())) {
                DAO.getLeitorDAO().findByPk(objIterator.getIdUsuario()).desbloquearConta();
                multasremovidas.add(objIterator);
            }
        }
        DAO.getMultaDAO().findMany().removeAll(multasremovidas);
    }

    /**
     * Checa em que ordem da reserva um usuário que fez a reserva de um livro está.
     * @param leitor Leitor que fez a reserva.
     * @param livro Livro que foi reservado.
     * @return A ordem em que o reserva está na fila de reservas.
     */
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

    /**
     * Esse método verifica se a posição da reserva do usuário permite-o a pegar o livro.
     * @param leitor Leitor no qual terá a posição da reserva verificada.
     * @param livro Livro da reserva.
     * @return caso a ordem de reserva seja != 0 e igual ou menor que a quantidade de livros, retorna verdadeiro, caso
     * contrário retorna falso.
     */
    public static boolean checarSeAReservaDoUsuarioOPermitePegarOLivro(Leitor leitor, Livro livro) {
        int quantidade = Integer.parseInt(livro.getQuantidade());
        int ordemReserva = getOrdemReserva(leitor, livro);
        int numeroReservasLivro = numeroReservasLivro(livro);
        if (ordemReserva == 0) { // usuário não tem reserva
            return quantidade > numeroReservasLivro;
        }
        return (ordemReserva <= quantidade && ordemReserva >= numeroReservasLivro);


    }

    /**
     * Conta quantas reservas tem um livro.
     * @param livro Livro que está reservado.
     * @return Número de reservas de um livro.
     */
    public static int numeroReservasLivro(Livro livro) {
        int contadora = 0;
        for (Reserva obj : DAO.getReservaDAO().findMany()) {
            if (obj.getIsbnLivro().equals(livro.getIsbn())) {
                contadora++;
            }
        }
        return contadora;
    }

    /**
     * Conta quantos livros emprestados existem no banco de dados.
     * @return Quantidade de livros emprestados.
     */
    public static int getQuantidadeLivrosEmprestados() {
        return DAO.getEmprestimoDAO().findMany().size();
    }

    /**
     * Conta quanto livros tem no acervo.
     * @return Número de livros no acervo.
     */
    public static int getEstoqueDeLivrosNoAcervo() {
        List<Livro> livroList = DAO.getLivroDAO().findMany();
        int contador = 0;
        for (Livro livro : livroList) {
            contador += Integer.parseInt(livro.getQuantidade());
        }
        return contador;
    }

    /**
     * Conta quantos livros estão atrasados.
     * @return Número de livros atrasados.
     */
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

    /**
     * Conta quanto livros estão reservados.
     * @return Número de livros reservados.
     */
    public static int getQuantidadeDeLivrosReservados() {
        return DAO.getMultaDAO().findMany().size();
    }

    /**
     * Ordena os livros de acordo com o número de empréstimos em que eles estão emprestados.
     * @return Os livros ordenados de mais emprestados para menos emprestados.
     * @throws objetoInexistenteException Se o livro não existir.
     */
    public static Map<Integer, Livro> getLivrosOrdenadosPorNumeroDeEmprestimo() throws objetoInexistenteException {
        List<Livro> livrosPopulares = DAO.getLivroDAO().findMany();
        Map<Integer, Livro> livrosPopularesDict = new TreeMap<>();
        for (Livro livro : livrosPopulares) {
            livrosPopularesDict.put(getQtdLivroNosEmprestimos(livro.getIsbn()), livro);
        }
        return livrosPopularesDict;
    }

    /**
     * Função que pega a sub-lista dos 10 primeiros livros da lista de livros ordenados de mais para menos emprestados.
     * @return Sub-lista contendo 10 ou menos livros mais populares.
     * @throws objetoInexistenteException Se o livro não existir.
     */
    public static List<Livro> getDezLivrosMaisPopulares() throws objetoInexistenteException {
        Map<Integer, Livro> livrosPopularesDict = getLivrosOrdenadosPorNumeroDeEmprestimo();
        List<Livro> dezLivrosPopulares = (List<Livro>) livrosPopularesDict.values();
        return dezLivrosPopulares.subList(0, 9);
    }

    /**
     * Função que calcula em quantos empréstimos um livro está.
     * @param isbn Isbn do livro.
     * @return Quantidade de empréstimos em que um livro está.
     * @throws objetoInexistenteException Se o livro não existir.
     */
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

    /**
     * Função que checa se tem livros disponíveis e libera os x's primeiros reservantes para ir buscar o livro, colocan-
     * do uma data limite para ir buscar.
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

    /**
     * Apaga as reservas em que o usuário não foi buscar o livro.
     * @throws foraDeEstoqueException Caso o usuário já tenha alcançado o máximo do estoque de reservas.
     */
    public static void atualizarReservas() throws foraDeEstoqueException {
        for (Reserva reserva : DAO.getReservaDAO().findMany()) {
            if (reserva.getDataFimReserva().isAfter(LocalDate.now())) {
                DAO.getReservaDAO().delete(reserva);
                DAO.getLeitorDAO().findByPk(reserva.getIdReserva()).adicionarUmaReserva();
            }
        }
    }

    /**
     * Função utilizada para encerrar um empréstimo, caso o usuário devolva no tempo hábil, o usuário não sofre penali-
     * zações, caso contrário, o usuário será multado ou terá sua multa prolongada caso esteja multado.
     * @param leitor Leitor que vai devolver o livro.
     * @param livro Livro que será devolvido
     * @throws objetoInexistenteException Se o leitor não existir.
     * @throws foraDeEstoqueException Se estoque de
     * número de empréstimos ou reservas do leitor ter alcançado o mínimo ou máximo.
     */
    public static void devolverLivro(Leitor leitor, Livro livro) throws objetoInexistenteException, foraDeEstoqueException {
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

    /**
     * Função recebe um usuário e verifica se seus os campos de usuário e senha batem com o do guardado no sistema.
     * @param usuario Usuário que será procurado no sistema.
     * @param senha Senha que será procurado no sistema.
     * @param cargo Cargo que irá definir onde procurar.
     * @return Retorna o objeto usuário encontrado.
     * @throws naoEncontradoException Caso o usuário não tenha sido encontrado ou a senha estiver incorreta.
     */
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

    /**
     * Verifica o usuário já pegou o livro em algum empréstimo ativo.
     * @param leitor Leitor que será verificado.
     * @param isbn Isbn do livro que será verifcado.
     * @return Verdadeiro se o usuário estiver com o livro em um empréstimo ativo, caso contrário, retorna falso.
     * @throws objetoInexistenteException Se o leitor não existir.
     */
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

    /**
     * Checa se o usuário já tem esse livro reservado em alguma reserva ativa.
     * @param leitor Leitor que será verificado.
     * @param isbnLivro Isbn do livro que será verifcado.
     * @return Verdadeiro se o usuário estiver com o livro em um reserva ativa, caso contrário, retorna falso.
     * @throws objetoInexistenteException Se o leitor não existir.
     */
    public static boolean checarSeOUsuarioReservouOLivro(Leitor leitor, String isbnLivro) throws objetoInexistenteException {
        if (leitor == null) throw new objetoInexistenteException("Leitor não existe.");
        if (DAO.getReservaDAO().findByIdReservante(leitor.getId()).isEmpty()) {
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

    /**
     * Checa se o livro já foi reservado por alguma pessoa.
     * @param isbnLivro Isbn do livro que será verifcado.
     * @return Retorna verdadeiro se o livro está reservado por alguma pessoa, caso contrário, retorna falso.
     */
    public static boolean checarSeOLivroFoiReservado(String isbnLivro) {
        for (Reserva reserva : DAO.getReservaDAO().findMany()) {
            if (reserva.getIsbnLivro().equals(isbnLivro)) {
                return true;
            }
        }
        return false;
    }
}

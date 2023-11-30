package dao;

import dao.administrador.AdministradorDAO;
import dao.administrador.AdministradorDAOList;
import dao.administrador.AdministradorDAOListFile;
import dao.bibliotecario.BibliotecarioDAO;
import dao.bibliotecario.BibliotecarioDAOList;
import dao.bibliotecario.BibliotecarioDAOListFile;
import dao.emprestimo.EmprestimoDAO;
import dao.emprestimo.EmprestimoDAOList;
import dao.emprestimo.EmprestimoDAOListFile;
import dao.leitor.LeitorDAO;
import dao.leitor.LeitorDAOList;
import dao.leitor.LeitorDAOListFile;
import dao.livro.LivroDAO;
import dao.livro.LivroDAOList;
import dao.livro.LivroDAOListFile;
import dao.multa.MultaDAO;
import dao.multa.MultaDAOList;
import dao.multa.MultaDAOListFile;
import dao.reserva.ReservaDAO;
import dao.reserva.ReservaDAOList;
import dao.reserva.ReservaDAOListFile;

/**
 * Essa classe é usada para explorar as extensões do DAO no sistema, onde os dados estão em sete diferentes
 * distintas: administrador, bibliotecario, emprestimo, leitor, livro, multa, reserva. Os atributos e métodos são
 * declarados como estáticos, o que implica que não é preciso criar uma instância de objeto para utilizar essas
 * funcionalidades.
 */
public class DAO {

    private static AdministradorDAO administradorDAO;
    private static BibliotecarioDAO bibliotecarioDAO;
    private static LeitorDAO leitorDAO;
    private static LivroDAO livroDAO;
    private static EmprestimoDAO emprestimoDAO;
    private static ReservaDAO reservaDAO;
    private static MultaDAO multaDAO;

    /**
     * Cria um objeto do AdministradorDAOList caso não esteja criado e o retorna.
     * @return Lista contendo os objetos administradores.
     */
    public static AdministradorDAO getAdministradorDAO() {
        if(administradorDAO == null)
        {
            administradorDAO = new AdministradorDAOListFile();
        }
        return administradorDAO;
    }

    /**
     * Cria um objeto do BibliotecarioDAOList caso não esteja criado e o retorna.
     * @return Lista contendo os objetos bibliotecarios.
     */
    public static BibliotecarioDAO getBibliotecarioDAO() {
        if(bibliotecarioDAO == null)
        {
            bibliotecarioDAO = new BibliotecarioDAOListFile();
        }
        return bibliotecarioDAO;
    }

    /**
     * Cria um objeto do LeitorDAOList caso não esteja criado e o retorna.
     * @return Lista contendo os objetos leitores.
     */

    public static LeitorDAO getLeitorDAO(){
        if(leitorDAO == null){
            leitorDAO = new LeitorDAOListFile();
        }
        return leitorDAO;
    }

    /**
     * Cria um objeto do LivroDAOList caso não esteja criado e o retorna.
     * @return Lista contendo os objetos livros.
     */
    public static LivroDAO getLivroDAO(){
        if(livroDAO == null){
            livroDAO = new LivroDAOListFile();
        }
        return livroDAO;
    }

    /**
     * Cria um objeto do EmprestimoDAOList caso não esteja criado e o retorna.
     * @return Lista contendo os objetos emprestimos.
     */
    public static EmprestimoDAO getEmprestimoDAO(){
        if(emprestimoDAO == null){
            emprestimoDAO = new EmprestimoDAOListFile();
        }
        return emprestimoDAO;
    }

    /**
     * Cria um objeto do ReservaDAOList caso não esteja criado e o retorna.
     * @return Lista contendo os objetos reservas.
     */
    public static ReservaDAO getReservaDAO(){
        if(reservaDAO == null){
            reservaDAO = new ReservaDAOListFile();
        }
        return reservaDAO;
    }

    /**
     * Cria um objeto do MultaDAOList caso não esteja criado e o retorna.
     * @return Lista contendo os objetos multas.
     */
    public static MultaDAO getMultaDAO(){
        if(multaDAO == null){
            multaDAO = new MultaDAOListFile();
        }
        return multaDAO;
    }
}

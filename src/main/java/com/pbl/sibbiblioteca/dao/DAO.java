package com.pbl.sibbiblioteca.dao;

import com.pbl.sibbiblioteca.dao.administrador.AdministradorDAO;
import com.pbl.sibbiblioteca.dao.administrador.AdministradorDAOListFile;
import com.pbl.sibbiblioteca.dao.bibliotecario.BibliotecarioDAO;
import com.pbl.sibbiblioteca.dao.bibliotecario.BibliotecarioDAOListFile;
import com.pbl.sibbiblioteca.dao.emprestimo.EmprestimoDAO;
import com.pbl.sibbiblioteca.dao.emprestimo.EmprestimoDAOListFile;
import com.pbl.sibbiblioteca.dao.leitor.LeitorDAO;
import com.pbl.sibbiblioteca.dao.leitor.LeitorDAOListFile;
import com.pbl.sibbiblioteca.dao.livro.LivroDAO;
import com.pbl.sibbiblioteca.dao.livro.LivroDAOListFile;
import com.pbl.sibbiblioteca.dao.multa.MultaDAO;
import com.pbl.sibbiblioteca.dao.multa.MultaDAOListFile;
import com.pbl.sibbiblioteca.dao.reserva.ReservaDAO;
import com.pbl.sibbiblioteca.dao.reserva.ReservaDAOListFile;

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

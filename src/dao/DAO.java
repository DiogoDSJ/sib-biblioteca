package dao;

import dao.administrador.AdministradorDAO;
import dao.administrador.AdministradorDAOList;
import dao.bibliotecario.BibliotecarioDAO;
import dao.bibliotecario.BibliotecarioDAOList;
import dao.leitor.LeitorDAO;
import dao.leitor.LeitorDAOList;
import dao.livro.LivroDAO;
import dao.livro.LivroDAOList;

public class DAO {

    private static AdministradorDAO administradorDAO;
    private static BibliotecarioDAO bibliotecarioDAO;
    private static LeitorDAO leitorDAO;
    private static LivroDAO livroDAO;
    public static AdministradorDAO getAdministradorDAO() {
        if(administradorDAO == null)
        {
            administradorDAO = new AdministradorDAOList();
        }
        return administradorDAO;
    }

    public static BibliotecarioDAO getBibliotecarioDAO() {
        if(bibliotecarioDAO == null)
        {
            bibliotecarioDAO = new BibliotecarioDAOList();
        }
        return bibliotecarioDAO;
    }

    public static LeitorDAO getLeitorDAO(){
        if(leitorDAO == null){
            leitorDAO = new LeitorDAOList();
        }
        return leitorDAO;
    }

    public static LivroDAO getLivroDAO(){
        if(livroDAO == null){
            livroDAO = new LivroDAOList();
        }
        return livroDAO;
    }

}

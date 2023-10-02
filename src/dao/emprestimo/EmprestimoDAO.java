package dao.emprestimo;

import dao.CRUD;
import model.entities.Emprestimo;

import java.util.List;

/**
 * Interface criada para implementar o armazenamento dos objetos emprestimo, usando a interface CRUD.
 */
public interface EmprestimoDAO extends CRUD<Emprestimo> {
    /**
     * Faz a busca de todos os empréstimos que um leitor tem.
     * @param idMutuario Id do leitor.
     * @return Lista dos emprestimos do leitor.
     */
    public List<Emprestimo> findByIdMutuario(String idMutuario);

    /**
     * Faz a busca de todos os empréstimos que um livro tem.
     * @param isbn Isbn do livro
     * @return Lista dos emprestimos do livro.
     */
    public List<Emprestimo> findByIsbn(String isbn);

}

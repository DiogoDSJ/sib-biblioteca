package dao.emprestimo;

import dao.CRUD;
import model.entities.Emprestimo;

import java.util.List;

public interface EmprestimoDAO extends CRUD<Emprestimo> {

    public List<Emprestimo> findByIdMutuario(String idMutuario);
    public List<Emprestimo> findByIsbn(String isbn);

}

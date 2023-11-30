package dao;

import java.util.List;

/**
 * Operações basicas de CRUD.
 * @param <T>
 */

public interface CRUD<T> {
    /**
     * Cria novo objeto.
     * @param obj objeto criado.
     * @return objeto criado.
     */
    public T create(T obj) ;

    /**
     * Deleta um objeto.
     * @param obj objeto a ser deletado.
     */
    public void delete(T obj) ;

    /**
     * Encontra um obj no DAO pela sua Chave primária
     * @param obj Chave primária
     * @return objeto encontrado.
     */
    public T findByPk(String obj);

    /**
     * Deleta todos os dados.
     */
    public void deleteMany() ;

    /**
     * Atualiza o objeto no DAO.
     * @param obj objeto a ser atualizado.
     * @return retorna o objeto atualizado.
     */
    public T update(T obj) ;

    /**
     * retorna todos os elementos do DAO.
     * @return lista com todos os objetos <T>.
     */
    public List<T> findMany();



}

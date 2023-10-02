package dao.bibliotecario;

import dao.CRUD;
import model.entities.Bibliotecario;

/**
 * Interface criada para implementar o armazenamento dos objetos bibliotecario, usando a interface CRUD.
 */
public interface BibliotecarioDAO extends CRUD<Bibliotecario> {
    /**
     * Busca o Bibliotecario pelo seu atributo usuário.
     * @param usuario atributo usuário do Bibliotecario.
     * @return o objeto Bibliotecario que tem esse atributo usuario
     */
    Bibliotecario findByUsuario(String usuario);

}

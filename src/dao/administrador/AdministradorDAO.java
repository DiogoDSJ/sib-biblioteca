package dao.administrador;


import dao.CRUD;
import model.entities.Administrador;
import model.entities.Bibliotecario;

/**
 * Interface criada para implementar o armazenamento dos objetos administradores, usando a interface CRUD.
 */
public interface AdministradorDAO extends CRUD<Administrador> {
    /**
     * Busca o administrador pelo seu atributo usuário.
     * @param usuario atributo usuário do administrador.
     * @return o objeto administrador que tem esse atributo usuario
     */
    Administrador findByUsuario(String usuario);
}

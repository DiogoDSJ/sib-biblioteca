package com.pbl.sibbiblioteca.dao.bibliotecario;

import com.pbl.sibbiblioteca.dao.CRUD;
import com.pbl.sibbiblioteca.model.entities.Bibliotecario;

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

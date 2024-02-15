package com.pbl.sibbiblioteca.dao.bibliotecario;

import com.pbl.sibbiblioteca.dao.CRUD;
import com.pbl.sibbiblioteca.model.entities.Administrador;
import com.pbl.sibbiblioteca.model.entities.Bibliotecario;

import java.util.List;

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
    List<Bibliotecario> findByUsuarioList(String usuario);
}

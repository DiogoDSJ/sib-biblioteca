package dao.leitor;

import dao.CRUD;
import model.entities.Leitor;

/**
 * Interface criada para implementar o armazenamento dos objetos leitor, usando a interface CRUD.
 */
public interface LeitorDAO extends CRUD<Leitor> {

    /**
     * Encontra um leitor a partir do seu atributo usuário.
     * @param usuario atributo usuário do leitor.
     * @return Retorna o leitor encontrado.
     */
    Leitor findByUsuario(String usuario);

}

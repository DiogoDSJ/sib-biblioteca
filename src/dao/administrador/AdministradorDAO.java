package dao.administrador;


import dao.CRUD;
import model.entities.Administrador;
import model.entities.Bibliotecario;

public interface AdministradorDAO extends CRUD<Administrador> {

    Administrador findByUsuario(String usuario);
}

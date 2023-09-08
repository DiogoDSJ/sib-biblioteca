package dao;

import dao.administrador.AdministradorDAO;
import dao.administrador.AdministradorDAOList;

public class DAO {

    private static AdministradorDAO administradorDAO;

    public static AdministradorDAO getAdministradorDAO() {
        if(administradorDAO == null)
        {
            administradorDAO = new AdministradorDAOList();
        }
        return administradorDAO;
    }


}

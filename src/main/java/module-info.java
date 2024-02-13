module com.pbl.sibblioteca {

    requires javafx.fxml;
    requires javafx.controls;
    requires org.junit.jupiter.api;
    requires org.junit.platform.commons;
    requires org.junit.platform.engine;


    opens com.pbl.sibbiblioteca.view to javafx.fxml;
    exports com.pbl.sibbiblioteca.view;
    opens com.pbl.sibbiblioteca.controller.TelaRelatorio to javafx.fxml;
    exports com.pbl.sibbiblioteca.controller.TelaRelatorio;
    opens com.pbl.sibbiblioteca.controller.TelaPesquisa to javafx.fxml;
    exports com.pbl.sibbiblioteca.controller.TelaPesquisa;
    opens com.pbl.sibbiblioteca.controller.TelaLogin to javafx.fxml;
    exports com.pbl.sibbiblioteca.controller.TelaLogin;
    opens com.pbl.sibbiblioteca.controller.TelaInicial to javafx.fxml;
    exports com.pbl.sibbiblioteca.controller.TelaInicial;
    opens com.pbl.sibbiblioteca.controller.TelaAdicionarObjeto to javafx.fxml;
    exports com.pbl.sibbiblioteca.controller.TelaAdicionarObjeto;
    opens com.pbl.sibbiblioteca.controller.TelaMenuBibliotecario to javafx.fxml;
    exports com.pbl.sibbiblioteca.controller.TelaMenuBibliotecario;
    opens com.pbl.sibbiblioteca.model.entities.enums to javafx.fxml;
    exports com.pbl.sibbiblioteca.model.entities.enums;
    opens com.pbl.sibbiblioteca.exceptions to javafx.fxml;
    exports com.pbl.sibbiblioteca.exceptions;
    opens com.pbl.sibbiblioteca.dao to com.pbl.sibbiblioteca.model;
    exports com.pbl.sibbiblioteca.dao;
    exports com.pbl.sibbiblioteca.dao.multa;
    exports com.pbl.sibbiblioteca.dao.administrador;
    exports com.pbl.sibbiblioteca.dao.leitor;
    exports com.pbl.sibbiblioteca.dao.bibliotecario;
    exports com.pbl.sibbiblioteca.dao.reserva;
    exports com.pbl.sibbiblioteca.dao.livro;
    exports com.pbl.sibbiblioteca.dao.emprestimo;
    opens com.pbl.sibbiblioteca.model.entities to com.pbl.sibbiblioteca.model.entities;
    exports com.pbl.sibbiblioteca.model.entities;

}
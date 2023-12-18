package com.pbl.sibbiblioteca.controller.TelaLogin;

import com.pbl.sibbiblioteca.exceptions.naoEncontradoException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.pbl.sibbiblioteca.model.entities.Sistema;
import com.pbl.sibbiblioteca.model.entities.enums.Cargo;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable
{
    @FXML
    private ChoiceBox<Cargo> cargoLogin;

    @FXML
    private TextField campoUsuario;
    @FXML
    private PasswordField campoSenha;

    private Cargo[] cargos = {Cargo.ADMINISTRADOR, Cargo.LEITOR, Cargo.BIBLIOTECARIO};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargoLogin.getItems().addAll(cargos);
    }

    public void fazerLogin() throws naoEncontradoException {
        if(Sistema.fazerLogin(campoUsuario.getText(), campoSenha.getText(), cargoLogin.getSelectionModel().getSelectedItem()) != null){
            System.out.println("Login existente.");

        }
        else{
            System.out.println("Login nao existe.");
        }
    }
}
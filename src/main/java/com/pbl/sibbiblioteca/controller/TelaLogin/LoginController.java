package com.pbl.sibbiblioteca.controller.TelaLogin;

import com.pbl.sibbiblioteca.controller.TelaInicial.TelaInicialController;
import com.pbl.sibbiblioteca.exceptions.naoEncontradoException;
import com.pbl.sibbiblioteca.model.entities.Usuario;
import com.pbl.sibbiblioteca.utils.TelaController;
import com.pbl.sibbiblioteca.view.SibApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import com.pbl.sibbiblioteca.model.entities.Sistema;
import com.pbl.sibbiblioteca.model.entities.enums.Cargo;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController
{
    @FXML
    private ChoiceBox<Cargo> cargoLogin;

    @FXML
    private TextField campoUsuario;
    @FXML
    private PasswordField campoSenha;

    private Usuario usuarioRetornado;
    private final Cargo[] cargos = {Cargo.ADMINISTRADOR, Cargo.LEITOR, Cargo.BIBLIOTECARIO};
    @FXML
    private Button visitanteButton;

    @FXML
    public void initialize() {
        cargoLogin.getItems().addAll(cargos);
    }

    public void fazerLogin(ActionEvent event) throws naoEncontradoException {
        try{
            if(cargoLogin.getSelectionModel().isEmpty()){
                Alert erroCargo = new Alert(Alert.AlertType.ERROR);
                erroCargo.setTitle("Cargo inválido");
                erroCargo.setContentText("Selecione um dos cargos para prosseguir.");
                erroCargo.showAndWait();
                return;
            }
            Usuario usuarioRetornado = Sistema.fazerLogin(campoUsuario.getText(), campoSenha.getText(), cargoLogin.getSelectionModel().getSelectedItem());
            FXMLLoader loader = TelaController.StageFXMLLoader("TelaDeInicio.fxml");
            Parent root = loader.load();
            Stage stage = TelaController.retornarStage(event);
            TelaInicialController telaInicialController = loader.getController();
            telaInicialController.setUsuario(usuarioRetornado);
            telaInicialController.initialize();
            stage.setScene(new Scene(root));
        }
        catch (naoEncontradoException e) {
            if (e.getMessage().equals("Usuário não encontrado.")) {
                Alert erroUsuario = new Alert(Alert.AlertType.ERROR);
                erroUsuario.setTitle("Usuário inexistente.");
                erroUsuario.setContentText("O usuário digitado não foi encontrado na base de dados.");
                erroUsuario.showAndWait();
            } else if (e.getMessage().equals("Senha incorreta.")) {
                Alert erroSenha = new Alert(Alert.AlertType.ERROR);
                erroSenha.setTitle("Senha incorreta.");
                erroSenha.setContentText("Senha incorreta, tente novamente.");
                erroSenha.showAndWait();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void setVisitanteButton(ActionEvent event) throws IOException {
        FXMLLoader loader = TelaController.StageFXMLLoader("TelaDeInicio.fxml");
        Parent root = loader.load();
        TelaInicialController telaInicialController = loader.getController();
        Stage stage = TelaController.retornarStage(event);
        telaInicialController.setUsuario(null);
        telaInicialController.initialize();
        stage.setScene(new Scene(root));
    }
}
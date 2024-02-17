package com.pbl.sibbiblioteca.controller.TelaAdicionarObjeto;

import com.pbl.sibbiblioteca.exceptions.cargoInvalidoException;
import com.pbl.sibbiblioteca.model.entities.Administrador;
import com.pbl.sibbiblioteca.model.entities.enums.Cargo;
import com.pbl.sibbiblioteca.utils.TelaController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TelaAdicionarUsuarioController {

    private Administrador administrador;
    @javafx.fxml.FXML
    private TextField nomeField;
    @javafx.fxml.FXML
    private TextField enderecoField;
    @javafx.fxml.FXML
    private TextField telefoneField;
    @javafx.fxml.FXML
    private TextField usuarioField;
    @javafx.fxml.FXML
    private TextField senhaField;
    @javafx.fxml.FXML
    private ChoiceBox<Cargo> tipoPesquisaChoiceBox;
    @javafx.fxml.FXML
    private Button adicionarUsuarioButton;
    @javafx.fxml.FXML
    private Button cancelarButton;
    private final Cargo[] cargos = {Cargo.ADMINISTRADOR, Cargo.LEITOR, Cargo.BIBLIOTECARIO};

    @FXML
    public void initialize(){
        tipoPesquisaChoiceBox.getItems().addAll(cargos);
    }

    public void setAdministrador(Administrador administrador){
        this.administrador = administrador;
    }

    @javafx.fxml.FXML
    public void setAdicionarUsuarioButton(ActionEvent actionEvent) throws cargoInvalidoException {
        if(nomeField.getText().isBlank() || enderecoField.getText().isBlank() || telefoneField.getText().isBlank() ||
                usuarioField.getText().isBlank() || senhaField.getText().isBlank() || tipoPesquisaChoiceBox.getSelectionModel().isEmpty()){
            TelaController.gerarAlertaErro("Campo vazio.", "Algum dos campos estão vazios.");
            return;
        }
        administrador.cadastrarUsuario(nomeField.getText(), enderecoField.getText(), telefoneField.getText(),
                usuarioField.getText(), senhaField.getText(), tipoPesquisaChoiceBox.getValue());
        TelaController.gerarAlertaOk("Usuario adicionado", "O usuário foi inserido com sucesso.");

    }

    @javafx.fxml.FXML
    public void setCancelarButton(ActionEvent actionEvent) {
        Stage stage = TelaController.retornarStage(actionEvent);
        stage.close();
    }
}

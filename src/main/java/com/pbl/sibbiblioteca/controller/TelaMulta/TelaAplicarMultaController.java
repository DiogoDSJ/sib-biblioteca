package com.pbl.sibbiblioteca.controller.TelaMulta;

import com.pbl.sibbiblioteca.exceptions.objetoInexistenteException;
import com.pbl.sibbiblioteca.model.entities.Administrador;
import com.pbl.sibbiblioteca.model.entities.Leitor;
import com.pbl.sibbiblioteca.utils.TelaController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TelaAplicarMultaController {

    Administrador administrador;
    Leitor usuarioSelecionado;

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }

    public void setUsuarioSelecionado(Leitor usuarioSelecionado) {
        this.usuarioSelecionado = usuarioSelecionado;
    }

    @javafx.fxml.FXML
    private TextField multaTextField;

    @FXML
    public void setAplicarButton(ActionEvent actionEvent) {
        try {
            if(multaTextField.getText().matches("[0-9]+") && !multaTextField.getText().isBlank()) {
                administrador.multarLeitor(usuarioSelecionado, Integer.parseInt(multaTextField.getText()));
                TelaController.gerarAlertaOk("Operação realizada", "O usuário foi mulltado pelo tempo determinado.");
            }
            else{
                TelaController.gerarAlertaErro("Erro", "É necessário digitar um número");
            }
        }
        catch (objetoInexistenteException e){
            TelaController.gerarAlertaErro("Erro", "O objeto não existe.");
        }
    }

    @FXML
    public void setCancelarButton(ActionEvent actionEvent){
        Stage stage = TelaController.retornarStage(actionEvent);
        stage.close();
    }
}

package com.pbl.sibbiblioteca.controller.TelaMenuBibliotecario;

import com.pbl.sibbiblioteca.utils.TelaController;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class TelaMenuBibliotecarioController {
    @javafx.fxml.FXML
    private Button adicionarLivroButton;

    @javafx.fxml.FXML
    public void setAdicionarLivroButton(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        Stage stageAtual = TelaController.retornarStage(actionEvent);
        stage.initOwner(stageAtual);
        TelaController.StageBuilder(stage, TelaController.StageFXMLLoader("TelaAdicionarLivro.fxml"));
        stage.showAndWait();
    }
}

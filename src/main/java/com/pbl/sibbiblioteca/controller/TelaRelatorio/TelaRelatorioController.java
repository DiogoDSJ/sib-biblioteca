package com.pbl.sibbiblioteca.controller.TelaRelatorio;

import com.pbl.sibbiblioteca.model.entities.Livro;
import com.pbl.sibbiblioteca.utils.TelaController;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class TelaRelatorioController {
    @javafx.fxml.FXML
    private Button relatorioLivrosButton;
    @javafx.fxml.FXML
    private Button livrosPopularesButton;

    @javafx.fxml.FXML
    public void setRelatorioLivrosButton(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        Stage stageAtual = TelaController.retornarStage(actionEvent);
        TelaController.StageBuilder(stage, TelaController.StageFXMLLoader("TelaDeRelatorioLivros.fxml"));
        stage.initOwner(stageAtual);
        stage.showAndWait();
    }

    @javafx.fxml.FXML
    public void setLivrosPopularesButton(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        Stage stageAtual = TelaController.retornarStage(actionEvent);
        TelaController.StageBuilder(stage, TelaController.StageFXMLLoader("TelaDeLivrosPopulares.fxml"));
        stage.initOwner(stageAtual);
        stage.showAndWait();
    }
}
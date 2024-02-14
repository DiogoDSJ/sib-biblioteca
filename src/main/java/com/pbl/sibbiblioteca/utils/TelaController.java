package com.pbl.sibbiblioteca.utils;

import com.pbl.sibbiblioteca.controller.TelaInicial.TelaInicialController;
import com.pbl.sibbiblioteca.model.entities.Livro;
import com.pbl.sibbiblioteca.view.SibApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class TelaController {

    public static void StageBuilder(Stage stage, FXMLLoader fxmlLoader) throws IOException {
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Sistema de Biblioteca - SIB");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
    }

    public static FXMLLoader StageFXMLLoader(String fxml){
        return new FXMLLoader(SibApplication.class.getResource(fxml));
    }

    public static Stage retornarStage(ActionEvent event){
        return (Stage)((Node)event.getSource()).getScene().getWindow();
    }

    public static void gerarAlertaErro(String titulo, String conteudo){
        Alert erro = new Alert(Alert.AlertType.ERROR);
        erro.setTitle(titulo);
        erro.setContentText(conteudo);
        erro.showAndWait();
    }

    public static void gerarAlertaOk(String titulo, String conteudo){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(conteudo);
        alert.showAndWait();
    }
}

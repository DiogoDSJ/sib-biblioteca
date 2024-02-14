package com.pbl.sibbiblioteca.controller.TelaAdicionarObjeto;

import com.pbl.sibbiblioteca.controller.TelaInicial.TelaInicialController;
import com.pbl.sibbiblioteca.model.entities.Bibliotecario;
import com.pbl.sibbiblioteca.model.entities.Usuario;
import com.pbl.sibbiblioteca.utils.TelaController;
import com.pbl.sibbiblioteca.view.SibApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class TelaAdicionarLivroController {
    @javafx.fxml.FXML
    private TextField tituloLivroField;//fazer um alerta para li vro ja existente
    @javafx.fxml.FXML
    private TextField isbnLivroField;
    @javafx.fxml.FXML
    private TextField autorLivroField;
    @javafx.fxml.FXML
    private TextField editoraLivroField;
    @javafx.fxml.FXML
    private TextField categoriaLivroField;
    @javafx.fxml.FXML
    private TextField anoLivroField;
    @javafx.fxml.FXML
    private Button adicionarLivroButton;

    @javafx.fxml.FXML
    public void setAdicionarLivroButton(ActionEvent actionEvent) throws IOException {
        if(tituloLivroField.getText().isBlank() || isbnLivroField.getText().isBlank() || isbnLivroField.getText().isBlank() ||
                autorLivroField.getText().isBlank() || editoraLivroField.getText().isBlank() || categoriaLivroField.getText().isBlank() ||
                anoLivroField.getText().isBlank()){
            Alert campoVazio = new Alert(Alert.AlertType.ERROR);
            campoVazio.setTitle("Campo vazio.");
            campoVazio.setContentText("Algum dos campos estão vazios.");
            campoVazio.showAndWait();
            return;
        }
        else if(isbnLivroField.getText().matches("[a-zA-Z]+"))
        {
            Alert erroIsbn = new Alert(Alert.AlertType.ERROR);
            erroIsbn.setTitle("Erro campo ISBN.");
            erroIsbn.setContentText("O ISBN só pode conter números.");
            erroIsbn.showAndWait();
        }
        else if(anoLivroField.getText().matches("[a-zA-Z]+"))
        {
            Alert erroAno = new Alert(Alert.AlertType.ERROR);
            erroAno.setTitle("Erro campo ano.");
            erroAno.setContentText("O ano só pode conter números.");
            erroAno.showAndWait();
        }
        Stage stage = TelaController.retornarStage(actionEvent);
        Bibliotecario usuarioLogado = (Bibliotecario) stage.getOwner().getUserData();

        int retorno = usuarioLogado.adicionarLivro(isbnLivroField.getText(), autorLivroField.getText(), tituloLivroField.getText(),
                editoraLivroField.getText(), categoriaLivroField.getText(), anoLivroField.getText());
        if(retorno == 1){
            Alert erroAno = new Alert(Alert.AlertType.CONFIRMATION);
            erroAno.setTitle("Livro criado.");
            erroAno.setContentText("O livro foi criado com sucesso.");
            erroAno.showAndWait();
        }
        else if(retorno == 0){
            Alert erroAno = new Alert(Alert.AlertType.WARNING);
            erroAno.setTitle("Livro já existe.");
            erroAno.setContentText("O livro já existe, então uma nova unidade foi adicionada ao acervo.");
            erroAno.showAndWait();
        }
    }
}

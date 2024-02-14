package com.pbl.sibbiblioteca.controller.TelaEditarObjeto;

import com.pbl.sibbiblioteca.exceptions.objetoDuplicadoException;
import com.pbl.sibbiblioteca.exceptions.objetoInexistenteException;
import com.pbl.sibbiblioteca.model.entities.Bibliotecario;
import com.pbl.sibbiblioteca.model.entities.Livro;
import com.pbl.sibbiblioteca.utils.TelaController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class TelaEdicaoLivroController {

    Livro livro;
    Bibliotecario usuarioLogado;
    @javafx.fxml.FXML
    private TextField tituloLivroField;
    @javafx.fxml.FXML
    private Button salvarTituloButton;
    @javafx.fxml.FXML
    private TextField isbnLivroField;
    @javafx.fxml.FXML
    private Button salvarIsbnButton;
    @javafx.fxml.FXML
    private TextField autorLivroField;
    @javafx.fxml.FXML
    private Button salvarAutorButton;
    @javafx.fxml.FXML
    private TextField editoraLivroField;
    @javafx.fxml.FXML
    private Button salvarEditoraButton;
    @javafx.fxml.FXML
    private TextField categoriaLivroField;
    @javafx.fxml.FXML
    private Button salvarCategoriaButton;
    @javafx.fxml.FXML
    private TextField anoLivroField;
    @javafx.fxml.FXML
    private Button salvarAnoButton;
    @javafx.fxml.FXML
    private Button cancelarButton;

    @FXML
    public void setIsbnLivroField(String isbnLivroField) {
        this.isbnLivroField.setText(isbnLivroField);
    }

    @FXML
    public void setAutorLivroField(String autorLivroField) {
        this.autorLivroField.setText(autorLivroField);
    }
    @FXML
    public void setEditoraLivroField(String editoraLivroField) {
        this.editoraLivroField.setText(editoraLivroField);
    }
    @FXML
    public void setCategoriaLivroField(String categoriaLivroField) {
        this.categoriaLivroField.setText(categoriaLivroField);
    }
    @FXML
    public void setAnoLivroField(String anoLivroField) {
        this.anoLivroField.setText(anoLivroField);
    }
    @FXML
    public void setTituloLivroField(String tituloLivroField){
        this.tituloLivroField.setText(tituloLivroField);
    }

    @javafx.fxml.FXML
    public void setSalvarTituloButton(ActionEvent actionEvent) throws objetoInexistenteException {
        try{
            usuarioLogado.trocarTituloLivro(livro.getIsbn(), tituloLivroField.getText());
            TelaController.gerarAlertaOk("Operação realizada", "O título do livro foi trocado.");
        }
        catch (objetoInexistenteException e){
            TelaController.gerarAlertaErro("Livro inexistente.", "Não há livro com esse ISBN.");
        }
    }

    @javafx.fxml.FXML
    public void setSalvarIsbnButton(ActionEvent actionEvent) throws objetoInexistenteException, objetoDuplicadoException {
        try {
            usuarioLogado.trocarIsbnLivro(livro.getIsbn(), isbnLivroField.getText());
            TelaController.gerarAlertaOk("Operação realizada", "O isbn do livro foi trocado.");
        }
        catch (objetoInexistenteException e){
            TelaController.gerarAlertaErro("Livro inexistente.", "Não há livro com esse ISBN.");
        }
        catch (objetoDuplicadoException e){
            TelaController.gerarAlertaErro("ISBN duplicado.", "Há um livro com esse mesmo ISBN no acervo.");
        }
    }

    @javafx.fxml.FXML
    public void setSalvarAutorButton(ActionEvent actionEvent) throws objetoInexistenteException {
        try {
            usuarioLogado.trocarAutorLivro(livro.getIsbn(), autorLivroField.getText());
            TelaController.gerarAlertaOk("Operação realizada", "O autor do livro foi trocado.");
        }
        catch (objetoInexistenteException e){
            TelaController.gerarAlertaErro("Livro inexistente.", "Não há livro com esse ISBN.");
        }
    }

    @javafx.fxml.FXML
    public void setSalvarEditoraButton(ActionEvent actionEvent) throws objetoInexistenteException {
        try {
            usuarioLogado.trocarEditoraLivro(livro.getIsbn(), editoraLivroField.getText());
            TelaController.gerarAlertaOk("Operação realizada", "A editora do livro foi trocada.");
        }
        catch (objetoInexistenteException e){
            TelaController.gerarAlertaErro("Livro inexistente.", "Não há livro com esse ISBN.");
        }
    }

    @javafx.fxml.FXML
    public void setSalvarCategoriaButton(ActionEvent actionEvent) throws objetoInexistenteException {
        try {
            usuarioLogado.trocarCategoriaLivro(livro.getIsbn(), editoraLivroField.getText());
            TelaController.gerarAlertaOk("Operação realizada", "A categoria do livro foi trocada.");
        }
        catch (objetoInexistenteException e){
            TelaController.gerarAlertaErro("Livro inexistente.", "Não há livro com esse ISBN.");
        }
    }

    @javafx.fxml.FXML
    public void setSalvarAnoButton(ActionEvent actionEvent) throws objetoInexistenteException {
        try {
            usuarioLogado.trocarAnoLivro(livro.getIsbn(), anoLivroField.getText());
        }
        catch (objetoInexistenteException e){
            TelaController.gerarAlertaErro("Livro inexistente.", "Não há livro com esse ISBN.");
        }
    }

    @javafx.fxml.FXML
    public void setCancelarButton(ActionEvent actionEvent) {
        Stage stage = TelaController.retornarStage(actionEvent);
        stage.close();
    }

    public void setLivro(Livro livro){
        this.livro = livro;
    }
    public void setBibliotecario(Bibliotecario bibliotecario){
        this.usuarioLogado = bibliotecario;
    }
}

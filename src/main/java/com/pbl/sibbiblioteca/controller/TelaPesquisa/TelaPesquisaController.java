package com.pbl.sibbiblioteca.controller.TelaPesquisa;

import com.pbl.sibbiblioteca.controller.TelaInicial.TelaInicialController;
import com.pbl.sibbiblioteca.controller.TelaRelatorio.TelaRelatorioLivrosPopulares;
import com.pbl.sibbiblioteca.exceptions.naoEncontradoException;
import com.pbl.sibbiblioteca.model.entities.Livro;
import com.pbl.sibbiblioteca.model.entities.Sistema;
import com.pbl.sibbiblioteca.utils.TelaController;
import com.pbl.sibbiblioteca.view.SibApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class TelaPesquisaController {

    @javafx.fxml.FXML
    private TableView<Livro> listaLivros;
    @javafx.fxml.FXML
    private TableColumn<Livro, String> tituloLivro;
    @javafx.fxml.FXML
    private TableColumn<Livro, String> isbnLivro;
    @javafx.fxml.FXML
    private TableColumn<Livro, String> autorLivro;
    @javafx.fxml.FXML
    private TableColumn<Livro, String> editoraLivro;
    @javafx.fxml.FXML
    private TableColumn<Livro, String> categoriaLivro;
    @javafx.fxml.FXML
    private TableColumn<Livro, String> anoLivro;
    @javafx.fxml.FXML
    private TableColumn<Livro, String> quantidadeLivro;

    @javafx.fxml.FXML
    private Button pesquisaButton;
    @javafx.fxml.FXML
    private TextField buscaTextField;
    @javafx.fxml.FXML
    private ChoiceBox<String> tipoPesquisaChoiceBox;
    @javafx.fxml.FXML
    private Button voltarButton;



    @FXML
    private void initialize() {
        tipoPesquisaChoiceBox.getItems().addAll("Todos","Título","ISBN","Autor","Editora","Categoria","Ano");
        TelaPesquisaController.setarTabelaLivros(tituloLivro, isbnLivro, autorLivro, editoraLivro, categoriaLivro, anoLivro, quantidadeLivro);
        TelaPesquisaController.desativarBotaoPesquisa(tipoPesquisaChoiceBox, pesquisaButton, buscaTextField);
    }

    @javafx.fxml.FXML
    public void realizarBusca(ActionEvent actionEvent) throws naoEncontradoException {
        try {
            listaLivros.setItems(listaDeLivros(tipoPesquisaChoiceBox, buscaTextField));
        }
        catch (naoEncontradoException e) {
            if (e.getMessage().equals("A busca não retornou em nada.")) {
                Alert erroUsuario = new Alert(Alert.AlertType.ERROR);
                erroUsuario.setTitle("Falha na pesquisa.");
                erroUsuario.setContentText("A busca não retornou em nada.");
                erroUsuario.showAndWait();
            }
        }
    }
    public static void desativarBotaoPesquisa(ChoiceBox<String> tipoPesquisaChoiceBox, Button pesquisaButton, TextField buscaTextField){
        if(tipoPesquisaChoiceBox.getSelectionModel().getSelectedItem() == null){
            pesquisaButton.setDisable(true);
            buscaTextField.setDisable(true);
        }
        else if(tipoPesquisaChoiceBox.getSelectionModel().getSelectedItem().equals("Todos")){
            buscaTextField.setDisable(true);
            pesquisaButton.setDisable(false);
        }
        else{
            pesquisaButton.setDisable(false);
            buscaTextField.setDisable(false);
        }
    }
    public static void setarTabelaLivros(TableColumn<Livro, String> tituloLivro, TableColumn<Livro, String> isbnLivro, TableColumn<Livro, String> autorLivro, TableColumn<Livro, String> editoraLivro, TableColumn<Livro, String> categoriaLivro, TableColumn<Livro, String> anoLivro, TableColumn<Livro, String> quantidadeLivro) {
        tituloLivro.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        isbnLivro.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        autorLivro.setCellValueFactory(new PropertyValueFactory<>("autor"));
        editoraLivro.setCellValueFactory(new PropertyValueFactory<>("editora"));
        categoriaLivro.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        anoLivro.setCellValueFactory(new PropertyValueFactory<>("anoDePublicacao"));
        quantidadeLivro.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
    }
    @FXML
    public static ObservableList<Livro> listaDeLivros(ChoiceBox<String> tipo, TextField campoDePesquisa) throws naoEncontradoException {
        if(tipo.getSelectionModel().isEmpty()) return null;
        else if(tipo.getSelectionModel().getSelectedItem().equals("Todos")) return FXCollections.observableArrayList(Sistema.findAll());
        else if(tipo.getSelectionModel().getSelectedItem().equals("Título")) return FXCollections.observableArrayList(Sistema.findByTitulo(campoDePesquisa.getText()));
        else if(tipo.getSelectionModel().getSelectedItem().equals("ISBN")) return FXCollections.observableArrayList(Sistema.findByIsbn(campoDePesquisa.getText()));
        else if(tipo.getSelectionModel().getSelectedItem().equals("Autor")) return FXCollections.observableArrayList(Sistema.findByAutor(campoDePesquisa.getText()));
        else if(tipo.getSelectionModel().getSelectedItem().equals("Editora")) return FXCollections.observableArrayList(Sistema.findByEditora(campoDePesquisa.getText()));
        else if(tipo.getSelectionModel().getSelectedItem().equals("Categoria")) return FXCollections.observableArrayList(Sistema.findByCategoria(campoDePesquisa.getText()));
        else if(tipo.getSelectionModel().getSelectedItem().equals("Ano")) return FXCollections.observableArrayList(Sistema.findByAnoDePublicao(campoDePesquisa.getText()));
        return null;
    }

    @FXML
    public void desativarBotao(){
        TelaPesquisaController.desativarBotaoPesquisa(tipoPesquisaChoiceBox, pesquisaButton, buscaTextField);
    }


    @javafx.fxml.FXML
    public void setVoltarButton(ActionEvent actionEvent) throws IOException {
        Stage stage = TelaController.retornarStage(actionEvent);
        stage.close();
    }
}

package com.pbl.sibbiblioteca.controller.TelaRelatorio;

import com.pbl.sibbiblioteca.controller.TelaPesquisa.TelaPesquisaController;
import com.pbl.sibbiblioteca.dao.DAO;
import com.pbl.sibbiblioteca.exceptions.naoEncontradoException;
import com.pbl.sibbiblioteca.exceptions.objetoInexistenteException;
import com.pbl.sibbiblioteca.model.entities.Livro;
import com.pbl.sibbiblioteca.model.entities.Sistema;
import com.pbl.sibbiblioteca.utils.TelaController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class TelaRelatorioLivrosPopulares {
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
    @FXML
    private Button sairButton;

    @FXML
    private void initialize() throws objetoInexistenteException {
        if(!DAO.getEmprestimoDAO().findMany().isEmpty()) listaLivros.setItems(FXCollections.observableArrayList(Sistema.getDezLivrosMaisPopulares()));
        else listaLivros.setItems(FXCollections.observableArrayList());
        setarTabelaPopulares(tituloLivro, isbnLivro, autorLivro, editoraLivro, categoriaLivro, anoLivro);
    }

    public static void setarTabelaPopulares(TableColumn<Livro, String> tituloLivro, TableColumn<Livro, String> isbnLivro, TableColumn<Livro, String> autorLivro, TableColumn<Livro, String> editoraLivro, TableColumn<Livro, String> categoriaLivro, TableColumn<Livro, String> anoLivro) {
        tituloLivro.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        isbnLivro.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        autorLivro.setCellValueFactory(new PropertyValueFactory<>("autor"));
        editoraLivro.setCellValueFactory(new PropertyValueFactory<>("editora"));
        categoriaLivro.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        anoLivro.setCellValueFactory(new PropertyValueFactory<>("anoDePublicacao"));
    }
    @FXML
    public void setSairButton(ActionEvent actionEvent) {
        Stage stage = TelaController.retornarStage(actionEvent);
        stage.close();
    }
}

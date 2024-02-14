package com.pbl.sibbiblioteca.controller.TelaMenuBibliotecario;

import com.pbl.sibbiblioteca.controller.TelaPesquisa.TelaPesquisaController;
import com.pbl.sibbiblioteca.exceptions.livroEmprestadoException;
import com.pbl.sibbiblioteca.exceptions.livroReservadoException;
import com.pbl.sibbiblioteca.exceptions.naoEncontradoException;
import com.pbl.sibbiblioteca.model.entities.Bibliotecario;
import com.pbl.sibbiblioteca.model.entities.Livro;
import com.pbl.sibbiblioteca.model.entities.Usuario;
import com.pbl.sibbiblioteca.utils.TelaController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

import static com.pbl.sibbiblioteca.controller.TelaPesquisa.TelaPesquisaController.listaDeLivros;

public class TelaMenuBibliotecarioController {

    private Bibliotecario usuarioLogado;

    private Livro livroSelecionado;

    @javafx.fxml.FXML
    private Button adicionarLivroButton;
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
    @FXML
    private Button removerLivroButton;
    @FXML
    private Button editarLivroButton;

    @FXML
    private void initialize() {
        tipoPesquisaChoiceBox.getItems().addAll("Todos","Título","ISBN","Autor","Editora","Categoria","Ano");
        TelaPesquisaController.setarTabelaLivros(tituloLivro, isbnLivro, autorLivro, editoraLivro, categoriaLivro, anoLivro, quantidadeLivro);
        TelaPesquisaController.desativarBotaoPesquisa(tipoPesquisaChoiceBox, pesquisaButton, buscaTextField);
        listaLivros.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                removerLivroButton.setDisable(false);
                editarLivroButton.setDisable(false);
                livroSelecionado = newSelection;
            }
            else{
                removerLivroButton.setDisable(true);
                editarLivroButton.setDisable(true);
            }
        });
    }

    @FXML
    public void setUsuarioLogado(ActionEvent actionEvent){
        Stage stageAtual = TelaController.retornarStage(actionEvent);
        usuarioLogado = (Bibliotecario) stageAtual.getOwner().getUserData();
        stageAtual.setUserData(usuarioLogado);
    }
    @javafx.fxml.FXML
    public void setAdicionarLivroButton(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        Stage stageAtual = TelaController.retornarStage(actionEvent);
        usuarioLogado = (Bibliotecario) stageAtual.getOwner().getUserData();
        stageAtual.setUserData(usuarioLogado);
        stage.initOwner(stageAtual);
        TelaController.StageBuilder(stage, TelaController.StageFXMLLoader("TelaAdicionarLivro.fxml"));
        stage.showAndWait();
    }

    @javafx.fxml.FXML
    public void realizarBusca(ActionEvent actionEvent) {
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

    @javafx.fxml.FXML
    public void desativarBotao(ActionEvent actionEvent) {
        TelaPesquisaController.desativarBotaoPesquisa(tipoPesquisaChoiceBox, pesquisaButton, buscaTextField);
    }

    @FXML
    public void setRemoverLivroButton(ActionEvent actionEvent) throws livroEmprestadoException, livroReservadoException, naoEncontradoException {
        setUsuarioLogado(actionEvent);
        Alert erroUsuario = new Alert(Alert.AlertType.CONFIRMATION);
        erroUsuario.setTitle("Confirmar remoção");
        erroUsuario.setContentText("Deseja mesmo remover o livro?.");
        Optional<ButtonType> option = erroUsuario.showAndWait();
        if (option.get() == ButtonType.OK) {
            usuarioLogado.removerLivro(livroSelecionado.getIsbn());
        } else if (option.get() == ButtonType.CANCEL) {
            System.out.println("Não");
        }
    }

    @FXML
    public void setEditarLivroButton(ActionEvent actionEvent) {
        setUsuarioLogado(actionEvent);
    }
}

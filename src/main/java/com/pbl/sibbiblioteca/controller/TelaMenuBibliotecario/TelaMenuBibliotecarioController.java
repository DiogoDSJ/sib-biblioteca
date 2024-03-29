package com.pbl.sibbiblioteca.controller.TelaMenuBibliotecario;

import com.pbl.sibbiblioteca.controller.TelaEditarObjeto.TelaEdicaoLivroController;
import com.pbl.sibbiblioteca.controller.TelaPesquisa.TelaPesquisaController;
import com.pbl.sibbiblioteca.dao.DAO;
import com.pbl.sibbiblioteca.exceptions.foraDeEstoqueException;
import com.pbl.sibbiblioteca.exceptions.livroEmprestadoException;
import com.pbl.sibbiblioteca.exceptions.livroReservadoException;
import com.pbl.sibbiblioteca.exceptions.naoEncontradoException;
import com.pbl.sibbiblioteca.model.entities.Bibliotecario;
import com.pbl.sibbiblioteca.model.entities.Livro;
import com.pbl.sibbiblioteca.utils.TelaController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    private Button removerUnidadeLivroButton;
    @FXML
    private Button adicionarUnidadeLivroButton;
    @FXML
    private Button sairButton;

    @FXML
    private void initialize() {
        tipoPesquisaChoiceBox.getItems().addAll("Todos","Título","ISBN","Autor","Editora","Categoria","Ano");
        TelaPesquisaController.setarTabelaLivros(tituloLivro, isbnLivro, autorLivro, editoraLivro, categoriaLivro, anoLivro, quantidadeLivro);
        TelaPesquisaController.desativarBotaoPesquisa(tipoPesquisaChoiceBox, pesquisaButton, buscaTextField);
        listaLivros.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                removerLivroButton.setDisable(false);
                editarLivroButton.setDisable(false);
                removerUnidadeLivroButton.setDisable(false);
                adicionarUnidadeLivroButton.setDisable(false);
                livroSelecionado = newSelection;
            }
            else{
                removerLivroButton.setDisable(true);
                editarLivroButton.setDisable(true);
                removerUnidadeLivroButton.setDisable(true);
                adicionarUnidadeLivroButton.setDisable(true);
            }
        });
    }


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
            listaLivros.refresh();
        }
        catch (naoEncontradoException e) {
            if (e.getMessage().equals("A busca não retornou em nada.")) {
                TelaController.gerarAlertaErro("Falha na pesquisa", "A busca não retornou em nada.");
            }
        }
    }

    @javafx.fxml.FXML
    public void desativarBotao(ActionEvent actionEvent) {
        TelaPesquisaController.desativarBotaoPesquisa(tipoPesquisaChoiceBox, pesquisaButton, buscaTextField);
    }

    @FXML
    public void setRemoverLivroButton(ActionEvent actionEvent) {
        setUsuarioLogado(actionEvent);
        Alert erroUsuario = new Alert(Alert.AlertType.CONFIRMATION);
        erroUsuario.setTitle("Confirmar remoção");
        erroUsuario.setContentText("Deseja mesmo remover o livro?.");
        Optional<ButtonType> option = erroUsuario.showAndWait();
        if (option.get() == ButtonType.OK) {
            try {
                usuarioLogado.removerLivro(livroSelecionado.getIsbn());
                TelaController.gerarAlertaOk("Livro removido.", "Uma unidade do livro foi removida do acervo.");
            }
            catch (foraDeEstoqueException e){
                TelaController.gerarAlertaOk("Livro removido.", "Quantidade mínima (0) alcançada, então o livro foi removido.");
            }
            catch (livroReservadoException e){
                TelaController.gerarAlertaErro("Erro", "Livro está reservado.");
            }
            catch (livroEmprestadoException e){
                TelaController.gerarAlertaErro("Erro", "Livro está emprestado.");
            }
            catch (naoEncontradoException e) {
                TelaController.gerarAlertaErro("Erro", "Erro inesperado, livro não encontrado.");
            }
        } else if (option.get() == ButtonType.CANCEL) {
            TelaController.gerarAlertaOk("Operação cancelada", "Operação cancelada, nenhuma alteração foi feita.");
        }
    }

    @FXML
    public void setEditarLivroButton(ActionEvent actionEvent) throws IOException {
        setUsuarioLogado(actionEvent);
        Stage stage = new Stage();
        Stage stageAtual = TelaController.retornarStage(actionEvent);
        stage.initOwner(stageAtual);
        FXMLLoader loader = TelaController.StageFXMLLoader("TelaEdicaoLivro.fxml");
        TelaController.StageBuilder(stage, loader);
        TelaEdicaoLivroController telaEdicaoLivroController = loader.getController();
        telaEdicaoLivroController.setLivro(livroSelecionado);
        telaEdicaoLivroController.setBibliotecario(usuarioLogado);
        telaEdicaoLivroController.setTituloLivroField(livroSelecionado.getTitulo());
        telaEdicaoLivroController.setAutorLivroField(livroSelecionado.getAutor());
        telaEdicaoLivroController.setIsbnLivroField(livroSelecionado.getIsbn());
        telaEdicaoLivroController.setEditoraLivroField(livroSelecionado.getEditora());
        telaEdicaoLivroController.setCategoriaLivroField(livroSelecionado.getCategoria());
        telaEdicaoLivroController.setAnoLivroField(livroSelecionado.getAnoDePublicacao());
        stage.showAndWait();
    }

    @FXML
    public void setRemoverUnidadeLivroButton(ActionEvent actionEvent) {
        try{
            livroSelecionado.removerUmaUnidade();
            TelaController.gerarAlertaOk("Confirmação", "Uma unidade do livro foi removida.");
            realizarBusca(actionEvent);
        }
        catch (foraDeEstoqueException e) {
            TelaController.gerarAlertaErro("Erro", "O livro já chegou ao mínimo de estoque (0).");
        }
    }
    @FXML
    public void setAdicionarUnidadeLivroButton(ActionEvent actionEvent) {
        livroSelecionado.adicionarUmaUnidade();
        TelaController.gerarAlertaOk("Confirmação", "Uma unidade do livro foi adicionada.");
        realizarBusca(actionEvent);
    }

    @FXML
    public void setSairButton(ActionEvent actionEvent) {
        Stage stage = TelaController.retornarStage(actionEvent);
        stage.close();
    }
}

package com.pbl.sibbiblioteca.controller.TelaInicial;

import com.pbl.sibbiblioteca.controller.TelaPesquisa.TelaPesquisaController;
import com.pbl.sibbiblioteca.exceptions.*;
import com.pbl.sibbiblioteca.model.entities.*;
import com.pbl.sibbiblioteca.model.entities.enums.StatusConta;
import com.pbl.sibbiblioteca.utils.TelaController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import static com.pbl.sibbiblioteca.controller.TelaMenuAdministrador.TelaMenuAdministradorController.setarTabelaUsuario;
import static com.pbl.sibbiblioteca.controller.TelaPesquisa.TelaPesquisaController.listaDeLivros;

public class TelaFazerEmprestimoController {
    @javafx.fxml.FXML
    private TableView<Usuario> listaUsuarios;
    @javafx.fxml.FXML
    private TableColumn<Usuario, String> nomeUsuario;
    @javafx.fxml.FXML
    private TableColumn<Usuario, String> enderecoUsuario;
    @javafx.fxml.FXML
    private TableColumn<Usuario, String> telefoneUsuario;
    @javafx.fxml.FXML
    private TableColumn<Usuario, String> usuarioUsuario;
    @javafx.fxml.FXML
    private TableColumn<Usuario, String> senhaUsuario;
    @javafx.fxml.FXML
    private TableColumn<Usuario, String> idUsuario;
    @javafx.fxml.FXML
    private TableColumn<Usuario, StatusConta> statusUsuario;
    @javafx.fxml.FXML
    private Button pesquisaButton;
    @javafx.fxml.FXML
    private TextField buscaTextField;
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
    private Button pesquisaButton1;
    @javafx.fxml.FXML
    private TextField buscaTextField1;
    @javafx.fxml.FXML
    private ChoiceBox<String> tipoPesquisaChoiceBox;

    private Leitor usuarioSelecionado;
    private Livro livroSelecionado;
    @FXML
    private Button fazerEmprestimoButton;
    @FXML
    private Button sairButton;

    public void setBibliotecario(Bibliotecario bibliotecario) {
        this.bibliotecario = bibliotecario;
    }

    private Bibliotecario bibliotecario;

    @FXML
    public void initialize(){
        tipoPesquisaChoiceBox.getItems().addAll("Todos","Título","ISBN","Autor","Editora","Categoria","Ano");
        TelaPesquisaController.setarTabelaLivros(tituloLivro, isbnLivro, autorLivro, editoraLivro, categoriaLivro, anoLivro, quantidadeLivro);
        TelaPesquisaController.desativarBotaoPesquisa(tipoPesquisaChoiceBox, pesquisaButton1, buscaTextField1);
        setarTabelaUsuario(nomeUsuario, enderecoUsuario, telefoneUsuario, usuarioUsuario, senhaUsuario, idUsuario, statusUsuario);
        listaUsuarios.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldSelection, newSelection) -> {
                    if(newSelection != null){
                        usuarioSelecionado = (Leitor) newSelection;
                    }
                    fazerEmprestimoButton.setDisable(newSelection == null || listaLivros.getSelectionModel().getSelectedItem() == null);
                });
        listaLivros.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldSelection, newSelection) -> {
                    if(newSelection != null) livroSelecionado = newSelection;
                    fazerEmprestimoButton.setDisable(newSelection == null || listaUsuarios.getSelectionModel().getSelectedItem() == null);
                });
    }

    @javafx.fxml.FXML
    public void desativarBotao(ActionEvent actionEvent) {
        TelaPesquisaController.desativarBotaoPesquisa(tipoPesquisaChoiceBox, pesquisaButton1, buscaTextField1);
    }

    @javafx.fxml.FXML
    public void realizarBuscaUsuario(ActionEvent actionEvent) {
        try {
            listaUsuarios.setItems(FXCollections.observableArrayList(Sistema.findByUsuarioLeitor(buscaTextField.getText())));
        }
        catch (naoEncontradoException e){
            TelaController.gerarAlertaErro("Falha na pesquisa", "A busca não retornou em nada.");
        }
    }

    @javafx.fxml.FXML
    public void realizarBuscaLivro(ActionEvent actionEvent) {
        try {
            listaLivros.setItems(listaDeLivros(tipoPesquisaChoiceBox, buscaTextField1));
            listaLivros.refresh();
        } catch (naoEncontradoException e) {
            if (e.getMessage().equals("A busca não retornou em nada.")) {
                TelaController.gerarAlertaErro("Falha na pesquisa", "A busca não retornou em nada.");
            }
        }
    }

    @FXML
    public void setFazerEmprestimoButton(ActionEvent actionEvent) {
        try {
            bibliotecario.fazerEmprestimo(usuarioSelecionado.getId(), livroSelecionado.getIsbn());
            TelaController.gerarAlertaOk("Confirmação", "O empréstimo foi feito com sucesso.");
            try {
                listaLivros.setItems(listaDeLivros(tipoPesquisaChoiceBox, buscaTextField1));
                listaLivros.refresh();
            }
                catch (naoEncontradoException e){
                listaLivros.setItems(FXCollections.observableArrayList());
                listaLivros.refresh();
            }

        } catch (naoEncontradoException e) {
            if(e.getMessage().equals("Leitor não existe.")) TelaController.gerarAlertaErro("Erro", "Leitor não existe.");
            else if(e.getMessage().equals("Livro não existe.")) TelaController.gerarAlertaErro("Erro", "Livro não existe.");
            else TelaController.gerarAlertaErro("Erro", "Erro1");
        } catch (objetoInexistenteException e) {
            TelaController.gerarAlertaErro("Erro", "Leitor não existe.");
        } catch (foraDeEstoqueException e) {
            if(e.getMessage().equals("Não há estoque disponível para esse livro.")) TelaController.gerarAlertaErro("Erro", "Não há estoque disponível para esse livro.");
            else if(e.getMessage().equals("Usuário alcançou o máximo de livros.")) TelaController.gerarAlertaErro("Erro", "Usuário alcançou o máximo de livros.");
            else TelaController.gerarAlertaErro("Erro", "Erro2");
        } catch (usuarioBloqueadoException e) {
            TelaController.gerarAlertaErro("Erro", "Usuário em atraso.");
        } catch (livroReservadoException e) {
            TelaController.gerarAlertaErro("Erro", "Livro está reservado para outro usuário.");
        } catch (objetoDuplicadoException e) {
            TelaController.gerarAlertaErro("Erro", "Usuário não pode ter dois livros iguais.");
        }
    }

    @FXML
    public void setSairButton(ActionEvent actionEvent) {
        Stage stage = TelaController.retornarStage(actionEvent);
        stage.close();
    }
}

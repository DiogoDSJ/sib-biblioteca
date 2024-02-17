package com.pbl.sibbiblioteca.controller.TelaInicial;

import com.pbl.sibbiblioteca.controller.TelaPesquisa.TelaPesquisaController;
import com.pbl.sibbiblioteca.exceptions.*;
import com.pbl.sibbiblioteca.model.entities.Leitor;
import com.pbl.sibbiblioteca.model.entities.Livro;
import com.pbl.sibbiblioteca.model.entities.Sistema;
import com.pbl.sibbiblioteca.utils.TelaController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import static com.pbl.sibbiblioteca.controller.TelaPesquisa.TelaPesquisaController.listaDeLivros;

public class TelaReservarLivroController {
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
    private Button reservarLivroButton;

    private Livro livroSelecionado;
    private Leitor usuarioLogado;
    @FXML
    private Button sairButton;

    @FXML
    private void initialize() {
        tipoPesquisaChoiceBox.getItems().addAll("Todos","Título","ISBN","Autor","Editora","Categoria","Ano");
        TelaPesquisaController.setarTabelaLivros(tituloLivro, isbnLivro, autorLivro, editoraLivro, categoriaLivro, anoLivro, quantidadeLivro);
        TelaPesquisaController.desativarBotaoPesquisa(tipoPesquisaChoiceBox, pesquisaButton, buscaTextField);
        listaLivros.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                reservarLivroButton.setDisable(false);
                livroSelecionado = newSelection;
            }
            else{
                reservarLivroButton.setDisable(true);
            }
        });
    }


    @javafx.fxml.FXML
    public void realizarBusca(ActionEvent actionEvent) {
        try {
            listaLivros.setItems(listaDeLivros(tipoPesquisaChoiceBox, buscaTextField));
            listaLivros.refresh();
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


    public void setUsuarioLogado(Leitor usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    @javafx.fxml.FXML
    public void setReservarLivroButton(ActionEvent actionEvent) {
        try {
            usuarioLogado.fazerReserva(livroSelecionado.getIsbn());
            TelaController.gerarAlertaOk("Confirmação", "Reserva feita com sucesso.");
            Sistema.ativarReservasLivros();
        } catch (objetoInexistenteException e) {
            TelaController.gerarAlertaErro("Erro", "Leitor não existe.");
        } catch (objetoDuplicadoException e) {
            if(e.getMessage().equals("Usuário não pode ter dois livros iguais.")) TelaController.gerarAlertaErro("Item repetido", "Usuário não pode ter dois livros iguais.");
            else TelaController.gerarAlertaErro("Item repetido", "Usuário já fez uma reserva desse livro.");
        } catch (naoEncontradoException e) {
            TelaController.gerarAlertaErro("Erro", "Livro não existe.");
        } catch (usuarioBloqueadoException e) {
            TelaController.gerarAlertaErro("Usuário bloqueado", "O usuario está com algum atraso e não pode fazer reserva.");
        } catch (foraDeEstoqueException e) {
            TelaController.gerarAlertaErro("Limite alcançado.", "Usuário já tem muitas reservas pendentes.");
        }

    }

    @FXML
    public void setSairButton(ActionEvent actionEvent) {
        Stage stage = TelaController.retornarStage(actionEvent);
        stage.close();
    }
}

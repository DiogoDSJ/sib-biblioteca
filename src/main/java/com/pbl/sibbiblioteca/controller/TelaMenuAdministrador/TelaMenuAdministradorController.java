package com.pbl.sibbiblioteca.controller.TelaMenuAdministrador;

import com.pbl.sibbiblioteca.controller.TelaAdicionarObjeto.TelaAdicionarUsuarioController;
import com.pbl.sibbiblioteca.controller.TelaEditarObjeto.TelaEdicaoUsuarioController;
import com.pbl.sibbiblioteca.controller.TelaMulta.TelaAplicarMultaController;
import com.pbl.sibbiblioteca.exceptions.foraDeEstoqueException;
import com.pbl.sibbiblioteca.exceptions.naoEncontradoException;
import com.pbl.sibbiblioteca.exceptions.objetoInexistenteException;
import com.pbl.sibbiblioteca.exceptions.usuarioPendenciasException;
import com.pbl.sibbiblioteca.model.entities.*;
import com.pbl.sibbiblioteca.model.entities.enums.Cargo;
import com.pbl.sibbiblioteca.model.entities.enums.StatusConta;
import com.pbl.sibbiblioteca.utils.TelaController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class TelaMenuAdministradorController {
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
    private Button pesquisaButton;
    @javafx.fxml.FXML
    private TextField buscaTextField;
    @javafx.fxml.FXML
    private ChoiceBox<String> tipoPesquisaChoiceBox;

    private Administrador administrador;
    private Usuario usuarioSelecionado;
    @FXML
    private Button cadastrarButton;
    @FXML
    private Button removerButton;
    @FXML
    private Button multaButton;
    @FXML
    private Button editarUsuarioButton;
    @FXML
    private Button historicoButton;
    @FXML
    private TableColumn<Usuario, String> idUsuario;
    @FXML
    private TableColumn<Usuario, StatusConta> statusUsuario;
    @FXML
    private Button verMultaButton;
    @FXML
    private Button removerMultaButton;
    @FXML
    private Button sairButton;

    @FXML
    public void initialize(){
        tipoPesquisaChoiceBox.getItems().addAll("ADMINISTRADOR", "BIBLIOTECARIO", "LEITOR");
        setarTabelaUsuario(nomeUsuario, enderecoUsuario, telefoneUsuario, usuarioUsuario, senhaUsuario, idUsuario, statusUsuario);
        listaUsuarios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                removerButton.setDisable(false);
                editarUsuarioButton.setDisable(false);
                usuarioSelecionado = newSelection;
                if(newSelection.getCargo().equals(Cargo.LEITOR)){
                    historicoButton.setDisable(false);
                    multaButton.setDisable(false);
                    removerMultaButton.setDisable(false);
                    verMultaButton.setDisable(false);
                }
                else{
                    historicoButton.setDisable(true);
                    multaButton.setDisable(true);
                    removerMultaButton.setDisable(true);
                    verMultaButton.setDisable(true);
                }
            }
            else{
                removerButton.setDisable(true);
                editarUsuarioButton.setDisable(true);
                historicoButton.setDisable(true);
                multaButton.setDisable(true);
                removerMultaButton.setDisable(true);
                verMultaButton.setDisable(true);
            }
        });
    }

    @javafx.fxml.FXML
    public void realizarBusca(ActionEvent actionEvent) throws naoEncontradoException {
        listaUsuarios.refresh();
        if(tipoPesquisaChoiceBox.getSelectionModel().isEmpty()){
            TelaController.gerarAlertaErro("Cargo inválido.", "Selecione um dos cargos para prosseguir.");
            return;
        }
        listaUsuarios.setItems(listaDeUsuarios(tipoPesquisaChoiceBox, buscaTextField.getText()));

    }

    @javafx.fxml.FXML
    public void desativarBotao(ActionEvent actionEvent) {
    }


    public static ObservableList<Usuario> listaDeUsuarios(ChoiceBox<String> tipo, String usuario) throws naoEncontradoException {
        if(tipo.getSelectionModel().isEmpty()) return null;
        else if(tipo.getSelectionModel().getSelectedItem().equals("ADMINISTRADOR")) return FXCollections.observableArrayList(Sistema.findByUsuarioAdministrador(usuario));
        else if(tipo.getSelectionModel().getSelectedItem().equals("BIBLIOTECARIO")) return FXCollections.observableArrayList(Sistema.findByUsuarioBibliotecario(usuario));
        else if(tipo.getSelectionModel().getSelectedItem().equals("LEITOR")) return FXCollections.observableArrayList(Sistema.findByUsuarioLeitor(usuario));
        return null;
    }

    public static void setarTabelaUsuario(TableColumn<Usuario, String> nomeUsuario, TableColumn<Usuario,
            String> enderecoUsuario, TableColumn<Usuario, String> telefoneUsuario, TableColumn<Usuario, String> usuarioUsuario,
                                          TableColumn<Usuario, String> senhaUsuario, TableColumn<Usuario, String> idUsuario, TableColumn<Usuario, StatusConta> statusUsuario)
    {
        nomeUsuario.setCellValueFactory(new PropertyValueFactory<>("nome"));
        enderecoUsuario.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        telefoneUsuario.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        usuarioUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        senhaUsuario.setCellValueFactory(new PropertyValueFactory<>("senhaDeAcesso"));
        idUsuario.setCellValueFactory(new PropertyValueFactory<>("id"));
        statusUsuario.setCellValueFactory(new PropertyValueFactory<>("statusDaConta"));
    }

    public void setAdministrador(Administrador administrador){
        this.administrador = administrador;
    }

    @FXML
    public void setCadastrarButton(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        Stage stageAtual = TelaController.retornarStage(actionEvent);
        stage.initOwner(stageAtual);
        FXMLLoader loader = TelaController.StageFXMLLoader("TelaAdicionarUsuario.fxml");
        TelaController.StageBuilder(stage, loader);
        TelaAdicionarUsuarioController telaAdicionarUsuarioController = loader.getController();
        telaAdicionarUsuarioController.setAdministrador(administrador);
        stage.showAndWait();
    }

    @FXML
    public void setRemoverButton(ActionEvent actionEvent) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmar remoção");
        alerta.setContentText("Deseja mesmo remover o usuário?.");
        Optional<ButtonType> option = alerta.showAndWait();
        if (option.get() == ButtonType.OK) {
            try {
                if(usuarioSelecionado.getCargo().equals(Cargo.BIBLIOTECARIO)) {
                    administrador.removerBibliotecario(usuarioSelecionado.getId());
                    TelaController.gerarAlertaOk("Bibliotecario removido.", "A conta do bibliotecário foi removido.");
                }
                else if(usuarioSelecionado.getCargo().equals(Cargo.ADMINISTRADOR)) {
                    if(usuarioSelecionado.equals(administrador)){
                        TelaController.gerarAlertaErro("Erro", "Você não pode apagar sua conta logada.");
                        return;
                    }
                    administrador.removerAdministrador(usuarioSelecionado.getId());
                    TelaController.gerarAlertaOk("Administrador removido.", "A conta do administrador foi removida.");
                }
                else if(usuarioSelecionado.getCargo().equals(Cargo.LEITOR)){
                    administrador.removerLeitor(usuarioSelecionado.getId());
                }
            } catch (naoEncontradoException e) {
                TelaController.gerarAlertaErro("Erro", "Objeto não existe.");
            } catch (usuarioPendenciasException e) {
                TelaController.gerarAlertaErro("Erro", "Usuário tem pendências, não pode ser removido.");
            } catch (foraDeEstoqueException e) {
                TelaController.gerarAlertaErro("Erro", "Usuário ultrapassou o número de reservas máximo.");
            }
        } else if (option.get() == ButtonType.CANCEL) {
            TelaController.gerarAlertaOk("Operação cancelada", "Operação cancelada, nenhuma alteração foi feita.");
        }
    }

    @FXML
    public void setMultaButton(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        Stage stageAtual = TelaController.retornarStage(actionEvent);
        stage.initOwner(stageAtual);
        FXMLLoader loader = TelaController.StageFXMLLoader("TelaDeAplicarMulta.fxml");
        TelaController.StageBuilder(stage, loader);
        TelaAplicarMultaController telaAplicarMultaController = loader.getController();
        telaAplicarMultaController.setAdministrador(administrador);
        telaAplicarMultaController.setUsuarioSelecionado((Leitor) usuarioSelecionado);
        stage.showAndWait();
    }

    @FXML
    public void setEditarUsuarioButton(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        Stage stageAtual = TelaController.retornarStage(actionEvent);
        stage.initOwner(stageAtual);
        FXMLLoader loader = TelaController.StageFXMLLoader("TelaEdicaoUsuario.fxml");
        TelaController.StageBuilder(stage, loader);
        TelaEdicaoUsuarioController telaEdicaoUsuarioController = loader.getController();
        telaEdicaoUsuarioController.setAdministrador(administrador);
        telaEdicaoUsuarioController.setUsuario(usuarioSelecionado);
        telaEdicaoUsuarioController.setUsuarioField(usuarioSelecionado.getUsuario());
        telaEdicaoUsuarioController.setSenhaField(usuarioSelecionado.getSenhaDeAcesso());
        telaEdicaoUsuarioController.setNomeField(usuarioSelecionado.getNome());
        telaEdicaoUsuarioController.setEnderecoField(usuarioSelecionado.getEndereco());
        telaEdicaoUsuarioController.setTelefoneField(usuarioSelecionado.getTelefone());
        stage.showAndWait();

    }

    @FXML
    public void setHistoricoButton(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        Stage stageAtual = TelaController.retornarStage(actionEvent);
        stage.initOwner(stageAtual);
        FXMLLoader loader = TelaController.StageFXMLLoader("TelaHistoricoEmprestimo.fxml");
        TelaController.StageBuilder(stage, loader);
        TelaHistoricoEmprestimoController telaHistoricoEmprestimoController = loader.getController();
        telaHistoricoEmprestimoController.setLeitor((Leitor) usuarioSelecionado);
        stage.showAndWait();
    }

    @FXML
    public void setVerMultaButton(ActionEvent actionEvent) {
        Multa multa = administrador.retornarMultaLeitor((Leitor) usuarioSelecionado);
        if(multa == null) TelaController.gerarAlertaErro("Erro", "Usuário não está multado.");
        else{
            String diaInicio = TelaController.parseData(multa.getDataInicio());
            String diaFim = TelaController.parseData(multa.getDataFim());
            TelaController.gerarAlertaOk("Multa leitor", "O usuário está multado desde o dia " + diaInicio + " até o dia " + diaFim);
        }
    }

    @FXML
    public void setRemoverMultaButton(ActionEvent actionEvent) {
        try {
            administrador.desbloquearLeitor((Leitor)usuarioSelecionado);
            TelaController.gerarAlertaOk("Aviso", "Leitor foi desbloqueado com sucesso.");
        }
        catch (objetoInexistenteException e){
            TelaController.gerarAlertaErro("Aviso", "Leitor não está bloqueado.");
        }
    }

    @FXML
    public void setSairButton(ActionEvent actionEvent) {
        Stage stage = TelaController.retornarStage(actionEvent);
        stage.close();
    }
}

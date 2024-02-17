package com.pbl.sibbiblioteca.controller.TelaInicial;
import com.pbl.sibbiblioteca.controller.TelaMenuAdministrador.TelaMenuAdministradorController;
import com.pbl.sibbiblioteca.dao.DAO;
import com.pbl.sibbiblioteca.exceptions.naoEncontradoException;
import com.pbl.sibbiblioteca.model.entities.*;
import com.pbl.sibbiblioteca.model.entities.enums.Cargo;
import com.pbl.sibbiblioteca.model.entities.enums.StatusConta;
import com.pbl.sibbiblioteca.utils.TelaController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.IOException;
import java.time.LocalDate;

public class TelaInicialController {
    @javafx.fxml.FXML
    private Text MsgBemVindo;

    private Usuario usuario;
    @javafx.fxml.FXML
    private Button pesquisarLivroButton;
    @javafx.fxml.FXML
    private Button emprestarLivroButton;
    @javafx.fxml.FXML
    private Button reservarLivroButton;
    @javafx.fxml.FXML
    private Button fazerLoginButton;
    @javafx.fxml.FXML
    private Button desconectarButton;
    @javafx.fxml.FXML
    private Button relatorioButton;
    @javafx.fxml.FXML
    private Button menuAdmButton;
    @javafx.fxml.FXML
    private Button meusEmprestimosButton;
    @javafx.fxml.FXML
    private Button minhasReservasButton;
    @javafx.fxml.FXML
    private Button menuBibliotecarioButton;
    @FXML
    private Text numeroEmprestimos;
    @FXML
    private Text numeroReservas;
    @FXML
    private Button sairButton;
    @FXML
    private Text statusConta;
    @FXML
    private Text dataFimMulta;

    @FXML
    public void initialize() {
        if(usuario != null) {
            fazerLoginButton.setDisable(true);
            desconectarButton.setDisable(false);
            if (usuario.getCargo().equals(Cargo.LEITOR)) {
                reservarLivroButton.setDisable(false);
                minhasReservasButton.setDisable(false);
                meusEmprestimosButton.setDisable(false);
                numeroReservas.setVisible(true);
                numeroEmprestimos.setVisible(true);
                statusConta.setVisible(true);
                if(usuario.getStatusDaConta().equals(StatusConta.BLOQUEADA)) {
                    dataFimMulta.setVisible(true);
                }
                setTextos();
            } else if (usuario.getCargo().equals(Cargo.BIBLIOTECARIO)) {
                menuBibliotecarioButton.setDisable(false);
                emprestarLivroButton.setDisable(false);
                numeroReservas.setVisible(false);
                numeroEmprestimos.setVisible(false);
                statusConta.setVisible(false);
                dataFimMulta.setVisible(false);
            }
            else if(usuario.getCargo().equals(Cargo.ADMINISTRADOR)){
                menuAdmButton.setDisable(false);
                menuBibliotecarioButton.setDisable(false);
                emprestarLivroButton.setDisable(false);
                numeroReservas.setVisible(false);
                numeroEmprestimos.setVisible(false);
                statusConta.setVisible(false);
                dataFimMulta.setVisible(false);
            }
            String nome = usuario.getNome();
            Cargo cargo = usuario.getCargo();
            MsgBemVindo.setText("Você entrou como " + cargo.toString().toLowerCase() + ", bem vindo " + nome + ".");

        }
        else{
            numeroReservas.setVisible(false);
            numeroEmprestimos.setVisible(false);
            statusConta.setVisible(false);
            dataFimMulta.setVisible(false);
            MsgBemVindo.setText("Você entrou como convidado.");
        }
        MsgBemVindo.autosize();
    }

    public void setTextos(){
        String nEmp = ((Leitor) usuario).getNumeroDeEmprestimos();
        String nRes = ((Leitor) usuario).getNumeroDeReservas();
        numeroEmprestimos.setText("Número de emprestimos: "+ nEmp);
        numeroReservas.setText("Número de reservas: "+nRes);
        statusConta.setText("Status da conta: "+usuario.getStatusDaConta());
        if(usuario.getStatusDaConta().equals(StatusConta.BLOQUEADA)) {
            dataFimMulta.setVisible(true);
            LocalDate dateFim = DAO.getMultaDAO().findByIdMutuario(usuario.getId()).getDataFim();
            dataFimMulta.setText("Fim da multa: "+TelaController.parseData(dateFim));
        }
    }
    @FXML
    public void setPesquisarLivroButton(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        Stage stageAtual = TelaController.retornarStage(actionEvent);
        stage.initOwner(stageAtual);
        TelaController.StageBuilder(stage, TelaController.StageFXMLLoader("TelaPesquisa.fxml"));
        stage.showAndWait();
    }

    @FXML
    public void setFazerLoginButton(ActionEvent actionEvent) throws IOException, naoEncontradoException {
        Stage stage = TelaController.retornarStage(actionEvent);
        FXMLLoader loader = TelaController.StageFXMLLoader("TelaDeLogin.fxml");
        Parent root = loader.load();
        stage.setScene(new Scene(root));
    }

    @FXML
    public void setDesconectarButton(ActionEvent event) throws IOException {
        Stage stage = TelaController.retornarStage(event);
        FXMLLoader loader = TelaController.StageFXMLLoader("TelaDeInicio.fxml");
        Parent root = loader.load();
        stage.setScene(new Scene(root));
    }

    @FXML
    public void setRelatorioButton(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        Stage stageAtual = TelaController.retornarStage(actionEvent);
        stage.initOwner(stageAtual);
        TelaController.StageBuilder(stage, TelaController.StageFXMLLoader("TelaDeRelatorio.fxml"));
        stage.showAndWait();
    }

    @FXML
    public void setMenuBibliotecarioButton(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        Stage stageAtual = TelaController.retornarStage(actionEvent);
        stageAtual.setUserData(usuario);
        stage.initOwner(stageAtual);
        TelaController.StageBuilder(stage, TelaController.StageFXMLLoader("TelaMenuBibliotecario.fxml"));
        stage.showAndWait();
    }

    @FXML
    public void setMenuAdmButton(ActionEvent actionEvent) throws IOException{
        Stage stage = new Stage();
        Stage stageAtual = TelaController.retornarStage(actionEvent);
        stageAtual.setUserData(usuario);
        stage.initOwner(stageAtual);
        FXMLLoader loader = TelaController.StageFXMLLoader("TelaMenuAdministrador.fxml");
        TelaController.StageBuilder(stage, loader);
        TelaMenuAdministradorController telaMenuAdministradorController = loader.getController();
        telaMenuAdministradorController.setAdministrador((Administrador) usuario);
        stage.showAndWait();
    }
    public void setUsuario(Usuario usuario){
        this.usuario = usuario;
    }

    public Usuario getUsuario() { return this.usuario; }

    @FXML
    public void setReservarLivroButton(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        Stage stageAtual = TelaController.retornarStage(actionEvent);
        stage.initOwner(stageAtual);
        FXMLLoader loader = TelaController.StageFXMLLoader("TelaReservarLivro.fxml");
        TelaController.StageBuilder(stage, loader);
        TelaReservarLivroController TelaReservarLivroController = loader.getController();
        TelaReservarLivroController.setUsuarioLogado((Leitor) usuario);
        stage.showAndWait();
        setTextos();
    }

    @FXML
    public void setMinhasReservasButton(ActionEvent actionEvent) throws IOException {
        try{
            Sistema.findReservasLeitor(usuario.getId());
        }
        catch (naoEncontradoException e){
            TelaController.gerarAlertaErro("Erro", "O usuário não tem reservas.");
            return;
        }
        Stage stage = new Stage();
        Stage stageAtual = TelaController.retornarStage(actionEvent);
        stage.initOwner(stageAtual);
        FXMLLoader loader = TelaController.StageFXMLLoader("TelaMinhasReservas.fxml");
        TelaController.StageBuilder(stage, loader);
        TelasMinhasReservasController telasMinhasReservasController= loader.getController();
        telasMinhasReservasController.setLeitor((Leitor) usuario);
        stage.showAndWait();
        setTextos();
    }

    @FXML
    public void setMeusEmprestimosButton(ActionEvent actionEvent) throws IOException {
        try{
            Sistema.findEmprestimosLeitor(usuario.getId());
        }
        catch (naoEncontradoException e){
            TelaController.gerarAlertaErro("Erro", "O usuário não tem empréstimos.");
            return;
        }
        Stage stage = new Stage();
        Stage stageAtual = TelaController.retornarStage(actionEvent);
        stage.initOwner(stageAtual);
        FXMLLoader loader = TelaController.StageFXMLLoader("TelaMeusEmprestimos.fxml");
        TelaController.StageBuilder(stage, loader);
        TelaMeusEmprestimosController telaMeusEmprestimosController= loader.getController();
        telaMeusEmprestimosController.setLeitor((Leitor) usuario);
        stage.showAndWait();
        setTextos();
    }

    @FXML
    public void setEmprestarLivroButton(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        Stage stageAtual = TelaController.retornarStage(actionEvent);
        stage.initOwner(stageAtual);
        FXMLLoader loader = TelaController.StageFXMLLoader("TelaFazerEmprestimo.fxml");
        TelaController.StageBuilder(stage, loader);
        TelaFazerEmprestimoController TelaFazerEmprestimoController= loader.getController();
        TelaFazerEmprestimoController.setBibliotecario((Bibliotecario) usuario);
        stage.showAndWait();
    }

    @FXML
    public void setSairButton(ActionEvent actionEvent) {
        Stage stage = TelaController.retornarStage(actionEvent);
        stage.close();
    }
}

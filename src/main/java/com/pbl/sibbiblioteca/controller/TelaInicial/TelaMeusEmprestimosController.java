package com.pbl.sibbiblioteca.controller.TelaInicial;

import com.pbl.sibbiblioteca.dao.DAO;
import com.pbl.sibbiblioteca.exceptions.foraDeEstoqueException;
import com.pbl.sibbiblioteca.exceptions.naoEncontradoException;
import com.pbl.sibbiblioteca.exceptions.objetoInexistenteException;
import com.pbl.sibbiblioteca.exceptions.usuarioPendenciasException;
import com.pbl.sibbiblioteca.model.entities.Emprestimo;
import com.pbl.sibbiblioteca.model.entities.Leitor;
import com.pbl.sibbiblioteca.model.entities.Sistema;
import com.pbl.sibbiblioteca.utils.TelaController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.time.LocalDate;

import static com.pbl.sibbiblioteca.controller.TelaMenuAdministrador.TelaHistoricoEmprestimoController.setarTabelaEmprestimo;

public class TelaMeusEmprestimosController {
    @javafx.fxml.FXML
    private Button renovarEmprestimoButton;
    @javafx.fxml.FXML
    private Button devolverLivroButton;
    @javafx.fxml.FXML
    private TableView<Emprestimo> listaEmprestimos;
    @javafx.fxml.FXML
    private TableColumn<Emprestimo, String>isbnLivro;
    @javafx.fxml.FXML
    private TableColumn<Emprestimo, LocalDate> dataInicio;
    @javafx.fxml.FXML
    private TableColumn<Emprestimo, LocalDate> dataFim;
    @javafx.fxml.FXML
    private TableColumn<Emprestimo, String> idMutuario;
    @javafx.fxml.FXML
    private TableColumn<Emprestimo, String> idEmprestimo;

    private Leitor leitor;
    private Emprestimo emprestimoSelecionado;
    @FXML
    private Button sairButton;

    public void setLeitor(Leitor leitor) {
        this.leitor = leitor;
        try {
            listaEmprestimos.setItems(emprestimosLeitor(leitor));
        }
        catch (naoEncontradoException e){
            TelaController.gerarAlertaErro("Erro", "O usuário não tem empréstimos.");
        }
    }

    @FXML
    public void initialize(){
        setarTabelaEmprestimo(isbnLivro, dataInicio, dataFim, idMutuario, idEmprestimo);
        listaEmprestimos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                renovarEmprestimoButton.setDisable(false);
                devolverLivroButton.setDisable(false);
                emprestimoSelecionado = newSelection;
            }
            else{
                renovarEmprestimoButton.setDisable(true);
                devolverLivroButton.setDisable(true);
            }
        });
    }
    public static ObservableList<Emprestimo> emprestimosLeitor(Leitor leitor) throws naoEncontradoException {
        return FXCollections.observableArrayList(Sistema.findEmprestimosLeitor(leitor.getId()));
    }
    @javafx.fxml.FXML
    public void setRenovarEmprestimoButton(ActionEvent actionEvent) {
        try {
            leitor.renovarEmprestimo(emprestimoSelecionado.getIsbnLivro());
            TelaController.gerarAlertaOk("Operação realizada.", "O empréstimo foi renovado em mais 7 dias a partir da data fim.");
            try{
                listaEmprestimos.setItems(FXCollections.observableArrayList(Sistema.findEmprestimosLeitor(leitor.getId())));
                listaEmprestimos.refresh();
            } catch (naoEncontradoException e) {
                listaEmprestimos.setItems(FXCollections.observableArrayList());
                listaEmprestimos.refresh();
            }
        } catch (usuarioPendenciasException e) {
            if(e.getMessage().equals("Usuário em atraso, não é possivel renovar.")){
                TelaController.gerarAlertaErro("Erro", "O usuário está com pendências, não é possivel renovar.");
            }
            else if(e.getMessage().equals("Esse empréstimo já alcançou o limite de renovações.")){
                TelaController.gerarAlertaErro("Erro", "Esse empréstimo já alcançou o limite de renovações.");
            }
        } catch (objetoInexistenteException e) {
            TelaController.gerarAlertaErro("Erro", "Não há emprestimo com esse livro.");
        }
    }

    @javafx.fxml.FXML
    public void setDevolverLivroButton(ActionEvent actionEvent) {
        try {
            Sistema.devolverLivro(leitor, DAO.getLivroDAO().findByIsbn(emprestimoSelecionado.getIsbnLivro()));
            TelaController.gerarAlertaOk("Devolução concluida", "A devolução do livro foi feita.");
            try {
                listaEmprestimos.setItems(emprestimosLeitor(leitor));
            }catch (naoEncontradoException e){
                listaEmprestimos.setItems(FXCollections.observableArrayList());
            }
        } catch (objetoInexistenteException e) {
            TelaController.gerarAlertaErro("Erro", "Não há um empréstimo com este livro.");
        } catch (foraDeEstoqueException e) {
            TelaController.gerarAlertaErro("Erro", "Algum ocorrido inesperado aconteceu, não foi possivel devolver.");
        }
    }

    @FXML
    public void setSairButton(ActionEvent actionEvent) {
        Stage stage = TelaController.retornarStage(actionEvent);
        stage.close();
    }
}

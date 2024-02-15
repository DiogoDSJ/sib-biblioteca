package com.pbl.sibbiblioteca.controller.TelaMenuAdministrador;

import com.pbl.sibbiblioteca.model.entities.Emprestimo;
import com.pbl.sibbiblioteca.model.entities.Leitor;
import com.pbl.sibbiblioteca.utils.TelaController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

public class TelaHistoricoEmprestimoController {
    @javafx.fxml.FXML
    private TableColumn<Emprestimo, String> isbnLivro;
    @javafx.fxml.FXML
    private TableColumn<Emprestimo, LocalDate> dataInicio;
    @javafx.fxml.FXML
    private TableColumn<Emprestimo, LocalDate> dataFim;
    @javafx.fxml.FXML
    private TableColumn<Emprestimo, String> idMutuario;
    @javafx.fxml.FXML
    private TableColumn<Emprestimo, String> idEmprestimo;
    @javafx.fxml.FXML
    private TableView<Emprestimo> listaEmprestimos;
    private Leitor leitor;

    public void setLeitor(Leitor leitor) {
        this.leitor = leitor;
        listaEmprestimos.setItems(historicoEmprestimo(leitor));
    }

    @FXML
    public void initialize(){
        setarTabelaEmprestimo(isbnLivro, dataInicio, dataFim, idMutuario, idEmprestimo);
    }

    public static ObservableList<Emprestimo> historicoEmprestimo(Leitor leitor){
        return FXCollections.observableArrayList(leitor.getHistoricoEmprestimos());
    }
    public void setarTabelaEmprestimo(TableColumn<Emprestimo, String> isbnLivro, TableColumn<Emprestimo, LocalDate> dataInicio,
        TableColumn<Emprestimo, LocalDate> dataFim, TableColumn<Emprestimo, String> idMutuario, TableColumn<Emprestimo, String> idEmprestimo)
    {
        isbnLivro.setCellValueFactory(new PropertyValueFactory<>("isbnLivro"));
        dataInicio.setCellFactory(column -> {
            TableCell<Emprestimo, LocalDate> cell = new TableCell<Emprestimo, LocalDate>() {
                @Override
                protected void updateItem(LocalDate data, boolean empty) {
                    super.updateItem(data, empty);
                    if(empty) {
                        setText(null);
                    }
                    else {
                        setText(TelaController.parseData(data));
                    }
                }
            };

            return cell;
        });
        dataFim.setCellFactory(column -> {
            TableCell<Emprestimo, LocalDate> cell = new TableCell<Emprestimo, LocalDate>() {
                @Override
                protected void updateItem(LocalDate data, boolean empty) {
                    super.updateItem(data, empty);
                    if(empty) {
                        setText(null);
                    }
                    else {
                        setText(TelaController.parseData(data));
                    }
                }
            };

            return cell;
        });
        dataInicio.setCellValueFactory(new PropertyValueFactory<>("dataInicio"));
        dataFim.setCellValueFactory(new PropertyValueFactory<>("dataFim"));
        idMutuario.setCellValueFactory(new PropertyValueFactory<>("idMutuario"));
        idEmprestimo.setCellValueFactory(new PropertyValueFactory<>("idEmprestimo"));
    }
}

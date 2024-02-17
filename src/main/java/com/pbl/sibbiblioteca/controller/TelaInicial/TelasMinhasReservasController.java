package com.pbl.sibbiblioteca.controller.TelaInicial;

import com.pbl.sibbiblioteca.exceptions.foraDeEstoqueException;
import com.pbl.sibbiblioteca.exceptions.naoEncontradoException;
import com.pbl.sibbiblioteca.model.entities.Leitor;
import com.pbl.sibbiblioteca.model.entities.Reserva;
import com.pbl.sibbiblioteca.model.entities.Sistema;
import com.pbl.sibbiblioteca.utils.TelaController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.LocalDate;

public class TelasMinhasReservasController {
    @javafx.fxml.FXML
    private TableColumn<Reserva, String> idReserva;
    @javafx.fxml.FXML
    private TableColumn<Reserva, LocalDate>  dataReserva;
    @javafx.fxml.FXML
    private TableColumn<Reserva, LocalDate>  dataInicioReserva;
    @javafx.fxml.FXML
    private TableColumn<Reserva, LocalDate>  dataFimReserva;
    @javafx.fxml.FXML
    private TableColumn<Reserva, String>  isbnLivro;
    @javafx.fxml.FXML
    private TableColumn<Reserva, String>  idReservante;
    @javafx.fxml.FXML
    private TableView<Reserva> listaReservas;
    private Leitor leitor;
    @FXML
    private Button removerReservaButton;
    private Reserva reservaSelecionada;
    @FXML
    private Button sairButton;

    public void setLeitor(Leitor leitor) {
        this.leitor = leitor;
        try {
            listaReservas.setItems(reservasLeitor(leitor));
        }catch (naoEncontradoException e){
            TelaController.gerarAlertaErro("Erro", "O usuário não tem reservas.");
        }
    }

    @FXML
    public void initialize(){
        setarTabelaReserva(idReserva, dataReserva, dataInicioReserva, dataFimReserva, isbnLivro, idReservante);
        listaReservas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                removerReservaButton.setDisable(false);
                reservaSelecionada = newSelection;
            }
            else{
                removerReservaButton.setDisable(true);
            }
        });
    }

    public static void setarTabelaReserva(TableColumn<Reserva, String> idReserva,
                                          TableColumn<Reserva, LocalDate> dataReserva,
                                          TableColumn<Reserva, LocalDate> dataInicioReserva,
                                          TableColumn<Reserva, LocalDate> dataFimReserva,
                                          TableColumn<Reserva, String> isbnLivro,
                                          TableColumn<Reserva, String> idReservante
                                            ) {
        idReserva.setCellValueFactory(new PropertyValueFactory<>("idReserva"));
        dataReserva.setCellValueFactory(new PropertyValueFactory<>("dataReserva"));
        dataInicioReserva.setCellValueFactory(new PropertyValueFactory<>("dataInicioReserva"));
        dataFimReserva.setCellValueFactory(new PropertyValueFactory<>("dataFimReserva"));
        isbnLivro.setCellValueFactory(new PropertyValueFactory<>("isbnLivro"));
        idReservante.setCellValueFactory(new PropertyValueFactory<>("idReservante"));
    }

    public static ObservableList<Reserva> reservasLeitor(Leitor leitor) throws naoEncontradoException {
        return FXCollections.observableArrayList(Sistema.findReservasLeitor(leitor.getId()));
    }

    @FXML
    public void setRemoverReservaButton(ActionEvent actionEvent) {
        try {
            leitor.removerReserva(reservaSelecionada.getIsbnLivro());
            TelaController.gerarAlertaOk("Confirmação", "Reserva removida com sucesso.");
            try {
                listaReservas.setItems(reservasLeitor(leitor));
            }
            catch (naoEncontradoException e){
                listaReservas.setItems(FXCollections.observableArrayList());
            }
        } catch (naoEncontradoException e) {
            TelaController.gerarAlertaErro("Erro", "Reserva ou livro inexistente.");
        } catch (foraDeEstoqueException e) {
            TelaController.gerarAlertaErro("Erro", "Leitor alcançou o número máximo de reservas.");
        }
    }

    @FXML
    public void setSairButton(ActionEvent actionEvent) {
        Stage stage = TelaController.retornarStage(actionEvent);
        stage.close();
    }
}

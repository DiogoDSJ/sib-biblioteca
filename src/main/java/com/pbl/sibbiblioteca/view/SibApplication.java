package com.pbl.sibbiblioteca.view;

import com.pbl.sibbiblioteca.exceptions.foraDeEstoqueException;
import com.pbl.sibbiblioteca.model.entities.Sistema;
import com.pbl.sibbiblioteca.utils.TelaController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class SibApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage program) throws IOException, foraDeEstoqueException {
        Sistema.atualizarReservas();
        Sistema.ativarReservasLivros();
        Sistema.updateMultas();
        TelaController.StageBuilder(program, TelaController.StageFXMLLoader("TelaDeInicio.fxml"));
        program.show();
    }
}

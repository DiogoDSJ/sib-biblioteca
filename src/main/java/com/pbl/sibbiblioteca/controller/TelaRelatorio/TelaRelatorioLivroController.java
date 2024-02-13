package com.pbl.sibbiblioteca.controller.TelaRelatorio;

import com.pbl.sibbiblioteca.model.entities.Sistema;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;

public class TelaRelatorioLivroController {

    @javafx.fxml.FXML
    private PieChart livrosPieChart;

    @FXML
    public void initialize(){
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Emprestados", Sistema.getQuantidadeLivrosEmprestados()),
                        new PieChart.Data("Disponíveis", Sistema.getEstoqueDeLivrosNoAcervo()),
                        new PieChart.Data("Atrasados", Sistema.getQuantidadeDeLivrosAtrasados()),
                        new PieChart.Data("Reservados", Sistema.getQuantidadeDeLivrosReservados()));


        pieChartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), ": " , data.pieValueProperty().intValue()
                        )
                )
        );

        livrosPieChart.getData().addAll(pieChartData);
    }


}

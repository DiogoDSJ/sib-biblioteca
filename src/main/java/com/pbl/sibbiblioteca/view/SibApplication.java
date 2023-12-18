package com.pbl.sibbiblioteca.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.io.IOException;

public class SibApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage telaLogin) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SibApplication.class.getResource("sib-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        telaLogin.setTitle("Fazer");
        telaLogin.setScene(scene);
        telaLogin.show();
    }
}

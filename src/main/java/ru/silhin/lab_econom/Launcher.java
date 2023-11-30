package ru.silhin.lab_econom;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Launcher extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Launcher.class.getResource("pages/menu.fxml"));
        Scene scene = fxmlLoader.load();
        stage.setTitle("Лабораторная работа");
        stage.setScene(scene);
        stage.show();
    }

}
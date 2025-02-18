package com.example.javafxproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginApplication extends Application {
    public static Stage mainStage;
    @Override
    public void start(Stage stage) throws IOException {
        mainStage=stage;
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("login.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 700, 530);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("Log in");
        stage.setScene(scene);
        stage.show();
    }
    public static Stage getMainStage()
    {
        return mainStage;
    }
    public static void main(String[] args) {
        launch();
    }
}
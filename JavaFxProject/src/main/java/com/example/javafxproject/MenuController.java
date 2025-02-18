package com.example.javafxproject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController {
    public void login()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("login.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 700, 530);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LoginApplication.getMainStage().setTitle("Log in!");
        LoginApplication.getMainStage().setScene(scene);
        LoginApplication.getMainStage().show();
    }
}

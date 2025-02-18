package com.example.javafxproject;

import com.example.javafxproject.production.Files.FileUtils;
import com.example.javafxproject.production.main.Main;
import com.example.javafxproject.production.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    @FXML
    private TextField usernameTextArea;
    @FXML
    private TextField passwordTextArea;

    public void login()
    {
        List<User> userList= FileUtils.getUsersFromFile();
        String usernameString=usernameTextArea.getText();
        String passwordString=passwordTextArea.getText();
        boolean equals=false;
        for (User user:userList)
        {
            if (user.getUsername().equals(usernameString) && user.getPassword().equals(passwordString))
            {
                equals=true;
            }
        }
        if (usernameTextArea.getText().isEmpty() || passwordTextArea.getText().isEmpty()) {
            String errorMessage = "Sljedeća polja su prazna:";
            if (usernameTextArea.getText().isEmpty())
            {
                errorMessage += "\n- Korisničko ime";
            }
            if (passwordTextArea.getText().isEmpty())
            {
                errorMessage += "\n- Lozinka";
            }
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška u unosu");
            alert.setHeaderText("Molimo unesite sve potrebne informacije");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            logger.error("Korisnik nije unio sve potrebne informacije za prijavu.");
        }
        else if (!equals)
        {
            usernameTextArea.clear();
            passwordTextArea.clear();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška u unosu");
            alert.setHeaderText("Molimo unesite sve potrebne informacije");
            alert.setContentText("Niste upisali dobre podatke za prijavu.");
            alert.showAndWait();
            logger.error("Korisnik nije unio ispravne informacije za prijavu.");
        }
        else
        {
            FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("menu.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load(), 700, 530);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            LoginApplication.getMainStage().setTitle("Welcome "+usernameString+"!");
            LoginApplication.getMainStage().setScene(scene);
            LoginApplication.getMainStage().show();
            logger.info("Uspješna prijava.");
        }
    }
    public void signUp()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("signup.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 700, 530);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LoginApplication.getMainStage().setTitle("Sign up now!");
        LoginApplication.getMainStage().setScene(scene);
        LoginApplication.getMainStage().show();
    }
}
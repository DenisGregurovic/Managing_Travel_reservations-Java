package com.example.javafxproject;

import com.example.javafxproject.production.Files.FileUtils;
import com.example.javafxproject.production.main.Main;
import com.example.javafxproject.production.model.Admin;
import com.example.javafxproject.production.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class SignUpController {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    @FXML
    private TextField usernameTextArea;
    @FXML
    private Label typeLabel;
    @FXML
    private TextField passwordTextArea;
    @FXML
    private RadioButton adminRadioButton;
    @FXML
    private RadioButton userRadioButton;
    public void signUp() {
        List<User> userList = FileUtils.getUsersFromFile();
        String usernameString = usernameTextArea.getText();
        String passwordString = passwordTextArea.getText();
        Long id = FileUtils.getNextUserID();
        boolean equals = false;
        boolean create=false;
        String type = "wrong";
        if (adminRadioButton.isSelected()) type = "Admin";
        if (userRadioButton.isSelected()) type = "User";
        if (usernameTextArea.getText().isEmpty() || passwordTextArea.getText().isEmpty() || type.equals("wrong")) {
            String errorMessage = "Sljedeća polja su prazna:";
            if (usernameTextArea.getText().isEmpty()) {
                errorMessage += "\n- Korisničko ime";
            }
            if (passwordTextArea.getText().isEmpty()) {
                errorMessage += "\n- Lozinka";
            }
            if (type.equals("wrong")) {
                errorMessage += "\n- Vrsta računa";
            }
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška u unosu");
            alert.setHeaderText("Molimo unesite sve potrebne informacije");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            type = "Wrong";
            logger.error("Korisnik nije unio sve potrebne informacije za prijavu.");
        }
        if (type.equals("Admin"))
        {
                for (User user : userList)
                {
                    if (user instanceof Admin)
                    {
                        if (user.getUsername().equals(usernameString))
                        {
                            typeLabel.setText(usernameString);
                            equals = true;
                            create=false;
                        }
                        else
                        {
                            typeLabel.setText("Admin");
                            equals=false;
                            create=true;
                        }

                    }
                }
            }
        if (type.equals("User"))
        {
                for (User user : userList)
                {
                    if (user instanceof User user1)
                    {
                        if (user1.getUsername().equals(usernameString))
                        {
                            typeLabel.setText(user1.getUsername());
                            equals = true;
                            create=false;
                        }
                        else
                        {
                            typeLabel.setText("User");
                            equals=false;
                            create=true;
                        }
                    }
                }
            }
        if (equals)
            {
                type="wrong";
                create=false;
                usernameTextArea.clear();
                passwordTextArea.clear();
                adminRadioButton.setSelected(false);
                adminRadioButton.setSelected(false);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Greška u unosu");
                alert.setHeaderText("Postojeći zapis");
                alert.setContentText("Već postoji nalog s tim korisničkim imenom.");
                alert.showAndWait();
            }

        }
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
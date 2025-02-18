package com.example.visual;

import com.example.visual.production.Entiteti.Korisnik;
import com.example.visual.production.Entiteti.Zaposlenik;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Pokreni extends Application {
    public static Stage mainStage;
    public static Korisnik korisnik;
    public static Zaposlenik zaposlenik;
    private static final Logger logger = LoggerFactory.getLogger(Pokreni.class);

    @Override
    public void start(Stage stage) throws IOException
    {
        mainStage=stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Pokreni.class.getResource("Login.fxml"));
        Scene scene = null;
        try
        {
            scene = new Scene(fxmlLoader.load(), 700, 530);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        stage.setTitle("Prijavite se!");
        stage.setScene(scene);
        stage.show();
    }
    public static Stage getMainStage()
    {
        return mainStage;
    }

    public static void setMainStage(Stage mainStage)
    {
        Pokreni.mainStage = mainStage;
    }

    public static Korisnik getKorisnik()
    {
        return korisnik;
    }

    public static void setKorisnik(Korisnik korisnik)
    {
        Pokreni.korisnik = korisnik;
    }

    public static Zaposlenik getZaposlenik()
    {
        return zaposlenik;
    }

    public static void setZaposlenik(Zaposlenik zaposlenik)
    {
        Pokreni.zaposlenik = zaposlenik;
    }

    public static void main(String[] args) {
        launch();
    }
}
package com.example.visual;

import com.example.visual.production.Entiteti.Korisnik;
import com.example.visual.production.Entiteti.Serijalizacija;
import com.example.visual.production.Entiteti.Zaposlenik;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.List;

public class KorisnikIzbornikController {
    @FXML
    private Label korisnickoImeLabel;
    public void initialize()
    {
        Korisnik korisnik= new Serijalizacija<Korisnik>().ucitajKorisnickiRacun("Datoteke/AktivniKorisnik.dat");
        korisnickoImeLabel.setText("Dobrodošao "+korisnik.getKorisnikoIme());
    }
    public void KorisniciPocetni()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(Pokreni.class.getResource("KorisniciPocetni.fxml"));
        Scene scene = null;
        try
        {
            scene = new Scene(fxmlLoader.load(), 600, 700);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        Pokreni.getMainStage().setTitle("Početni izbornik!");
        Pokreni.getMainStage().setScene(scene);
        Pokreni.getMainStage().show();
    }
    public void KorisniciDogadaji()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(Pokreni.class.getResource("KorisniciDogadaji.fxml"));
        Scene scene = null;
        try
        {
            scene = new Scene(fxmlLoader.load(), 1200, 700);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        Pokreni.getMainStage().setTitle("Kupite ulaznice!");
        Pokreni.getMainStage().setScene(scene);
        Pokreni.getMainStage().show();
    }
    public void KorisniciSmjestaji()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(Pokreni.class.getResource("KorisniciSmjestaji.fxml"));
        Scene scene = null;
        try
        {
            scene = new Scene(fxmlLoader.load(), 1200, 700);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        Pokreni.getMainStage().setTitle("Rezervirajte smještaj!");
        Pokreni.getMainStage().setScene(scene);
        Pokreni.getMainStage().show();
    }
    public void Logout()
    {
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
        Pokreni.getMainStage().setTitle("Prijavite se!");
        Pokreni.getMainStage().setScene(scene);
        Pokreni.getMainStage().show();
    }
}

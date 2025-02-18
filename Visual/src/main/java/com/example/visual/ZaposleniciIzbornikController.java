package com.example.visual;

import com.example.visual.production.Entiteti.Metode;
import com.example.visual.production.Entiteti.Serijalizacija;
import com.example.visual.production.Entiteti.Zaposlenik;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.List;

public class ZaposleniciIzbornikController
{
    @FXML
    private Label korisnickoImeLabel;
    public void initialize()
    {
        Zaposlenik zaposlenik= new Serijalizacija<Zaposlenik>().ucitajZaposlenickiRacun("Datoteke/AktivniKorisnik.dat");
        korisnickoImeLabel.setText("Dobrodošao "+zaposlenik.getKorisnikoIme());
    }
    public void ZaposleniciPocetni()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(Pokreni.class.getResource("ZaposleniciPocetni.fxml"));
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
    public void ZaposleniciDogadaji()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(Pokreni.class.getResource("ZaposleniciDogadaji.fxml"));
        Scene scene = null;
        try
        {
            scene = new Scene(fxmlLoader.load(), 1200, 700);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        Pokreni.getMainStage().setTitle("Događaji!");
        Pokreni.getMainStage().setScene(scene);
        Pokreni.getMainStage().show();
    }
    public void ZaposleniciSmjestaji()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(Pokreni.class.getResource("ZaposleniciSmjestaji.fxml"));
        Scene scene = null;
        try
        {
            scene = new Scene(fxmlLoader.load(), 1200, 700);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        Pokreni.getMainStage().setTitle("Smještaji!");
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
    public void ZaposleniciZaposlenici()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(Pokreni.class.getResource("ZaposleniciZaposlenici.fxml"));
        Scene scene = null;
        try
        {
            scene = new Scene(fxmlLoader.load(), 1200, 700);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        Pokreni.getMainStage().setTitle("Zaposlenici!");
        Pokreni.getMainStage().setScene(scene);
        Pokreni.getMainStage().show();
    }
    public void ZaposleniciKorisnici()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(Pokreni.class.getResource("ZaposleniciKorisnici.fxml"));
        Scene scene = null;
        try
        {
            scene = new Scene(fxmlLoader.load(), 1200, 700);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        Pokreni.getMainStage().setTitle("Korisnici!");
        Pokreni.getMainStage().setScene(scene);
        Pokreni.getMainStage().show();
    }
    public void ZaposleniciPromjene()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(Pokreni.class.getResource("ZaposleniciPromjene.fxml"));
        Scene scene = null;
        try
        {
            scene = new Scene(fxmlLoader.load(), 1200, 700);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        Pokreni.getMainStage().setTitle("Promjene!");
        Pokreni.getMainStage().setScene(scene);
        Pokreni.getMainStage().show();
    }
}

package com.example.visual;

import com.example.visual.production.Datoteke.Datoteka;
import com.example.visual.production.Entiteti.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class LoginController
{
    private static final Logger logger = LoggerFactory.getLogger(Pokreni.class);
    @FXML
    private TextField korisnickoImeTextArea;
    @FXML
    private PasswordField lozinkaPasswordField;

    public void login() {
        List<Prijava> listaPrijava= Datoteka.dohvatiLozinkeIzDatoteke();
        String korisnickoImeString=korisnickoImeTextArea.getText();
        String lozinkaString=lozinkaPasswordField.getText();
        boolean podudaranje=false;
        Prijava pokusajPrijave=new Prijava(1L,"korisnik",1L,"dsa",RazinaPrava.ZAPOSLENI);
        for (Prijava racun:listaPrijava)
        {
            if (racun.getKorisnickoIme().equals(korisnickoImeString) && racun.getLozinka().equals(lozinkaString))
            {
                podudaranje = true;
                pokusajPrijave=racun;
                break;
            }
        }
        if (korisnickoImeTextArea.getText().isEmpty() || lozinkaPasswordField.getText().isEmpty())
        {
            String errorMessage = "Sljedeća polja su prazna:";
            if (korisnickoImeTextArea.getText().isEmpty())
            {
                errorMessage += "\n- Korisničko ime";
            }
            if (lozinkaPasswordField.getText().isEmpty())
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
        else if (!podudaranje)
        {
            korisnickoImeTextArea.clear();
            lozinkaPasswordField.clear();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška u unosu");
            alert.setHeaderText("Molimo unesite sve potrebne informacije");
            alert.setContentText("Niste upisali dobre podatke za prijavu.");
            alert.showAndWait();
            logger.error("Korisnik nije unio ispravne informacije za prijavu.");
        }
        else
        {
            if (pokusajPrijave.getRazinaPrava().equals(RazinaPrava.KLIJENT))
            {
                    Korisnik korisnik= Metode.pronadiKorisnika(korisnickoImeString,lozinkaString);
                    Serijalizacija<Korisnik> korisnikSerijalizacija=new Serijalizacija<>();
                    Serijalizacija.ocistitDatoteku("Datoteke/AktivniKorisnik.dat");
                    korisnikSerijalizacija.serializirajRacun(korisnik,"Datoteke/AktivniKorisnik.dat");
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
            else
            {
                Zaposlenik zaposlenik= Metode.pronadiZaposlenika(korisnickoImeString,lozinkaString);
                Serijalizacija<Zaposlenik> zaposlenikSerijalizacija=new Serijalizacija<>();
                Serijalizacija.ocistitDatoteku("Datoteke/AktivniKorisnik.dat");
                zaposlenikSerijalizacija.serializirajRacun(zaposlenik,"Datoteke/AktivniKorisnik.dat");
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
        }

    }
}
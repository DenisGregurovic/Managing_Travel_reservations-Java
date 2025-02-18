package com.example.visual.production.Niti;

import com.example.visual.production.BazaPodataka.Baza;
import com.example.visual.production.Entiteti.Dogadaj;
import com.example.visual.production.Entiteti.Korisnik;
import javafx.application.Platform;
import javafx.collections.ObservableList;

public class UcitajKorisnikeThread implements Runnable
{
    private final ObservableList<Korisnik> podaciIzBaze;
    public UcitajKorisnikeThread(ObservableList<Korisnik> podaciIzBaze)
    {
        this.podaciIzBaze = podaciIzBaze;
    }

    @Override
    public void run()
    {
        ObservableList<Korisnik> noviPodaci = Baza.dohvatiKorisnike();
        Platform.runLater(() ->
        {
            podaciIzBaze.clear();
            podaciIzBaze.addAll(noviPodaci);
        });
    }
}

package com.example.visual.production.Niti;

import com.example.visual.production.BazaPodataka.Baza;
import com.example.visual.production.Entiteti.Dogadaj;
import com.example.visual.production.Entiteti.Smjestaj;
import javafx.application.Platform;
import javafx.collections.ObservableList;

public class UcitajDogadajeThread implements Runnable
{
    private final ObservableList<Dogadaj> podaciIzBaze;
    public UcitajDogadajeThread(ObservableList<Dogadaj> podaciIzBaze)
    {
        this.podaciIzBaze = podaciIzBaze;
    }

    @Override
    public void run()
    {
        ObservableList<Dogadaj> noviPodaci = Baza.dohvatiDogadaje();
        Platform.runLater(() ->
        {
            podaciIzBaze.clear();
            podaciIzBaze.addAll(noviPodaci);
        });
    }

}

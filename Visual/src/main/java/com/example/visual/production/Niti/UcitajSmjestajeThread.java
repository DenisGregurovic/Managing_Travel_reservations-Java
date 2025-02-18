package com.example.visual.production.Niti;

import com.example.visual.production.BazaPodataka.Baza;
import com.example.visual.production.Entiteti.Smjestaj;
import javafx.application.Platform;
import javafx.collections.ObservableList;

public class UcitajSmjestajeThread implements Runnable
{
    private final ObservableList<Smjestaj> podaciIzBaze;
    public UcitajSmjestajeThread(ObservableList<Smjestaj> podaciIzBaze)
    {
        this.podaciIzBaze = podaciIzBaze;
    }

    @Override
    public void run()
    {
        ObservableList<Smjestaj> noviPodaci = Baza.dohvatiSmjestaje();
        Platform.runLater(() ->
        {
            podaciIzBaze.clear();
            podaciIzBaze.addAll(noviPodaci);
        });
    }
}

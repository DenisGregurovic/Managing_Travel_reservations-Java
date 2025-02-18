package com.example.visual.production.Niti;

import com.example.visual.production.BazaPodataka.Baza;
import com.example.visual.production.Entiteti.Korisnik;
import com.example.visual.production.Entiteti.Zaposlenik;
import javafx.application.Platform;
import javafx.collections.ObservableList;

public class UcitajZaposlenikeThread implements Runnable
{
    private final ObservableList<Zaposlenik> podaciIzBaze;
    public UcitajZaposlenikeThread(ObservableList<Zaposlenik> podaciIzBaze)
    {
        this.podaciIzBaze = podaciIzBaze;
    }

    @Override
    public void run()
    {
        ObservableList<Zaposlenik> noviPodaci = Baza.dohvatiZaposlenike();
        Platform.runLater(() ->
        {
            podaciIzBaze.clear();
            podaciIzBaze.addAll(noviPodaci);
        });
    }
}

package com.example.visual.production.Niti;

import com.example.visual.production.BazaPodataka.Baza;
import com.example.visual.production.Entiteti.Korisnik;

public class SpremiKorisnikaUDatotekuThread extends DatotekeThreads implements Runnable
{
    private Korisnik korisnik;
    public SpremiKorisnikaUDatotekuThread(Korisnik korisnik)
    {
        this.korisnik = korisnik;
    }
    @Override
    public void run()
    {
        super.spremiKorisnika(this.korisnik);
    }
}

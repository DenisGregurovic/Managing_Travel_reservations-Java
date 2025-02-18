package com.example.visual.production.Niti;

import com.example.visual.production.Entiteti.Dogadaj;
import com.example.visual.production.Entiteti.Korisnik;

public class SpremiKorisnikeUBazuThread extends BazaThreads implements Runnable
{
    private Korisnik korisnik;
    public SpremiKorisnikeUBazuThread(Korisnik korisnik)
    {
        this.korisnik = korisnik;
    }
    @Override
    public void run()
    {
        super.spremiKorisnika(this.korisnik);
    }
}

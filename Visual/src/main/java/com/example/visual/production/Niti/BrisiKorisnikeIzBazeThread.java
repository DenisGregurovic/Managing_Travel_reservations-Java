package com.example.visual.production.Niti;

import com.example.visual.production.Entiteti.Korisnik;

public class BrisiKorisnikeIzBazeThread extends BazaThreads implements Runnable
{
    private Korisnik korisnik;
    public BrisiKorisnikeIzBazeThread(Korisnik korisnik)
    {
        this.korisnik = korisnik;
    }
    @Override
    public void run()
    {
        super.obrisiKorisnika(this.korisnik);
    }
}

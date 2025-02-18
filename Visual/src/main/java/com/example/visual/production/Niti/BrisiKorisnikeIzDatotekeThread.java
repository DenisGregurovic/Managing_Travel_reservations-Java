package com.example.visual.production.Niti;

import com.example.visual.production.Entiteti.Korisnik;

public class BrisiKorisnikeIzDatotekeThread extends DatotekeThreads implements Runnable
{
    private Korisnik korisnik;
    public BrisiKorisnikeIzDatotekeThread(Korisnik korisnik)
    {
        this.korisnik = korisnik;
    }
    @Override
    public void run()
    {
        super.brisiKorisnika(this.korisnik);
    }
}

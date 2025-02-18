package com.example.visual.production.Niti;

import com.example.visual.production.Entiteti.Korisnik;

public class AzurirajKorisnikeUBaziThread  extends BazaThreads implements Runnable
{
    private Korisnik korisnik;
    public AzurirajKorisnikeUBaziThread(Korisnik korisnik)
    {
        this.korisnik = korisnik;
    }
    @Override
    public void run()
    {
        super.azurirajKorisnika(this.korisnik);
    }
}

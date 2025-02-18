package com.example.visual.production.Niti;

import com.example.visual.production.Entiteti.Korisnik;
import com.example.visual.production.Entiteti.Zaposlenik;

public class AzurirajKorisnikeUDatoteciThread extends DatotekeThreads implements Runnable
{
    private Korisnik korisnik;
    public AzurirajKorisnikeUDatoteciThread(Korisnik korisnik)
    {
        this.korisnik = korisnik;
    }
    @Override
    public void run()
    {
        super.azurirajKorisnika(this.korisnik);
    }
}

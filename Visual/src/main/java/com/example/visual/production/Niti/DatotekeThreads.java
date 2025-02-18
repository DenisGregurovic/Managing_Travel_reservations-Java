package com.example.visual.production.Niti;

import com.example.visual.Pokreni;
import com.example.visual.production.BazaPodataka.Baza;
import com.example.visual.production.Datoteke.Datoteka;
import com.example.visual.production.Entiteti.Korisnik;
import com.example.visual.production.Entiteti.Zaposlenik;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DatotekeThreads
{
    private static final Logger logger = LoggerFactory.getLogger(Pokreni.class);
    public static Boolean operacijaJeUTijeku = false;
    public synchronized void spremiZaposlenika(Zaposlenik zaposlenik)
    {
        while (operacijaJeUTijeku)
        {
            try
            {
                this.wait();
            }
            catch
            (InterruptedException iznimka)
            {
                logger.error("Iznimka: "+iznimka);
            }
        }
        operacijaJeUTijeku=true;
        Datoteka.dodajZaposlenika(zaposlenik);
        operacijaJeUTijeku=false;
        this.notifyAll();
    }
    public synchronized void spremiKorisnika(Korisnik korisnik)
    {
        while (operacijaJeUTijeku)
        {
            try
            {
                this.wait();
            }
            catch
            (InterruptedException iznimka)
            {
                logger.error("Iznimka: "+iznimka);
            }
        }
        operacijaJeUTijeku=true;
        Datoteka.dodajKorisnika(korisnik);
        operacijaJeUTijeku=false;
        this.notifyAll();
    }
    public synchronized void azurirajKorisnika(Korisnik korisnik)
    {
        while (operacijaJeUTijeku)
        {
            try
            {
                this.wait();
            }
            catch
            (InterruptedException iznimka)
            {
                logger.error("Iznimka: "+iznimka);
            }
        }
        operacijaJeUTijeku=true;
        Datoteka.azurirajKorisnika(korisnik);
        operacijaJeUTijeku=false;
        this.notifyAll();
    }
    public synchronized void azurirajZaposlenika(Zaposlenik zaposlenik)
    {
        while (operacijaJeUTijeku)
        {
            try
            {
                this.wait();
            }
            catch
            (InterruptedException iznimka)
            {
                logger.error("Iznimka: "+iznimka);
            }
        }
        operacijaJeUTijeku=true;
        Datoteka.azurirajZaposlenika(zaposlenik);
        operacijaJeUTijeku=false;
        this.notifyAll();
    }
    public synchronized void brisiKorisnika(Korisnik korisnik)
    {
        while (operacijaJeUTijeku)
        {
            try
            {
                this.wait();
            }
            catch
            (InterruptedException iznimka)
            {
                logger.error("Iznimka: "+iznimka);
            }
        }
        operacijaJeUTijeku=true;
        Datoteka.obrisiKorisnika(korisnik);
        operacijaJeUTijeku=false;
        this.notifyAll();
    }
    public synchronized void brisiZaposlenika(Zaposlenik zaposlenik)
    {
        while (operacijaJeUTijeku)
        {
            try
            {
                this.wait();
            }
            catch
            (InterruptedException iznimka)
            {
                logger.error("Iznimka: "+iznimka);
            }
        }
        operacijaJeUTijeku=true;
        Datoteka.obrisiZaposlenika(zaposlenik);
        operacijaJeUTijeku=false;
        this.notifyAll();
    }
}

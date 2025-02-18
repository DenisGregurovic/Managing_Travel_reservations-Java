package com.example.visual.production.Niti;

import com.example.visual.Pokreni;
import com.example.visual.production.BazaPodataka.Baza;
import com.example.visual.production.Entiteti.Dogadaj;
import com.example.visual.production.Entiteti.Korisnik;
import com.example.visual.production.Entiteti.Smjestaj;
import com.example.visual.production.Entiteti.Zaposlenik;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class BazaThreads
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
        Baza.spremiZaposlenika(zaposlenik);
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
        Baza.spremiKorisnika(korisnik);
        operacijaJeUTijeku=false;
        this.notifyAll();
    }
    public synchronized void spremiDogadaj(Dogadaj dogadaj)
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
        Baza.spremiDogadaj(dogadaj);
        operacijaJeUTijeku=false;
        this.notifyAll();
    }
    public synchronized void spremiSmjestaj(Smjestaj smjestaj)
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
        Baza.spremiSmjestaj(smjestaj);
        operacijaJeUTijeku=false;
        this.notifyAll();
    }
    public synchronized void azurirajSmjestaj(Smjestaj smjestaj)
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
        Baza.azurirajSmjestaj(smjestaj);
        operacijaJeUTijeku=false;
        this.notifyAll();
    }
    public synchronized void azurirajDogadaj(Dogadaj dogadaj)
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
        Baza.azurirajDogadaj(dogadaj);
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
        Baza.azurirajZaposlenika(zaposlenik);
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
        Baza.azurirajKorisnika(korisnik);
        operacijaJeUTijeku=false;
        this.notifyAll();
    }
    public synchronized void brisiDogadaj(Dogadaj dogadaj)
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
        Baza.obrisiDogadaj(dogadaj);
        operacijaJeUTijeku=false;
        this.notifyAll();
    }
    public synchronized void brisiSmjestaj(Smjestaj smjestaj)
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
        Baza.obrisiSmjestaj(smjestaj);
        operacijaJeUTijeku=false;
        this.notifyAll();
    }
    public synchronized void obrisiZaposlenika(Zaposlenik zaposlenik)
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
        Baza.obrisiZaposlenika(zaposlenik);
        operacijaJeUTijeku=false;
        this.notifyAll();
    }
    public synchronized void obrisiKorisnika(Korisnik korisnik)
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
        Baza.obrisiKorisnika(korisnik);
        operacijaJeUTijeku=false;
        this.notifyAll();
    }
}

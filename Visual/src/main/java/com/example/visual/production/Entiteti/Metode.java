package com.example.visual.production.Entiteti;

import com.example.visual.Pokreni;
import com.example.visual.production.BazaPodataka.Baza;
import com.example.visual.production.Datoteke.Datoteka;
import com.example.visual.production.Iznimke.Neoznacene.PrevelikaKolicinaException;
import com.example.visual.production.Iznimke.Oznacene.BrojException;
import com.example.visual.production.Iznimke.Oznacene.KorisnickoImePostojiException;
import com.example.visual.production.Iznimke.Neoznacene.BuduciDatumException;
import com.example.visual.production.Iznimke.Neoznacene.ProsliDatumException;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface Metode
{
    static final Logger logger = LoggerFactory.getLogger(Pokreni.class);
    public static String kriptirajSifru(String tekst)
    {
        StringBuilder kriptiraniTekst = new StringBuilder();
        for (int i = 0; i < tekst.length(); i++)
        {
            char originalniZnak = tekst.charAt(i);
            char kriptiraniZnak = (char) (originalniZnak + 10);
            kriptiraniTekst.append(kriptiraniZnak);
        }
        return kriptiraniTekst.toString();
    }

    public static String dekriptirajSifru(String kriptiraniTekst)
    {
        StringBuilder dekriptiraniTekst = new StringBuilder();
        for (int i = 0; i < kriptiraniTekst.length(); i++)
        {
            char kriptiraniZnak = kriptiraniTekst.charAt(i);
            char originalniZnak = (char) (kriptiraniZnak - 10);
            dekriptiraniTekst.append(originalniZnak);
        }
        return dekriptiraniTekst.toString();
    }

    static Vrsta vratiVrstu(String opis)
    {
        Vrsta varijabla;
        switch (opis)
        {
            case "Hotel":
                varijabla = Vrsta.HOTEL;
                break;
            case "Hostel":
                varijabla = Vrsta.HOSTEL;
                break;
            case "Apartman":
                varijabla = Vrsta.APARTMAN;
                break;
            case "Soba":
                varijabla = Vrsta.SOBA;
                break;
            default:
                varijabla = null;
        }

        return varijabla;
    }

    static Uloga vratiUlogu(String opis)
    {
        Uloga varijabla;
        switch (opis)
        {
            case "Nabava":
                varijabla = Uloga.NABAVA;
                break;
            case "Ljudski resursi":
                varijabla = Uloga.LJUDSKI_RESURSI;
                break;
            case "Administracija":
                varijabla = Uloga.ADMINISTRACIJA;
                break;
            case "Prodaja":
                varijabla = Uloga.PRODAJA;
                break;
            case "Voditelj":
                varijabla = Uloga.VODITELJ;
                break;
            default:
                varijabla = null;
        }
        return varijabla;
    }

    static List<String> ispisiUloge()
    {
        List<String> lista = new ArrayList<>();
        for (Uloga uloga : Uloga.values())
        {
            lista.add(uloga.getOpis());
        }
        return lista;
    }

    static List<String> ispisiVrsteSmjestaja()
    {
        List<String> lista = new ArrayList<>();
        for (Vrsta vrsta : Vrsta.values())
        {
            lista.add(vrsta.getOpis());
        }
        return lista;
    }

    static String vratiLozinkuZaposlenika(String korisnickoIme)
    {
        List<Prijava> listaPrijava = Datoteka.dohvatiLozinkeIzDatoteke();
        String lozinka = "";
        for (Prijava prijava : listaPrijava)
        {
            if (prijava.getKorisnickoIme().equals(korisnickoIme) && prijava.razinaPrava.equals(RazinaPrava.ZAPOSLENI))
                lozinka = prijava.getLozinka();
        }
        return lozinka;
    }

    static String vratiLozinkuKlijenta(String korisnickoIme)
    {
        List<Prijava> listaPrijava = Datoteka.dohvatiLozinkeIzDatoteke();
        String lozinka = "";
        for (Prijava prijava : listaPrijava)
        {
            if (prijava.getKorisnickoIme().equals(korisnickoIme) && prijava.razinaPrava.equals(RazinaPrava.KLIJENT))
                lozinka = prijava.getLozinka();
        }
        return lozinka;
    }

     static void ProvjeriBroj(BigDecimal broj) throws BrojException
    {
        int rezultatUsporedbe = broj.compareTo(BigDecimal.ZERO);
        if (rezultatUsporedbe < 0)
        {
            String poruka="Ne možete upisati broj koji je negativan!";
            logger.info(poruka);
            throw new BrojException(poruka);
        }
    }
    static Korisnik ispisiKorisnika(Korisnik korisnik)
    {
        ObservableList<Korisnik> korisnici= Baza.dohvatiKorisnike();
        Korisnik povratnaInfo=null;
        for (Korisnik racun:korisnici)
        {
            if (racun.getIme().equals(korisnik.getIme()) && racun.getPrezime().equals(korisnik.getPrezime()) && racun.getAdresa().equals(korisnik.getAdresa()) && racun.getKorisnikoIme().equals(korisnik.getKorisnikoIme())&& racun.getAdresa().equals(korisnik.getAdresa()))
            {
                povratnaInfo=racun;
                povratnaInfo.setId(racun.getId());
                povratnaInfo.setKorisnikoIme(korisnik.getKorisnikoIme());
                povratnaInfo.setLozinka(korisnik.getLozinka());
            }
        }
        return povratnaInfo;
    }
    static Zaposlenik ispisiZaposlenika(Zaposlenik zaposlenik)
    {
        ObservableList<Zaposlenik> zaposlenici= Baza.dohvatiZaposlenike();
        Zaposlenik povratnaInfo=null;
        for (Zaposlenik racun:zaposlenici)
        {
            if (racun.getIme().equals(zaposlenik.getIme()) && racun.getPrezime().equals(zaposlenik.getPrezime()) && racun.getKorisnikoIme().equals(zaposlenik.getKorisnikoIme()))
            {
                povratnaInfo=racun;
                povratnaInfo.setId(racun.getId());
                povratnaInfo.setKorisnikoIme(zaposlenik.getKorisnikoIme());
                povratnaInfo.setLozinka(zaposlenik.getLozinka());
            }
        }
        return povratnaInfo;
    }
    static Zaposlenik pronadiZaposlenika(String korisnickoIme,String lozinka)
    {
        ObservableList<Zaposlenik> zaposleniciIzBaze=Baza.dohvatiZaposlenike();
        Zaposlenik zaposlenik=zaposleniciIzBaze.get(0);
        List<Prijava> prijave=Datoteka.dohvatiLozinkeIzDatoteke();
        for (Prijava prijava:prijave)
        {
            if (prijava.getKorisnickoIme().equals(korisnickoIme) && prijava.getLozinka().equals(lozinka) && prijava.getRazinaPrava().equals(RazinaPrava.ZAPOSLENI)) zaposlenik.setId(prijava.getIdOsobe());
        }
        for (Zaposlenik osoba:zaposleniciIzBaze)
        {
            if (osoba.getId().equals(zaposlenik.getId()))
            {
                zaposlenik=osoba;
            }
        }
        return zaposlenik;
    }
    static Korisnik pronadiKorisnika(String korisnickoIme,String lozinka)
    {
        ObservableList<Korisnik> korisniciIzBaze=Baza.dohvatiKorisnike();
        Korisnik korisnik=korisniciIzBaze.get(0);
        List<Prijava> prijave=Datoteka.dohvatiLozinkeIzDatoteke();
        for (Prijava prijava:prijave)
        {
            if (prijava.getKorisnickoIme().equals(korisnickoIme) && prijava.getLozinka().equals(lozinka) && prijava.getRazinaPrava().equals(RazinaPrava.KLIJENT)) korisnik.setId(prijava.getIdOsobe());
        }
        for (Korisnik osoba:korisniciIzBaze)
        {
            if (osoba.getId().equals(korisnik.getId()))
            {
                korisnik=osoba;
            }
        }
        return korisnik;
    }
    static Long zadnjiIDKorisnika()
    {
        ObservableList<Korisnik> korisnici=Baza.dohvatiKorisnike();
        return korisnici.getLast().getId();
    }
    static Long zadnjiIDZaposlenika()
    {
        ObservableList<Zaposlenik> zaposlenici=Baza.dohvatiZaposlenike();
        return zaposlenici.getLast().getId();
    }
    static void provjeriKolicinu(Integer broj,Dogadaj dogadaj) throws PrevelikaKolicinaException
    {
        if (dogadaj.getKolicina()<broj)
        {
            String poruka="Nemamo toliko ulaznica na zalihi";
            logger.info(poruka);
            throw new PrevelikaKolicinaException(poruka);
        }
    }
    static void ProvjeriKorisnickoImeZaposlenika(Zaposlenik zaposlenik) throws KorisnickoImePostojiException
    {
        List<Prijava> lista= Datoteka.dohvatiLozinkeIzDatoteke();
        for (Prijava racun:lista)
        {
            if (racun.getKorisnickoIme().equals(zaposlenik.getKorisnikoIme()) && !racun.getIdOsobe().equals(zaposlenik.getId()))
            {
                String poruka="Već postoji račun s ovim korisničkim imenom";
                logger.info(poruka);
                throw new KorisnickoImePostojiException(poruka);
            }
        }
    }
    static void ProvjeriKorisnickoImeKorisnika(Korisnik korisnik) throws KorisnickoImePostojiException
    {
        List<Prijava> lista= Datoteka.dohvatiLozinkeIzDatoteke();
        for (Prijava racun:lista)
        {
            if (racun.getKorisnickoIme().equals(korisnik.getKorisnikoIme()) && !racun.getIdOsobe().equals(korisnik.getId()))
            {
                String poruka="Već postoji račun s ovim korisničkim imenom";
                logger.info(poruka);
                throw new KorisnickoImePostojiException(poruka);
            }
        }
    }
     static void provjeriDatumDogadaja(LocalDate datumRodenja) throws ProsliDatumException
    {
        LocalDate trenutniDatum = LocalDate.now();
        if (datumRodenja.isBefore(trenutniDatum))
        {
            String poruka="Ne možete upisati datum za događaj u prošlosti!";
            logger.info(poruka);
            throw new ProsliDatumException(poruka);
        }
    }
     static void provjeriDatumRodenja(LocalDate datumRodenja) throws BuduciDatumException
    {
        LocalDate trenutniDatum = LocalDate.now();
        if (datumRodenja.isAfter(trenutniDatum))
        {
            String poruka="Ne možete upisati datum rođenja za osobu koja se još nije rodila!";
            logger.info(poruka);
            throw new BuduciDatumException(poruka);
        }
    }
}

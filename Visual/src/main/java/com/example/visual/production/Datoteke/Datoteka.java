package com.example.visual.production.Datoteke;
import com.example.visual.Pokreni;
import com.example.visual.production.Entiteti.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

public class Datoteka
{
    private static final Logger logger = LoggerFactory.getLogger(Pokreni.class);
    private static final String NAZIV_DATOTEKE= "txtfiles/logins.txt";
    public static Long dohvatiIduciID()
    {
        List<Prijava> listZaPrijavu=dohvatiLozinkeIzDatoteke();
        Long posljednjiID=listZaPrijavu.stream().map(u ->u.getId()).max((u1,u2) ->u1.compareTo(u2)).get();
        return posljednjiID+1;
    }
    public static List<Prijava> dohvatiLozinkeIzDatoteke()
    {
        List<Prijava> listaOsoba=new ArrayList<>();
        File datoteka=new File(NAZIV_DATOTEKE);
        try (BufferedReader reader=new BufferedReader(new FileReader(datoteka)))
        {
            Optional<String> redak=Optional.empty();

            while ((redak=Optional.ofNullable(reader.readLine())).isPresent())
            {
                Long osobaID=Long.parseLong(redak.get());
                String osobaKorisnickoIme= reader.readLine();
                Long racunID=Long.parseLong(reader.readLine());
                String osobaKriptiranaLozinka= reader.readLine();
                String osobaLozinka=Metode.dekriptirajSifru(osobaKriptiranaLozinka);
                Integer osobaTip=Integer.parseInt(reader.readLine());
                if (osobaTip.equals(1))
                {
                    listaOsoba.add(new Prijava(osobaID,osobaKorisnickoIme,racunID,osobaLozinka, RazinaPrava.ZAPOSLENI));
                }
                else  listaOsoba.add(new Prijava(osobaID,osobaKorisnickoIme,racunID,osobaLozinka, RazinaPrava.KLIJENT));
            }
        }
        catch (IOException exception)
        {
            System.out.println("Dogodila se pogreška kod čitanja datoteke!");
            logger.error("Dogodila se pogreška kod čitanja datoteke! " + exception);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        return listaOsoba;
    }
    public static void dodajZaposlenika(Zaposlenik zaposlenik)
    {
        List<Prijava> lista=dohvatiLozinkeIzDatoteke();
        lista.add(new Prijava(dohvatiIduciID(),zaposlenik.getKorisnikoIme(), zaposlenik.getId(), zaposlenik.getLozinka(),RazinaPrava.ZAPOSLENI));
        spremiLozinkeUDatoteku(lista);
    }
    public static void obrisiZaposlenika(Zaposlenik zaposlenik)
    {
        List<Prijava> lista = Datoteka.dohvatiLozinkeIzDatoteke();
        Prijava izbrisiva=null;
        for (Prijava racun : lista)
        {
            if (racun.getKorisnickoIme().equals(zaposlenik.getKorisnikoIme()) && racun.getIdOsobe().equals(zaposlenik.getId())) izbrisiva=racun;
        }
        lista.remove(izbrisiva);
         spremiLozinkeUDatoteku(lista);
    }
    public static void azurirajZaposlenika(Zaposlenik zaposlenik) {
        List<Prijava> lista = dohvatiLozinkeIzDatoteke();
        for (Prijava racun : lista)
        {
            if (racun.getIdOsobe().equals(zaposlenik.getId()) && racun.getRazinaPrava().equals(RazinaPrava.ZAPOSLENI))
            {
                racun.setKorisnickoIme(zaposlenik.getKorisnikoIme());
                racun.setLozinka(zaposlenik.getLozinka());
            }
        }
        spremiLozinkeUDatoteku(lista);
    }
    public static void dodajKorisnika(Korisnik korisnik)
    {
        List<Prijava> lista=dohvatiLozinkeIzDatoteke();
        lista.add(new Prijava(dohvatiIduciID(),korisnik.getKorisnikoIme(), korisnik.getId(), korisnik.getLozinka(),RazinaPrava.KLIJENT));
        spremiLozinkeUDatoteku(lista);
    }
    public static void obrisiKorisnika(Korisnik korisnik)
    {
        List<Prijava> lista = Datoteka.dohvatiLozinkeIzDatoteke();
        Prijava izbrisiva=null;
        for (Prijava racun : lista)
        {
            if (racun.getKorisnickoIme().equals(korisnik.getKorisnikoIme()) && racun.getIdOsobe().equals(korisnik.getId()) && racun.getRazinaPrava().equals(RazinaPrava.KLIJENT)) izbrisiva=racun;
        }
        lista.remove(izbrisiva);
        Datoteka.spremiLozinkeUDatoteku(lista);
    }
    public static void azurirajKorisnika(Korisnik korisnik)
    {
        List<Prijava> lista = dohvatiLozinkeIzDatoteke();
        for (Prijava racun : lista)
        {
            if (racun.getIdOsobe().equals(korisnik.getId()) && racun.getRazinaPrava().equals(RazinaPrava.KLIJENT))
            {
                racun.setKorisnickoIme(korisnik.getKorisnikoIme());
                racun.setLozinka(korisnik.getLozinka());
            }
        }
        spremiLozinkeUDatoteku(lista);
    }

    public static void spremiLozinkeUDatoteku(List<Prijava> lista) {
        File categoryFile = new File("txtfiles/logins.txt");
        try {
            PrintWriter printWriter = new PrintWriter(categoryFile);

            try {
                Iterator var8 = lista.iterator();
                while(var8.hasNext()) {
                    Prijava prijava = (Prijava) var8.next();
                    printWriter.println(prijava.getId());
                    printWriter.println(prijava.getKorisnickoIme());
                    printWriter.println(prijava.getIdOsobe());
                    String lozinka=Metode.kriptirajSifru(prijava.getLozinka());
                    printWriter.println(lozinka);
                    if (prijava.getRazinaPrava().equals(RazinaPrava.ZAPOSLENI)) printWriter.println("1");
                    else printWriter.println("2");
                }
            } catch (Throwable var6)
            {
                try
                {
                    printWriter.close();
                }
                catch (Throwable var5)
                {
                    var6.addSuppressed(var5);
                }
                throw var6;
            }
            printWriter.close();
        }
        catch (IOException var7)
        {
            String message = "Dogodila se pogreška kod zapisivanja podataka o prijavama u datoteku";
            logger.error(message + String.valueOf(var7));
            System.out.println(message);
        }

    }
}

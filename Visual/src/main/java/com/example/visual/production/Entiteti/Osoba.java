package com.example.visual.production.Entiteti;

import java.time.LocalDate;

public abstract sealed class Osoba extends  Entitet permits Korisnik, Zaposlenik
{
    String ime;
    String prezime;
    String korisnikoIme;
    String lozinka;
    LocalDate datumRodjenja;

    public Osoba(Long id, String ime, String prezime, String korisnikoIme, String lozinka, LocalDate datumRodjenja)
    {
        super(id);
        this.ime = ime;
        this.prezime = prezime;
        this.korisnikoIme = korisnikoIme;
        this.lozinka = lozinka;
        this.datumRodjenja = datumRodjenja;
    }

    public String getIme()
    {
        return ime;
    }

    public void setIme(String ime)
    {
        this.ime = ime;
    }

    public String getPrezime()
    {
        return prezime;
    }

    public void setPrezime(String prezime)
    {
        this.prezime = prezime;
    }

    public String getKorisnikoIme()
    {
        return korisnikoIme;
    }

    public void setKorisnikoIme(String korisnikoIme)
    {
        this.korisnikoIme = korisnikoIme;
    }

    public String getLozinka()
    {
        return lozinka;
    }

    public void setLozinka(String lozinka)
    {
        this.lozinka = lozinka;
    }

    public LocalDate getDatumRodjenja()
    {
        return datumRodjenja;
    }

    public void setDatumRodjenja(LocalDate datumRodjenja)
    {
        this.datumRodjenja = datumRodjenja;
    }
}

package com.example.visual.production.Entiteti;

import java.time.LocalDate;

public class KorisnikBuilder {
    private Long id;
    private String ime;
    private String prezime;
    private String korisnikoIme;
    private String lozinka;
    private LocalDate datumRodjenja;
    private String adresa;

    public KorisnikBuilder setId(Long id)
    {
        this.id = id;
        return this;
    }

    public KorisnikBuilder setIme(String ime)
    {
        this.ime = ime;
        return this;
    }

    public KorisnikBuilder setPrezime(String prezime)
    {
        this.prezime = prezime;
        return this;
    }

    public KorisnikBuilder setKorisnikoIme(String korisnikoIme)
    {
        this.korisnikoIme = korisnikoIme;
        return this;
    }

    public KorisnikBuilder setLozinka(String lozinka)
    {
        this.lozinka = lozinka;
        return this;
    }

    public KorisnikBuilder setDatumRodjenja(LocalDate datumRodjenja)
    {
        this.datumRodjenja = datumRodjenja;
        return this;
    }

    public KorisnikBuilder setAdresa(String adresa)
    {
        this.adresa = adresa;
        return this;
    }

    public Korisnik createKorisnik()
    {
        return new Korisnik(id, ime, prezime, korisnikoIme, lozinka, datumRodjenja, adresa);
    }
}
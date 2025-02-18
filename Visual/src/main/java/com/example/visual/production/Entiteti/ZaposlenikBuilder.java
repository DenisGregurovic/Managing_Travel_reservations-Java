package com.example.visual.production.Entiteti;

import java.time.LocalDate;

public class ZaposlenikBuilder {
    private Long id;
    private String ime;
    private String prezime;
    private String korisnikoIme;
    private String lozinka;
    private LocalDate datumRodjenja;
    private Uloga uloga;

    public ZaposlenikBuilder setId(Long id)
    {
        this.id = id;
        return this;
    }

    public ZaposlenikBuilder setIme(String ime)
    {
        this.ime = ime;
        return this;
    }

    public ZaposlenikBuilder setPrezime(String prezime)
    {
        this.prezime = prezime;
        return this;
    }

    public ZaposlenikBuilder setKorisnikoIme(String korisnikoIme)
    {
        this.korisnikoIme = korisnikoIme;
        return this;
    }

    public ZaposlenikBuilder setLozinka(String lozinka)
    {
        this.lozinka = lozinka;
        return this;
    }

    public ZaposlenikBuilder setDatumRodjenja(LocalDate datumRodjenja)
    {
        this.datumRodjenja = datumRodjenja;
        return this;
    }

    public ZaposlenikBuilder setUloga(Uloga uloga)
    {
        this.uloga = uloga;
        return this;
    }

    public Zaposlenik createZaposlenik()
    {
        return new Zaposlenik(id, ime, prezime, korisnikoIme, lozinka, datumRodjenja, uloga);
    }
}
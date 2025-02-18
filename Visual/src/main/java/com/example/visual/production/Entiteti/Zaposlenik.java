package com.example.visual.production.Entiteti;

import java.time.LocalDate;

public final class Zaposlenik extends Osoba
{
    Uloga uloga;
    public Zaposlenik(Long id, String ime, String prezime, String korisnikoIme, String lozinka, LocalDate datumRodjenja,Uloga uloga)
    {
        super(id, ime, prezime, korisnikoIme, lozinka, datumRodjenja);
        this.uloga=uloga;
    }

    public Uloga getUloga()
    {
        return uloga;
    }

    public void setUloga(Uloga uloga)
    {
        this.uloga = uloga;
    }

}

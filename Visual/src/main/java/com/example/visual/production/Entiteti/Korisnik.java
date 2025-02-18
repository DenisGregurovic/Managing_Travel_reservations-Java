package com.example.visual.production.Entiteti;

import java.time.LocalDate;
import java.util.Objects;

public final class Korisnik extends Osoba
{
    String adresa;
    public Korisnik(Long id, String ime, String prezime, String korisnikoIme, String lozinka, LocalDate datumRodjenja,String adresa)
    {
        super(id, ime, prezime, korisnikoIme, lozinka, datumRodjenja);
        this.adresa=adresa;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

}

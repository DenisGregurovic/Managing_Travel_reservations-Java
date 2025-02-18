package com.example.visual.production.Entiteti;

public class Prijava extends Entitet
{
    String korisnickoIme;
    String lozinka;
    RazinaPrava razinaPrava;
    Long idOsobe;
    public Prijava(Long id, String korisnickoIme, Long idOsobe,String lozinka, RazinaPrava razinaPrava) {
        super(id);
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
        this.idOsobe=idOsobe;
        this.razinaPrava = razinaPrava;
    }

    public Prijava(Long id) {
        super(id);
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public Long getIdOsobe() {
        return idOsobe;
    }

    public void setIdOsobe(Long idOsobe) {
        this.idOsobe = idOsobe;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public RazinaPrava getRazinaPrava() {
        return razinaPrava;
    }

    public void setRazinaPrava(RazinaPrava razinaPrava) {
        this.razinaPrava = razinaPrava;
    }
}

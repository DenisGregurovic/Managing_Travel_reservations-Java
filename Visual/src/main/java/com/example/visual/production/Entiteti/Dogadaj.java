package com.example.visual.production.Entiteti;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Dogadaj extends Entitet
{

    String naziv;
    LocalDate datumDogadaja;
    String opis;
    BigDecimal cijena;
    Integer kolicina;
    public Dogadaj(Long id, String naziv, LocalDate datumDogadaja, String opis, BigDecimal cijena,Integer kolicina)
    {
        super(id);
        this.naziv = naziv;
        this.datumDogadaja = datumDogadaja;
        this.opis = opis;
        this.cijena = cijena;
        this.kolicina=kolicina;
    }

    public String getNaziv()
    {
        return naziv;
    }

    public void setNaziv(String naziv)
    {
        this.naziv = naziv;
    }

    public LocalDate getDatumDogadaja()
    {
        return datumDogadaja;
    }

    public void setDatumDogadaja(LocalDate datumDogadaja)
    {
        this.datumDogadaja = datumDogadaja;
    }

    public String getOpis()
    {
        return opis;
    }

    public void setOpis(String opis)
    {
        this.opis = opis;
    }

    public BigDecimal getCijena()
    {
        return cijena;
    }

    public void setCijena(BigDecimal cijena)
    {
        this.cijena = cijena;
    }

    public Integer getKolicina()
    {
        return kolicina;
    }

    public void setKolicina(Integer kolicina)
    {
        this.kolicina = kolicina;
    }
}

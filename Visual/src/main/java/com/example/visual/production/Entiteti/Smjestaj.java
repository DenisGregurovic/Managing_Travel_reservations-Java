package com.example.visual.production.Entiteti;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Smjestaj extends Entitet
{
    String naziv;
    String adresa;
    Vrsta vrstaSmjestaja;
    BigDecimal regularnaCijena;
    Popust popust;
    BigDecimal snizenaCijena;

    public Smjestaj(Long id,String naziv, String adresa,Vrsta vrstaSmjestaja, BigDecimal regularnaCijena, Popust popust, BigDecimal snizenaCijena)
    {
        super(id);
        this.naziv = naziv;
        this.adresa = adresa;
        this.vrstaSmjestaja = vrstaSmjestaja;
        this.regularnaCijena = regularnaCijena;
        this.popust = popust;
        this.snizenaCijena = snizenaCijena;
    }

    public String getNaziv()
    {
        return naziv;
    }

    public void setNaziv(String naziv)
    {
        this.naziv = naziv;
    }

    public String getAdresa()
    {
        return adresa;
    }

    public void setAdresa(String adresa)
    {
        this.adresa = adresa;
    }

    public Vrsta getVrstaSmjestaja()
    {
        return vrstaSmjestaja;
    }

    public void setVrstaSmjestaja(Vrsta vrstaSmjestaja)
    {
        this.vrstaSmjestaja = vrstaSmjestaja;
    }

    public BigDecimal getRegularnaCijena()
    {
        return regularnaCijena;
    }

    public void setRegularnaCijena(BigDecimal regularnaCijena)
    {
        this.regularnaCijena = regularnaCijena;
    }

    public Popust getPopust()
    {
        return popust;
    }

    public void setPopust(Popust popust)
    {
        this.popust = popust;
    }

    public BigDecimal getSnizenaCijena()
    {
        return snizenaCijena;
    }

    public void setSnizenaCijena(BigDecimal snizenaCijena)
    {
        this.snizenaCijena = snizenaCijena;
    }
}

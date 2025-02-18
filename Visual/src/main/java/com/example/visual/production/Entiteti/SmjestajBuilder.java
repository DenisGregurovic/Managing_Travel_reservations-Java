package com.example.visual.production.Entiteti;

import java.math.BigDecimal;

public class SmjestajBuilder {
    private Long id;
    private String naziv;
    private String adresa;
    private Vrsta vrstaSmjestaja;
    private BigDecimal regularnaCijena;
    private Popust popust;
    private BigDecimal snizenaCijena;

    public SmjestajBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public SmjestajBuilder setNaziv(String naziv) {
        this.naziv = naziv;
        return this;
    }

    public SmjestajBuilder setAdresa(String adresa) {
        this.adresa = adresa;
        return this;
    }

    public SmjestajBuilder setVrstaSmjestaja(Vrsta vrstaSmjestaja) {
        this.vrstaSmjestaja = vrstaSmjestaja;
        return this;
    }

    public SmjestajBuilder setRegularnaCijena(BigDecimal regularnaCijena) {
        this.regularnaCijena = regularnaCijena;
        return this;
    }

    public SmjestajBuilder setPopust(Popust popust) {
        this.popust = popust;
        return this;
    }

    public SmjestajBuilder setSnizenaCijena(BigDecimal snizenaCijena) {
        this.snizenaCijena = snizenaCijena;
        return this;
    }

    public Smjestaj createSmjestaj() {
        return new Smjestaj(id, naziv, adresa, vrstaSmjestaja, regularnaCijena, popust, snizenaCijena);
    }
}
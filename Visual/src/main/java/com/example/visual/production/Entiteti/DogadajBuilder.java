package com.example.visual.production.Entiteti;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DogadajBuilder {
    private Long id;
    private String naziv;
    private LocalDate datumDogadaja;
    private String opis;
    private BigDecimal cijena;
    private Integer kolicina;

    public DogadajBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public DogadajBuilder setNaziv(String naziv) {
        this.naziv = naziv;
        return this;
    }

    public DogadajBuilder setDatumDogadaja(LocalDate datumDogadaja) {
        this.datumDogadaja = datumDogadaja;
        return this;
    }

    public DogadajBuilder setOpis(String opis) {
        this.opis = opis;
        return this;
    }

    public DogadajBuilder setCijena(BigDecimal cijena) {
        this.cijena = cijena;
        return this;
    }

    public DogadajBuilder setKolicina(Integer kolicina) {
        this.kolicina = kolicina;
        return this;
    }


    public Dogadaj createDogadaj() {
        return new Dogadaj(id, naziv, datumDogadaja, opis, cijena, kolicina);
    }
}
package com.example.visual.production.Entiteti;

import java.time.LocalDateTime;

public class PromjenaBuilder<T, K> {
    private T pocetni;
    private T krajnji;
    private K uloga;
    private LocalDateTime vrijeme;

    public PromjenaBuilder setPocetni(T pocetni) {
        this.pocetni = pocetni;
        return this;
    }

    public PromjenaBuilder setKrajnji(T krajnji) {
        this.krajnji = krajnji;
        return this;
    }

    public PromjenaBuilder setUloga(K uloga) {
        this.uloga = uloga;
        return this;
    }

    public PromjenaBuilder setVrijeme(LocalDateTime vrijeme) {
        this.vrijeme = vrijeme;
        return this;
    }

    public Promjena createPromjena() {
        return new Promjena(pocetni, krajnji, uloga, vrijeme);
    }
}
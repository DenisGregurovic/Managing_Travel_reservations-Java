package com.example.visual.production.Entiteti;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Promjena <T,K> implements Serializable
{
    private final T pocetni;
    private final T krajnji;
    private final K uloga;
    private final LocalDateTime vrijeme;

    public Promjena(T pocetni, T krajnji, K uloga, LocalDateTime vrijeme) {
        this.pocetni = pocetni;
        this.krajnji = krajnji;
        this.uloga = uloga;
        this.vrijeme = vrijeme;
    }

    public T getPocetni() {
        return pocetni;
    }

    public T getKrajnji() {
        return krajnji;
    }

    public K getUloga() {
        return uloga;
    }

    public LocalDateTime getVrijeme() {
        return vrijeme;
    }
}

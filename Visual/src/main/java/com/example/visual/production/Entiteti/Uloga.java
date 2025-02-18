package com.example.visual.production.Entiteti;

import java.io.Serializable;

public enum Uloga implements Serializable
{
    NABAVA("Nabava"),
    PRODAJA ("Prodaja"),
    LJUDSKI_RESURSI("Ljudski resursi"),
    ADMINISTRACIJA("Administracija"),
    VODITELJ("Voditelj");
    private final String opis;

    Uloga(String opis)
    {
        this.opis = opis;
    }

    public String getOpis()
    {
        return opis;
    }

}

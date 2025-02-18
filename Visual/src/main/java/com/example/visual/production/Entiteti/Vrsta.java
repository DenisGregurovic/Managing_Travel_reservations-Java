package com.example.visual.production.Entiteti;

import java.io.Serializable;

public enum Vrsta implements Serializable
{
    HOTEL("Hotel"),
    HOSTEL("Hostel"),
    APARTMAN("Apartman"),
    SOBA("Soba");
    private final String opis;

    Vrsta(String opis)
    {
        this.opis = opis;
    }

    public String getOpis()
    {
        return opis;
    }
}

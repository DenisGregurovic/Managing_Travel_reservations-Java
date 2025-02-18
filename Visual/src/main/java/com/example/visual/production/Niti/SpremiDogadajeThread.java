package com.example.visual.production.Niti;

import com.example.visual.Pokreni;
import com.example.visual.production.BazaPodataka.Baza;
import com.example.visual.production.Entiteti.Dogadaj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SpremiDogadajeThread extends BazaThreads implements Runnable
{
    private Dogadaj dogadaj;
    public SpremiDogadajeThread(Dogadaj dogadaj)
    {
        this.dogadaj = dogadaj;
    }
    @Override
    public void run()
    {
        super.spremiDogadaj(this.dogadaj);
    }
}

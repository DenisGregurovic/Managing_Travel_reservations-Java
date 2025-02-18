package com.example.visual.production.Niti;

import com.example.visual.production.Entiteti.Dogadaj;

public class BrisiDogadajeThread extends BazaThreads implements Runnable
{
    private Dogadaj dogadaj;
    public BrisiDogadajeThread(Dogadaj dogadaj)
    {
        this.dogadaj = dogadaj;
    }
    @Override
    public void run()
    {
        super.brisiDogadaj(this.dogadaj);
    }
}

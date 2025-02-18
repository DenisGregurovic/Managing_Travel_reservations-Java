package com.example.visual.production.Niti;

import com.example.visual.production.Entiteti.Dogadaj;

public class AzurirajDogadajeThread extends BazaThreads implements Runnable
{
    private Dogadaj dogadaj;
    public AzurirajDogadajeThread(Dogadaj dogadaj)
    {
        this.dogadaj = dogadaj;
    }
    @Override
    public void run()
    {
        super.azurirajDogadaj(this.dogadaj);
    }
}

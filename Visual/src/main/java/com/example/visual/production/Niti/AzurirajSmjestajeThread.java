package com.example.visual.production.Niti;

import com.example.visual.production.Entiteti.Smjestaj;

public class AzurirajSmjestajeThread extends BazaThreads implements Runnable
{
    private Smjestaj smjestaj;
    public AzurirajSmjestajeThread(Smjestaj smjestaj)
    {
        this.smjestaj = smjestaj;
    }
    @Override
    public void run()
    {
        super.azurirajSmjestaj(this.smjestaj);
    }
}

package com.example.visual.production.Niti;

import com.example.visual.production.Entiteti.Smjestaj;

public class BrisiSmjestajeThread extends BazaThreads implements Runnable
{
    private Smjestaj smjestaj;
    public BrisiSmjestajeThread(Smjestaj smjestaj)
    {
        this.smjestaj = smjestaj;
    }
    @Override
    public void run()
    {
        super.brisiSmjestaj(this.smjestaj);
    }
}

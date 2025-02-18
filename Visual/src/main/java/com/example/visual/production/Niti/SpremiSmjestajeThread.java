package com.example.visual.production.Niti;

import com.example.visual.Pokreni;
import com.example.visual.production.Entiteti.Dogadaj;
import com.example.visual.production.Entiteti.Smjestaj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpremiSmjestajeThread extends BazaThreads implements Runnable
{
    private Smjestaj smjestaj;
    public SpremiSmjestajeThread(Smjestaj smjestaj)
    {
        this.smjestaj = smjestaj;
    }
    @Override
    public void run()
    {
        super.spremiSmjestaj(this.smjestaj);
    }
}

package com.example.visual.production.Niti;

import com.example.visual.production.Entiteti.Zaposlenik;

public class SpremiZaposlenikaUDatotekuThread extends DatotekeThreads implements Runnable
{
    private Zaposlenik zaposlenik;
    public SpremiZaposlenikaUDatotekuThread(Zaposlenik zaposlenik)
    {
        this.zaposlenik = zaposlenik;
    }
    @Override
    public void run()
    {
        super.spremiZaposlenika(this.zaposlenik);
    }
}

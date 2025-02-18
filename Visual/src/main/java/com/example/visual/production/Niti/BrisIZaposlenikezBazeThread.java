package com.example.visual.production.Niti;

import com.example.visual.production.Entiteti.Zaposlenik;

public class BrisIZaposlenikezBazeThread extends BazaThreads implements Runnable
{
    private Zaposlenik zaposlenik;
    public BrisIZaposlenikezBazeThread(Zaposlenik zaposlenik)
    {
        this.zaposlenik = zaposlenik;
    }
    @Override
    public void run()
    {
        super.obrisiZaposlenika(this.zaposlenik);
    }
}

package com.example.visual.production.Niti;

import com.example.visual.production.Entiteti.Zaposlenik;

public class BrisiZaposlenikeIzDatotekeThread extends DatotekeThreads implements Runnable
{
    private Zaposlenik zaposlenik;
    public BrisiZaposlenikeIzDatotekeThread(Zaposlenik zaposlenik)
    {
        this.zaposlenik = zaposlenik;
    }
    @Override
    public void run()
    {
        super.brisiZaposlenika(this.zaposlenik);
    }
}

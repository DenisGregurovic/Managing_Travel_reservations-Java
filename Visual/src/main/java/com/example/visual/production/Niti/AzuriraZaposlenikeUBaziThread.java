package com.example.visual.production.Niti;

import com.example.visual.production.Entiteti.Zaposlenik;

public class AzuriraZaposlenikeUBaziThread extends BazaThreads implements Runnable
{
    private Zaposlenik zaposlenik;
    public AzuriraZaposlenikeUBaziThread(Zaposlenik zaposlenik)
    {
        this.zaposlenik = zaposlenik;
    }
    @Override
    public void run()
    {
        super.azurirajZaposlenika(this.zaposlenik);
    }
}

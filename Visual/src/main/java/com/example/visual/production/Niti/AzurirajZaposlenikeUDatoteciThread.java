package com.example.visual.production.Niti;

import com.example.visual.production.Entiteti.Zaposlenik;

public class AzurirajZaposlenikeUDatoteciThread extends DatotekeThreads implements Runnable
{
    private Zaposlenik zaposlenik;
    public AzurirajZaposlenikeUDatoteciThread(Zaposlenik zaposlenik)
    {
        this.zaposlenik = zaposlenik;
    }
    @Override
    public void run()
    {
        super.azurirajZaposlenika(this.zaposlenik);
    }
}

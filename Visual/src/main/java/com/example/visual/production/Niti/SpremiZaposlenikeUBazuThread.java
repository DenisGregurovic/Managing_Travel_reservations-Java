package com.example.visual.production.Niti;

import com.example.visual.Pokreni;
import com.example.visual.production.BazaPodataka.Baza;
import com.example.visual.production.Entiteti.Smjestaj;
import com.example.visual.production.Entiteti.Zaposlenik;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpremiZaposlenikeUBazuThread extends BazaThreads implements Runnable
{
    private Zaposlenik zaposlenik;
    public SpremiZaposlenikeUBazuThread(Zaposlenik zaposlenik)
    {
        this.zaposlenik = zaposlenik;
    }
    @Override
    public void run()
    {
        super.spremiZaposlenika(this.zaposlenik);
    }
}

package com.example.visual.production.Entiteti;

import com.example.visual.Pokreni;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Serijalizacija<T> implements Serijaliziraj<T>
{
    private static final Logger logger = LoggerFactory.getLogger(Pokreni.class);
    @Override
    public void serializiraj(T objekt, String datoteka)
    {
        List<T> lista = new ArrayList<>();
        if (ucitaj(datoteka)!=null)
        {
            lista.addAll(ucitaj(datoteka));
        }
        lista.add(objekt);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(datoteka)))
        {
            out.writeObject(lista);
        }
        catch (IOException ex)
        {
            logger.info(ex.getMessage());
        }
    }
    public void serializirajRacun(T objekt,String datoteka)
    {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(datoteka)))
        {
            out.writeObject(objekt);
        }
        catch (IOException ex)
        {
            logger.info(ex.getMessage());
        }
    }
    public static void ocistitDatoteku(String filePath)
    {
        try
        {
            RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "rw");
            randomAccessFile.setLength(0);
            randomAccessFile.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            logger.info("Došlo je do greške prilikom brisanja sadržaja binarne datoteke.");
        }
    }

    public Korisnik ucitajKorisnickiRacun(String datoteka)
    {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(datoteka)))
        {
            return (Korisnik) in.readObject();
        }
        catch (IOException | ClassNotFoundException iznimka)
        {
            logger.info(iznimka.getMessage());
        }
        return null;
    }
    public Zaposlenik ucitajZaposlenickiRacun(String datoteka)
    {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(datoteka)))
        {
            return (Zaposlenik) in.readObject();
        }
        catch (IOException | ClassNotFoundException iznimka)
        {
            logger.info(iznimka.getMessage());
        }
        return null;
    }
    @Override
    public List<T> ucitaj(String datoteka)
    {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(datoteka)))
        {
            return (List<T>) in.readObject();
        }
        catch (IOException | ClassNotFoundException iznimka)
        {
            logger.info(iznimka.getMessage());
        }
        return null;
    }
}

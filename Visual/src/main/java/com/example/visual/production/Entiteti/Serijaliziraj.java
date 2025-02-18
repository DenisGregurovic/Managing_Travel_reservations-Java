package com.example.visual.production.Entiteti;

import java.util.List;

public interface Serijaliziraj <T>
{
    void serializiraj(T objekt, String datoteka);
    List<T> ucitaj(String datoteka);

}

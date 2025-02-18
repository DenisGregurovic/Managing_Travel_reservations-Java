package com.example.visual.production.Iznimke.Oznacene;

import java.io.IOException;

public class KorisnickoImePostojiException extends IOException
{
    public KorisnickoImePostojiException() {
    }

    public KorisnickoImePostojiException(String message) {
        super(message);
    }

    public KorisnickoImePostojiException(String message, Throwable cause) {
        super(message, cause);
    }

    public KorisnickoImePostojiException(Throwable cause) {
        super(cause);
    }
}

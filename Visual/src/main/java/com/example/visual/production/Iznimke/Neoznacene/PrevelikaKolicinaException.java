package com.example.visual.production.Iznimke.Neoznacene;

public class PrevelikaKolicinaException extends Exception
{
    public PrevelikaKolicinaException() {
    }

    public PrevelikaKolicinaException(String message) {
        super(message);
    }

    public PrevelikaKolicinaException(String message, Throwable cause) {
        super(message, cause);
    }

    public PrevelikaKolicinaException(Throwable cause) {
        super(cause);
    }

    public PrevelikaKolicinaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

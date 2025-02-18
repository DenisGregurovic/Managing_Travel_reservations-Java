package com.example.visual.production.Iznimke.Neoznacene;

public class BuduciDatumException extends Exception{
    public BuduciDatumException() {
    }

    public BuduciDatumException(String message) {
        super(message);
    }

    public BuduciDatumException(String message, Throwable cause) {
        super(message, cause);
    }

    public BuduciDatumException(Throwable cause) {
        super(cause);
    }

    public BuduciDatumException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

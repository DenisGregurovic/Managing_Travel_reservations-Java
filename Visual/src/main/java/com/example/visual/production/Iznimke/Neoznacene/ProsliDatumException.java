package com.example.visual.production.Iznimke.Neoznacene;

public class ProsliDatumException extends Exception{
    public ProsliDatumException() {
    }

    public ProsliDatumException(String message) {
        super(message);
    }

    public ProsliDatumException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProsliDatumException(Throwable cause) {
        super(cause);
    }

    public ProsliDatumException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

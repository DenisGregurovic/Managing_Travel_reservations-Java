package com.example.visual.production.Iznimke.Oznacene;

import java.io.IOException;

public class BrojException extends IOException
{
    public BrojException() {
    }

    public BrojException(String message) {
        super(message);
    }

    public BrojException(String message, Throwable cause) {
        super(message, cause);
    }

    public BrojException(Throwable cause) {
        super(cause);
    }
}

package de.tobchen.health.questobyonnaire.rest.exceptions;

public class JsonSerializationException extends RuntimeException {
    public JsonSerializationException(String msg)
    {
        super(msg);
    }
}

package de.tobchen.health.questobyonnaire.rest.exceptions;

public class JsonDeSerializationException extends RuntimeException {
    public JsonDeSerializationException(String msg)
    {
        super(msg);
    }
}

package de.tobchen.health.questobyonnaire.rest.exceptions;

public class IllegalSearchParameterException extends IllegalArgumentException {
    public IllegalSearchParameterException(String msg)
    {
        super(msg);
    }
}

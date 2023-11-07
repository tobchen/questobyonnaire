package de.tobchen.health.questobyonnaire.rest.exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException()
    {
        super("Cannot find questionnaire");
    }
}

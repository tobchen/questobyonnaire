package de.tobchen.health.questobyonnaire.rest.exceptions;

import de.tobchen.health.questobyonnaire.model.deserialized.DeserializedQuestionnaireStatus;

public class IllegalQuestionnaireStatusChangeException extends IllegalArgumentException {
    public IllegalQuestionnaireStatusChangeException(DeserializedQuestionnaireStatus from,
        DeserializedQuestionnaireStatus to)
    {
        super("Cannot change from " + from.value + " to " + to.value);
    }
}

package de.tobchen.health.questobyonnaire.fhir.util;

import org.hl7.fhir.r4.model.Questionnaire;

import ca.uhn.fhir.rest.server.exceptions.UnprocessableEntityException;

public abstract class QuestionnaireUtil {
    public static void checkRules(Questionnaire questionnaire) throws UnprocessableEntityException
    {
        // TODO Check
    }
}

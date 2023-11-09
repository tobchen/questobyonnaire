package de.tobchen.health.questobyonnaire.fhir.providers;

import org.hl7.fhir.r5.model.Questionnaire;

import ca.uhn.fhir.rest.server.IResourceProvider;

public abstract class AbstractQuestionnaireProvider implements IResourceProvider
{

    @Override
    public Class<Questionnaire> getResourceType()
    {
        return Questionnaire.class;
    }
    
}

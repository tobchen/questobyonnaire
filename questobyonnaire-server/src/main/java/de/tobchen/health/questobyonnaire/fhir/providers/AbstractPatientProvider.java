package de.tobchen.health.questobyonnaire.fhir.providers;

import org.hl7.fhir.r5.model.Patient;

import ca.uhn.fhir.rest.server.IResourceProvider;

public abstract class AbstractPatientProvider implements IResourceProvider
{
    
    @Override
    public Class<Patient> getResourceType()
    {
        return Patient.class;
    }

}

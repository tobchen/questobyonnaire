package de.tobchen.health.questobyonnaire.fhir.providers;

import java.util.ArrayList;
import java.util.List;

import org.hl7.fhir.r5.model.IdType;
import org.hl7.fhir.r5.model.Parameters;
import org.hl7.fhir.r5.model.Patient;
import org.springframework.stereotype.Service;

import ca.uhn.fhir.rest.annotation.Create;
import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.Operation;
import ca.uhn.fhir.rest.annotation.OperationParam;
import ca.uhn.fhir.rest.annotation.OptionalParam;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.annotation.RequiredParam;
import ca.uhn.fhir.rest.annotation.ResourceParam;
import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.annotation.Update;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.param.DateParam;
import ca.uhn.fhir.rest.param.ReferenceParam;
import ca.uhn.fhir.rest.param.StringParam;
import ca.uhn.fhir.rest.param.TokenParam;

@Service
public class PatientProvider extends AbstractPatientProvider {
    @Create
    public MethodOutcome create(@ResourceParam Patient patient)
    {
        // TODO Implement
        return null;
    }

    @Read
    public Patient read(@IdParam IdType id)
    {
        // TODO Implement
        return null;
    }

    @Update
    public MethodOutcome update(@IdParam IdType id, @ResourceParam Patient patient)
    {
        // TODO Implement
        return null;
    }

    @Search
    public List<Patient> searchByIdentifier(@RequiredParam(name = Patient.SP_IDENTIFIER) TokenParam identifier)
    {
        var result = new ArrayList<Patient>();

        // TODO Implement

        return result;
    }

    @Search
    public List<Patient> searchByDemographics(@OptionalParam(name = Patient.SP_FAMILY) StringParam familyName,
        @OptionalParam(name = Patient.SP_GIVEN) StringParam givenName,
        @OptionalParam(name = Patient.SP_BIRTHDATE) DateParam birthdate)
    {
        var result = new ArrayList<Patient>();

        // TODO Implement

        return result;
    }

    @Operation(name = "$merge", idempotent = false)
    public Parameters merge(@OperationParam(name = "source-patient") ReferenceParam sourcePatient,
        @OperationParam(name = "target-patient") ReferenceParam targetPatient)
    {
        // TODO Implement
        return null;        
    }
}

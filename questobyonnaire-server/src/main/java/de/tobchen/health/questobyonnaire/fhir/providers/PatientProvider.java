package de.tobchen.health.questobyonnaire.fhir.providers;

import java.util.ArrayList;
import java.util.List;

import org.hl7.fhir.r5.model.HumanName;
import org.hl7.fhir.r5.model.IdType;
import org.hl7.fhir.r5.model.Identifier;
import org.hl7.fhir.r5.model.Parameters;
import org.hl7.fhir.r5.model.Patient;
import org.hl7.fhir.r5.model.Reference;
import org.hl7.fhir.r5.model.HumanName.NameUse;
import org.hl7.fhir.r5.model.Patient.LinkType;
import org.hl7.fhir.r5.model.Patient.PatientLinkComponent;
import org.springframework.stereotype.Service;

import ca.uhn.fhir.parser.IParser;
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
import de.tobchen.health.questobyonnaire.fhir.entities.PatientEntity;
import de.tobchen.health.questobyonnaire.fhir.repositories.PatientRepository;

@Service
public class PatientProvider extends AbstractPatientProvider {
    private final PatientRepository repository;
    private final IParser parser;

    public PatientProvider(PatientRepository repository, IParser parser)
    {
        this.repository = repository;
        this.parser = parser;
    }

    @Create
    public MethodOutcome create(@ResourceParam Patient patient)
    {
        var entity = new PatientEntity();
        entity = repository.save(entity);

        patient.setId(entity.getId().toString());
        updateEntityAndResource(entity, patient);

        repository.save(entity);

        return new MethodOutcome(patient.getIdElement());
    }

    @Update
    public MethodOutcome update(@IdParam IdType id, @ResourceParam Patient patient)
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

    @Search
    public List<Patient> searchByMrn(@RequiredParam(name = Patient.SP_IDENTIFIER) TokenParam identifier)
    {
        var result = new ArrayList<Patient>();

        // TODO Implement

        return result;
    }

    @Search
    public List<Patient> searchByDemographics(@OptionalParam(name = Patient.SP_FAMILY) StringParam familyName,
        @OptionalParam(name = Patient.SP_GIVEN) StringParam givenName,
        @OptionalParam(name = Patient.SP_BIRTHDATE) DateParam birthDate)
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

    private void updateEntityAndResource(PatientEntity entity, Patient resource)
    {
        if (entity.getMrn() == null)
        {
            var mrnIdentifier = findMrn(resource);
            if (mrnIdentifier != null)
            {
                var mrn = mrnIdentifier.getValue();
                if (mrn != null)
                {
                    entity.setMrn(mrn);
                    entity.setMrnSystem(mrnIdentifier.getSystem());
                }
            }
        }

        String givenName = null;
        String familyName = null;

        var name = findRelevantHumanName(resource);
        if (name != null)
        {
            givenName = name.getGivenAsSingleString();
            familyName = name.getFamily();
        }

        entity.setGivenName(givenName);
        entity.setFamilyName(familyName);

        var birthDate = entity.getBirthDate();
        if (birthDate != null)
        {
            entity.setBirthDate(new java.sql.Date(birthDate.getTime()));
        }

        var mergedIntoPatient = entity.getMergedInto();
        if (mergedIntoPatient != null)
        {
            var linkList = new ArrayList<PatientLinkComponent>();
            linkList.add(new PatientLinkComponent(new Reference(
                new IdType("Patient", mergedIntoPatient.getId())), LinkType.REPLACEDBY));
            resource.setLink(linkList);
        }
        else
        {
            resource.setLink(null);
        }

        // TODO Catch exception
        var serialized = parser.encodeResourceToString(resource);
        entity.setSerialized(serialized);
    }

    private HumanName findRelevantHumanName(Patient patient)
    {
        HumanName result = null;

        var names = patient.getName();
        if (names != null && !names.isEmpty())
        {
            result = names.get(0);
            var resultUse = result.getUse();

            for (int i = 1; i < names.size(); ++i)
            {
                var name = names.get(i);
                var use = name.getUse();
                if (!use.equals(resultUse) && (use.equals(NameUse.OFFICIAL)
                    || (use.equals(NameUse.USUAL) && resultUse.equals(NameUse.OFFICIAL))))
                {
                    result = name;
                    resultUse = use;
                }
            }
        }

        return result;
    }

    private Identifier findMrn(Patient patient)
    {
        Identifier result = null;

        var identifiers = patient.getIdentifier();
        if (identifiers != null)
        {
            identifier_loop:
            for (var identifier : identifiers)
            {
                var type = identifier.getType();
                if (type != null)
                {
                    var codings = type.getCoding();
                    if (codings != null)
                    {
                        for (var coding : codings)
                        {
                            if ("MR".equals(coding.getCode())
                                && "http://terminology.hl7.org/CodeSystem/v2-0203".equals(coding.getSystem()))
                            {
                                result = identifier;
                                break identifier_loop;
                            }
                        }
                    }
                }
            }
        }

        return result;
    }
}

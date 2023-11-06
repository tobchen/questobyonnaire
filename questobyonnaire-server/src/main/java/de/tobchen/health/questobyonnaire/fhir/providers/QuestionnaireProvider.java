package de.tobchen.health.questobyonnaire.fhir.providers;

import java.util.ArrayList;
import java.util.List;

import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Questionnaire;
import org.hl7.fhir.r4.model.Enumerations.PublicationStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.rest.annotation.Create;
import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.OptionalParam;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.annotation.ResourceParam;
import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.annotation.Update;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.param.TokenParam;
import ca.uhn.fhir.rest.server.exceptions.InvalidRequestException;
import de.tobchen.health.questobyonnaire.fhir.entities.QuestionnaireEntity;
import de.tobchen.health.questobyonnaire.fhir.repositories.QuestionnaireRepository;
import de.tobchen.health.questobyonnaire.fhir.util.QuestionnaireUtil;
import de.tobchen.health.questobyonnaire.fhir.util.ResourceUtil;

@Service
public class QuestionnaireProvider extends AbstractQuestionnaireProvider
{
    private final IParser parser;

    private final QuestionnaireRepository repository;

    public QuestionnaireProvider(FhirContext context, QuestionnaireRepository repository)
    {
        // XXX Maybe use setDontEncodeElements on parser
        parser = context.newJsonParser();

        this.repository = repository;
    }

    @Search
    public List<Questionnaire> search(@OptionalParam(name = Questionnaire.SP_STATUS) TokenParam status)
    {
        // TODO Catch exception
        var pubStatus = status != null ? PublicationStatus.fromCode(status.getValue()) : null;
        
        var entities = pubStatus != null ?
            repository.findAllByStatus(pubStatus) : repository.findAll();

        var result = new ArrayList<Questionnaire>();

        for (var entity : entities)
        {
            var questionnaire = resourceFromEntity(entity);
            if (questionnaire != null)
            {
                result.add(questionnaire);
            }
        }

        return result;
    }

    @Read
    public Questionnaire read(@IdParam IdType id)
    {
        // TODO Catch exception
        var longId = id.getIdPartAsLong();

        var optional = repository.findById(longId);

        Questionnaire questionnaire = null;
        if (optional.isPresent())
        {
            questionnaire = resourceFromEntity(optional.get());
        }

        return questionnaire;
    }

    @Create
    public MethodOutcome create(@ResourceParam Questionnaire questionnaire)
    {
        QuestionnaireUtil.checkRules(questionnaire);

        ResourceUtil.removeMeta(questionnaire);

        // TODO Catch exception
        var resourceString = parser.encodeResourceToString(questionnaire);

        var status = questionnaire.getStatus();
        if (status == null)
        {
            status = PublicationStatus.UNKNOWN;
        }

        var entity = new QuestionnaireEntity(status, resourceString);
        entity = repository.save(entity);

        questionnaire.setId(entity.getId().toString());
        
        // TODO Catch exception
        resourceString = parser.encodeResourceToString(questionnaire);
        entity.setResource(resourceString);

        entity = repository.save(entity);

        return new MethodOutcome(new IdType("Questionnaire", entity.getId().toString()));
    }

    @Update
    public MethodOutcome update(@IdParam IdType idType, @ResourceParam Questionnaire questionnaire)
    {
        QuestionnaireUtil.checkRules(questionnaire);

        ResourceUtil.removeMeta(questionnaire);
        
        var status = questionnaire.getStatus();
        if (status == null)
        {
            status = PublicationStatus.UNKNOWN;
        }

        // TODO Catch exception
        var id = idType.getIdPartAsLong();

        var optionalEntity = repository.findById(id);
        if (!optionalEntity.isPresent())
        {
            throw new InvalidRequestException("No resource to update found");
        }

        var entity = optionalEntity.get();

        // TODO Allowed status updates: unknown->*, draft->draft|active|retired, active->retired
        
        entity.setStatus(status);

        // TODO Catch exception
        var resourceString = parser.encodeResourceToString(questionnaire);
        entity.setResource(resourceString);

        entity = repository.save(entity);

        return new MethodOutcome(new IdType("Questionnaire", entity.getId().toString()));
    }

    private @Nullable Questionnaire resourceFromEntity(@NonNull QuestionnaireEntity entity)
    {
        // TODO Catch exception
        var questionnaire = parser.parseResource(Questionnaire.class,
            entity.getResource());

        return questionnaire;
    }
}

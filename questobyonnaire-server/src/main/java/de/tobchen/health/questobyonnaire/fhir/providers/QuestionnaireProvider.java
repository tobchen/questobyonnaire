package de.tobchen.health.questobyonnaire.fhir.providers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import de.tobchen.health.questobyonnaire.fhir.entities.QuestionnaireEntity;
import de.tobchen.health.questobyonnaire.fhir.repositories.QuestionnaireRepository;
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

        var questionnaire = resourceFromEntity(optional);

        return questionnaire;
    }

    @Create
    public MethodOutcome create(@ResourceParam Questionnaire questionnaire)
    {
        ResourceUtil.removeMeta(questionnaire);

        // TODO Catch exception
        var resourceString = parser.encodeResourceToString(questionnaire);

        var entity = new QuestionnaireEntity(questionnaire.getStatus(), resourceString);
        repository.save(entity);

        return new MethodOutcome(new IdType("Questionnaire", entity.getId().toString()));
    }

    @Update
    public MethodOutcome update(@IdParam IdType id, @ResourceParam Questionnaire questionnaire)
    {
        ResourceUtil.removeMeta(questionnaire);

        // TODO Check statuses; allowed updates: unknown->*, draft->draft|active|retired, active->retired

        return new MethodOutcome();
    }

    private @Nullable Questionnaire resourceFromEntity(@NonNull QuestionnaireEntity entity)
    {
        // TODO Catch exception
        var questionnaire = parser.parseResource(Questionnaire.class,
            entity.getResource());

        ResourceUtil.addMeta(questionnaire, entity.getId().toString());

        return questionnaire;
    }

    private @Nullable Questionnaire resourceFromEntity(@NonNull Optional<QuestionnaireEntity> entity)
    {
        return entity.isPresent() ? resourceFromEntity(entity.get()) : null;
    }
}

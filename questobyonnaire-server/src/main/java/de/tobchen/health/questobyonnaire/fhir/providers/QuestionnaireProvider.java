package de.tobchen.health.questobyonnaire.fhir.providers;

import java.util.ArrayList;
import java.util.List;

import org.hl7.fhir.r5.model.IdType;
import org.hl7.fhir.r5.model.Questionnaire;
import org.hl7.fhir.r5.model.Enumerations.PublicationStatus;
import org.springframework.stereotype.Service;

import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.rest.annotation.Create;
import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.OptionalParam;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.annotation.ResourceParam;
import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.param.TokenParam;
import de.tobchen.health.questobyonnaire.fhir.entities.QuestionnaireEntity;
import de.tobchen.health.questobyonnaire.fhir.repositories.QuestionnaireRepository;

@Service
public class QuestionnaireProvider extends AbstractQuestionnaireProvider
{
    private final QuestionnaireRepository repository;
    private final IParser parser;

    public QuestionnaireProvider(QuestionnaireRepository repository, IParser parser)
    {
        this.repository = repository;
        this.parser = parser;
    }

    @Create
    public MethodOutcome create(@ResourceParam Questionnaire questionnaire)
    {
        // TODO Check status
        var status = questionnaire.getStatus();

        // TODO Catch exception
        var serialized = parser.encodeResourceToString(questionnaire);

        var entity = new QuestionnaireEntity(status, serialized);
        entity = repository.save(entity);

        questionnaire.setId(entity.getId().toString());

        // TODO Catch exception
        serialized = parser.encodeResourceToString(questionnaire);
        entity.setSerialized(serialized);
        repository.save(entity);

        return new MethodOutcome(questionnaire.getIdElement());
    }

    @Search
    public List<Questionnaire> search(@OptionalParam(name = Questionnaire.SP_STATUS) TokenParam statusParam)
    {
        Iterable<QuestionnaireEntity> entityList = null;

        if (statusParam == null)
        {
            entityList = repository.findAll();
        }
        else
        {
            PublicationStatus status = PublicationStatus.fromCode(statusParam.getValue());
            
            if (status != null)
            {
                String qualifier = statusParam.getQueryParameterQualifier();

                if (qualifier == null)
                {
                    entityList = repository.findAllByStatus(status);
                }
                else if (qualifier.equals(":not"))
                {
                    entityList = repository.findAllByStatusNot(status);
                }
            }
        }

        var result = new ArrayList<Questionnaire>();

        if (entityList != null)
        {
            for (var entity : entityList)
            {
                var questionnaire = resourceFromEntity(entity);

                if (questionnaire != null)
                {
                    result.add(questionnaire);
                }
            }
        }

        return result;
    }

    @Read
    public Questionnaire read(@IdParam IdType idParam)
    {
        Questionnaire questionnaire = null;

        // TODO Catch exception
        Long id = idParam.getIdPartAsLong();

        var optionalEntity = repository.findById(id);

        if (optionalEntity.isPresent())
        {
            questionnaire = resourceFromEntity(optionalEntity.get());
        }

        return questionnaire;
    }

    private Questionnaire resourceFromEntity(QuestionnaireEntity entity)
    {
        Questionnaire questionnaire = null;

        if (entity != null)
        {
            // TODO Catch exception
            questionnaire = parser.parseResource(Questionnaire.class, entity.getSerialized());
        }

        return questionnaire;
    }
}

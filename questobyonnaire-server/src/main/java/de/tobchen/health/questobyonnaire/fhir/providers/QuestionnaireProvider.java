package de.tobchen.health.questobyonnaire.fhir.providers;

import java.util.ArrayList;
import java.util.List;

import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Questionnaire;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.annotation.Search;
import de.tobchen.health.questobyonnaire.model.entities.QuestionnaireEntity;
import de.tobchen.health.questobyonnaire.model.repositories.QuestionnaireRepository;

@Service
public class QuestionnaireProvider extends AbstractQuestionnaireProvider
{
    private final QuestionnaireRepository repository;

    public QuestionnaireProvider(QuestionnaireRepository repository)
    {
        this.repository = repository;
    }

    @Search
    public List<Questionnaire> search()
    {
        var entities = repository.findAll();

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
        Questionnaire questionnaire = null;

        try {
            var optional = repository.findById(id.getIdPartAsLong());
            if (optional.isPresent())
            {
                questionnaire = resourceFromEntity(optional.get());
            }
        }
        catch (NumberFormatException e)
        {
            // Nothing
        }

        return questionnaire;
    }

    private @Nullable Questionnaire resourceFromEntity(@NonNull QuestionnaireEntity entity)
    {
        // TODO
        return null;
    }
}

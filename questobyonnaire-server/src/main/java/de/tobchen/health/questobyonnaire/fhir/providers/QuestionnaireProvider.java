package de.tobchen.health.questobyonnaire.fhir.providers;

import java.util.ArrayList;
import java.util.List;

import org.hl7.fhir.r5.model.IdType;
import org.hl7.fhir.r5.model.Questionnaire;
import org.springframework.stereotype.Service;

import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.annotation.Search;
import de.tobchen.health.questobyonnaire.model.deserialized.DeserializedQuestionnaire;
import de.tobchen.health.questobyonnaire.rest.controllers.QuestionnaireController;

@Service
public class QuestionnaireProvider extends AbstractQuestionnaireProvider
{
    private final QuestionnaireController controller;

    public QuestionnaireProvider(QuestionnaireController controller)
    {
        this.controller = controller;
    }

    @Search
    public List<Questionnaire> search()
    {
        var result = new ArrayList<Questionnaire>();

        var deserializedQuestionnaires = controller.search(null, null);

        for (var deserializedQuestionnaire : deserializedQuestionnaires)
        {
            var questionnaire = resourceFromDeserialized(deserializedQuestionnaire);
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
            var deserializedQuestionnaire = controller.read(id.getIdPartAsLong());
            questionnaire = resourceFromDeserialized(deserializedQuestionnaire);
        }
        catch (NumberFormatException e)
        {
            // Nothing
        }

        return questionnaire;
    }

    private Questionnaire resourceFromDeserialized(DeserializedQuestionnaire deserialized)
    {
        // TODO Implement
        return null;
    }
}

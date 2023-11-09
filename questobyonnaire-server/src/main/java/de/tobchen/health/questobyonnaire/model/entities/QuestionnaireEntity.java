package de.tobchen.health.questobyonnaire.model.entities;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import com.fasterxml.jackson.core.JsonProcessingException;

import de.tobchen.health.questobyonnaire.model.deserialized.DeserializedQuestionnaire;
import de.tobchen.health.questobyonnaire.model.deserialized.DeserializedQuestionnaireStatus;
import de.tobchen.health.questobyonnaire.model.util.JacksonObjectMapper;
import de.tobchen.health.questobyonnaire.rest.exceptions.IllegalQuestionnaireStatusChangeException;

@Entity
public class QuestionnaireEntity
{
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated
    private DeserializedQuestionnaireStatus status;

    @Lob
    private String serialized;

    protected QuestionnaireEntity() {}

    public QuestionnaireEntity(DeserializedQuestionnaire questionnaire) throws JsonProcessingException
    {
        this.status = questionnaire.status();
        this.serialized = JacksonObjectMapper.getWriter().writeValueAsString(questionnaire);
    }

    public void update(DeserializedQuestionnaire questionnaire) throws JsonProcessingException
    {
        switch (this.status)
        {
        case DRAFT:
            this.serialized = JacksonObjectMapper.getWriter().writeValueAsString(questionnaire);
            this.status = questionnaire.status();
            break;
        case PUBLISHED:
            if (questionnaire.status() == DeserializedQuestionnaireStatus.RETIRED)
            {
                DeserializedQuestionnaire previous =
                    JacksonObjectMapper.getReader().readValue(this.serialized);
                DeserializedQuestionnaire current =
                    new DeserializedQuestionnaire(previous.id(), previous.title(),
                        previous.description(), questionnaire.status(), previous.items());
                
                this.serialized = JacksonObjectMapper.getWriter().writeValueAsString(current);
                this.status = current.status();

                break;
            }
        case RETIRED:
            throw new IllegalQuestionnaireStatusChangeException(this.status, questionnaire.status());
        }
    }

    public Long getId() {
        return id;
    }

    public DeserializedQuestionnaireStatus getStatus() {
        return status;
    }

    public String getSerialized() {
        return serialized;
    }
}

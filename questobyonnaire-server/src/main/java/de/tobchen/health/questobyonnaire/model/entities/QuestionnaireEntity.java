package de.tobchen.health.questobyonnaire.model.entities;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import de.tobchen.health.questobyonnaire.model.deserialized.DeserializedQuestionnaireStatus;

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

    public QuestionnaireEntity(DeserializedQuestionnaireStatus status, String serialized)
    {
        this.status = status;
        this.serialized = serialized;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DeserializedQuestionnaireStatus getStatus() {
        return status;
    }

    public void setStatus(DeserializedQuestionnaireStatus status) {
        this.status = status;
    }

    public String getSerialized() {
        return serialized;
    }

    public void setSerialized(String serialized) {
        this.serialized = serialized;
    }
}

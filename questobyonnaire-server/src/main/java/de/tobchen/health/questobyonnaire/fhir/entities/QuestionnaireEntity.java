package de.tobchen.health.questobyonnaire.fhir.entities;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.hl7.fhir.r5.model.Enumerations.PublicationStatus;

@Entity
public class QuestionnaireEntity
{
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated
    private PublicationStatus status;

    @Lob
    private String serialized;

    protected QuestionnaireEntity() {}

    public QuestionnaireEntity(PublicationStatus status)
    {
        this.status = status;
    }

    public void setStatus(PublicationStatus status) {
        this.status = status;
    }

    public void setSerialized(String serialized) {
        this.serialized = serialized;
    }

    public Long getId() {
        return id;
    }

    public PublicationStatus getStatus() {
        return status;
    }

    public String getSerialized() {
        return serialized;
    }
}

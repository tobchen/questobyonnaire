package de.tobchen.health.questobyonnaire.fhir.entities;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.hl7.fhir.r4.model.Enumerations.PublicationStatus;

@Entity
public class QuestionnaireEntity
{
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated
    private PublicationStatus status;

    @Lob
    private String resource;

    protected QuestionnaireEntity() {}

    public QuestionnaireEntity(PublicationStatus status, String resource)
    {
        this.status = status;
        this.resource = resource;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PublicationStatus getStatus() {
        return status;
    }

    public void setStatus(PublicationStatus status) {
        this.status = status;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}

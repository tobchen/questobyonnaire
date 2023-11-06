package de.tobchen.health.questobyonnaire.fhir.repositories;

import org.hl7.fhir.r4.model.Enumerations.PublicationStatus;
import org.springframework.data.repository.CrudRepository;

import de.tobchen.health.questobyonnaire.fhir.entities.QuestionnaireEntity;

public interface QuestionnaireRepository extends CrudRepository<QuestionnaireEntity, Long> {
    public Iterable<QuestionnaireEntity> findAllByStatus(PublicationStatus status);

    public Iterable<QuestionnaireEntity> findAllByStatusNot(PublicationStatus status);
}

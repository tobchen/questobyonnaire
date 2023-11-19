package de.tobchen.health.questobyonnaire.fhir.repositories;

import java.util.Optional;

import org.hl7.fhir.r5.model.Enumerations.PublicationStatus;
import org.springframework.data.repository.Repository;

import de.tobchen.health.questobyonnaire.fhir.entities.QuestionnaireEntity;

public interface QuestionnaireRepository extends Repository<QuestionnaireEntity, Long> {
    QuestionnaireEntity save(QuestionnaireEntity entity);

    Optional<QuestionnaireEntity> findById(Long id);

    Iterable<QuestionnaireEntity> findAll();

    public Iterable<QuestionnaireEntity> findAllByStatus(PublicationStatus status);

    public Iterable<QuestionnaireEntity> findAllByStatusNot(PublicationStatus status);
}

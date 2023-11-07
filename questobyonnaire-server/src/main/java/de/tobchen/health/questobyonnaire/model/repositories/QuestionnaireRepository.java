package de.tobchen.health.questobyonnaire.model.repositories;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import de.tobchen.health.questobyonnaire.model.deserialized.DeserializedQuestionnaireStatus;
import de.tobchen.health.questobyonnaire.model.entities.QuestionnaireEntity;

public interface QuestionnaireRepository extends Repository<QuestionnaireEntity, Long> {
    QuestionnaireEntity save(QuestionnaireEntity entity);

    Optional<QuestionnaireEntity> findById(Long id);

    Iterable<QuestionnaireEntity> findAll();

    public Iterable<QuestionnaireEntity> findAllByStatus(DeserializedQuestionnaireStatus status);

    public Iterable<QuestionnaireEntity> findAllByStatusNot(DeserializedQuestionnaireStatus status);
}

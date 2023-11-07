package de.tobchen.health.questobyonnaire.rest.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.tobchen.health.questobyonnaire.model.deserialized.DeserializedQuestionnaire;
import de.tobchen.health.questobyonnaire.model.deserialized.DeserializedQuestionnaireStatus;
import de.tobchen.health.questobyonnaire.model.entities.QuestionnaireEntity;
import de.tobchen.health.questobyonnaire.model.repositories.QuestionnaireRepository;
import de.tobchen.health.questobyonnaire.rest.exceptions.IllegalIdException;
import de.tobchen.health.questobyonnaire.rest.exceptions.IllegalSearchArgumentException;
import de.tobchen.health.questobyonnaire.rest.exceptions.IllegalSearchParameterException;
import de.tobchen.health.questobyonnaire.rest.exceptions.JsonSerializationException;
import de.tobchen.health.questobyonnaire.rest.exceptions.EntityNotFoundException;

@RestController
@RequestMapping("/rest/questionnaires")
public class QuestionnaireController {
    private final ObjectMapper mapper = new ObjectMapper();
    private final QuestionnaireRepository repository;

    public QuestionnaireController(QuestionnaireRepository repository)
    {
        this.repository = repository;
    }

    @GetMapping("")
    public List<DeserializedQuestionnaire> search(
        @RequestParam(name = "status", required = false) String status,
        @RequestParam(name = "status:not", required = false) String statusNot)
    {
        Iterable<QuestionnaireEntity> entities;

        if (status != null && statusNot != null)
        {
            throw new IllegalSearchParameterException("Cannot have both status and status:not");
        }
        else if (status != null)
        {
            try {
                var deserializedStatus = mapper.readValue("\"" + status + "\"",
                    DeserializedQuestionnaireStatus.class);
                entities = repository.findAllByStatus(deserializedStatus);
            } catch (JsonProcessingException e)
            {
                throw new IllegalSearchArgumentException("Unknown status");
            }
        }
        else if (statusNot != null)
        {
            try {
                var deserializedStatus = mapper.readValue("\"" + statusNot + "\"",
                    DeserializedQuestionnaireStatus.class);
                entities = repository.findAllByStatusNot(deserializedStatus);
            } catch (JsonProcessingException e)
            {
                throw new IllegalSearchArgumentException("Unknown status");
            }
        }
        else
        {
            entities = repository.findAll();
        }

        var questionnaires = new ArrayList<DeserializedQuestionnaire>();

        for (var entity : entities)
        {
            var deserialized = getDeserializedFromEntity(entity);
            if (deserialized != null)
            {
                questionnaires.add(deserialized);
            }
        }
        
        return questionnaires;
    }

    @GetMapping("/{id}")
    public DeserializedQuestionnaire read(@PathVariable Long id)
    {
        DeserializedQuestionnaire questionnaire = null;

        var entity = repository.findById(id);
        if (entity.isPresent())
        {
            questionnaire = getDeserializedFromEntity(entity.get());
        }

        return questionnaire;
    }

    @PostMapping("")
    public DeserializedQuestionnaire create(@RequestBody DeserializedQuestionnaire questionnaire)
    {
        if (questionnaire.id() != null)
        {
            questionnaire = new DeserializedQuestionnaire(null, questionnaire.title(),
                questionnaire.description(), questionnaire.status(), questionnaire.items());
        }

        String serialized;
        try {
            serialized = mapper.writeValueAsString(questionnaire);
        } catch (JsonProcessingException e) {
            throw new JsonSerializationException("Cannot re-serialize questionnaire");
        }

        var entity = new QuestionnaireEntity(questionnaire.status(), serialized);
        entity = repository.save(entity);

        questionnaire = new DeserializedQuestionnaire(entity.getId(), questionnaire.title(),
            questionnaire.description(), questionnaire.status(), questionnaire.items());
        
        try {
            serialized = mapper.writeValueAsString(questionnaire);
        } catch (JsonProcessingException e) {
            // TODO That shouldn't have happened...
        }

        entity.setSerialized(serialized);
        repository.save(entity);

        return questionnaire;
    }

    @PutMapping("/{id}")
    public DeserializedQuestionnaire update(@RequestBody DeserializedQuestionnaire questionnaire,
        @PathVariable Long id)
    {
        if (id == null || questionnaire.id() == null || !questionnaire.id().equals(id))
        {
            throw new IllegalIdException("Bad id");
        }

        var optionalEntity = repository.findById(id);
        if (!optionalEntity.isPresent())
        {
            throw new EntityNotFoundException();
        }

        var entity = optionalEntity.get();

        // TODO Check status change for validity

        if (entity.getStatus().equals(DeserializedQuestionnaireStatus.DRAFT))
        {
            String serialized;
            try {
                serialized = mapper.writeValueAsString(questionnaire);
            } catch (JsonProcessingException e) {
                throw new JsonSerializationException("Cannot re-serialize questionnaire");
            }

            entity.setSerialized(serialized);
        }

        entity.setStatus(questionnaire.status());

        repository.save(entity);

        return questionnaire;
    }

    private DeserializedQuestionnaire getDeserializedFromEntity(QuestionnaireEntity entity)
    {
        try {
            return mapper.readValue(entity.getSerialized(), DeserializedQuestionnaire.class);
        }
        catch (JsonProcessingException e)
        {
            return null;
        }
    }
}
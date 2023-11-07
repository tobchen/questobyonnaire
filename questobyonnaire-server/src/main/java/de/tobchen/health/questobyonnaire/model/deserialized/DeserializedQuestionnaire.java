package de.tobchen.health.questobyonnaire.model.deserialized;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DeserializedQuestionnaire(
    Long id,
    @JsonProperty(required = true)
    String title,
    @JsonProperty(defaultValue = "")
    String description,
    @JsonProperty(required = true)
    DeserializedQuestionnaireStatus status,
    @JsonProperty(defaultValue = "[]")
    DeserializedQuestionnaireItem[] items
)
{ }

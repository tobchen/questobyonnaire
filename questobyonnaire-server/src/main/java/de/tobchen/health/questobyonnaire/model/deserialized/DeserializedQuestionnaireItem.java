package de.tobchen.health.questobyonnaire.model.deserialized;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DeserializedQuestionnaireItem(
    @JsonProperty(required = true)
    String text,
    @JsonProperty(required = true)
    DeserializedQuestionnaireItemType type,
    @JsonProperty(defaultValue = "false")
    boolean required,
    @JsonProperty(defaultValue = "false")
    boolean multiple,
    @JsonProperty(defaultValue = "")
    String options
)
{ }

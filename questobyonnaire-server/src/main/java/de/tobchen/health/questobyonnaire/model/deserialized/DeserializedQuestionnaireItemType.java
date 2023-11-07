package de.tobchen.health.questobyonnaire.model.deserialized;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum DeserializedQuestionnaireItemType {
    @JsonProperty("boolean")
    BOOLEAN,
    @JsonProperty("decimal")
    DECIMAL,
    @JsonProperty("integer")
    INTEGER,
    @JsonProperty("date")
    DATE,
    @JsonProperty("dateTime")
    DATE_TIME,
    @JsonProperty("time")
    TIME,
    @JsonProperty("text")
    TEXT,
    @JsonProperty("longText")
    LONG_TEXT,
    @JsonProperty("choice")
    CHOICE,
    @JsonProperty("openChoice")
    OPEN_CHOICE,
    @JsonProperty("attachment")
    ATTACHMENT,
}

package de.tobchen.health.questobyonnaire.model.deserialized;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum DeserializedQuestionnaireStatus {
    @JsonProperty("draft")
    DRAFT,
    @JsonProperty("published")
    PUBLISHED,
    @JsonProperty("retired")
    RETIRED,
}

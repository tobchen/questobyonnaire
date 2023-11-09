package de.tobchen.health.questobyonnaire.model.deserialized;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum DeserializedQuestionnaireStatus {
    @JsonProperty("draft")
    DRAFT("draft"),
    @JsonProperty("published")
    PUBLISHED("published"),
    @JsonProperty("retired")
    RETIRED("retired");

    public final String value;

    private DeserializedQuestionnaireStatus(String value)
    {
        this.value = value;
    }

    public static DeserializedQuestionnaireStatus fromString(String str)
    {
        for (var status : values())
        {
            if (status.value.equals(str))
            {
                return status;
            }
        }

        return null;
    }
}

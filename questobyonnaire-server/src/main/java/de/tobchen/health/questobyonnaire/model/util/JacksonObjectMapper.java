package de.tobchen.health.questobyonnaire.model.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

public abstract class JacksonObjectMapper {
    private static final ObjectMapper mapper = new ObjectMapper();

    private static ObjectWriter writer = null;
    private static ObjectReader reader = null;

    public static ObjectWriter getWriter()
    {
        if (writer == null)
        {
            writer = mapper.writer();
        }

        return mapper.writer();
    }

    public static ObjectReader getReader()
    {
        if (reader == null)
        {
            reader = mapper.reader();
        }

        return mapper.reader();
    }
}

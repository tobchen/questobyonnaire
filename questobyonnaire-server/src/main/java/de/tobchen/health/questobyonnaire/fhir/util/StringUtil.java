package de.tobchen.health.questobyonnaire.fhir.util;

public abstract class StringUtil {
    public static String searchable(String str)
    {
        // TODO Turn all white spaces into simple spaces
        // TODO Remove accents and stuff

        return str.toLowerCase();
    }
}

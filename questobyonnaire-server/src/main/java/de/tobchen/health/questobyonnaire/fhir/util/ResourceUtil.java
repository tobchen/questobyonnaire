package de.tobchen.health.questobyonnaire.fhir.util;

import org.hl7.fhir.r4.model.Resource;

public abstract class ResourceUtil {
    public static void removeMeta(Resource resource)
    {
        var meta = resource.getMeta();
        if (meta != null)
        {
            meta.setVersionIdElement(null);
            meta.setLastUpdatedElement(null);
        }
    }
}

package de.tobchen.health.questobyonnaire.fhir.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;

@Configuration
public class FhirParserConfiguration
{
    @Bean
    public IParser fhirParser(FhirContext context)
    {
        return context.newJsonParser();
    }
}

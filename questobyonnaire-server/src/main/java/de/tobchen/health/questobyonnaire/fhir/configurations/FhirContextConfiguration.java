package de.tobchen.health.questobyonnaire.fhir.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ca.uhn.fhir.context.FhirContext;

@Configuration
public class FhirContextConfiguration {
    @Bean
	public FhirContext fhirContext()
	{
		return FhirContext.forR5();
	}
}

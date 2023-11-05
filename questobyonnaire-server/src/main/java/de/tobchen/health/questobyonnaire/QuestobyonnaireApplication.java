package de.tobchen.health.questobyonnaire;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

import ca.uhn.fhir.context.FhirContext;

@ServletComponentScan
@SpringBootApplication
public class QuestobyonnaireApplication
{

	public static void main(String[] args)
	{
		SpringApplication.run(QuestobyonnaireApplication.class, args);
	}

	@Bean
	public FhirContext fhirContext()
	{
		return FhirContext.forR4();
	}

}

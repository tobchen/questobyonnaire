package de.tobchen.health.questobyonnaire.fhir;

import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import org.springframework.web.cors.CorsConfiguration;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.server.RestfulServer;
import ca.uhn.fhir.rest.server.interceptor.CorsInterceptor;
import de.tobchen.health.questobyonnaire.fhir.providers.AbstractQuestionnaireProvider;

@WebServlet(urlPatterns = "/fhir/*")
public class FhirServlet extends RestfulServer
{
    private final AbstractQuestionnaireProvider questionnaireProvider;

    public FhirServlet(FhirContext context, AbstractQuestionnaireProvider questionnaireProvider)
    {
        super(context);

        this.questionnaireProvider = questionnaireProvider;
    }

    @Override
    protected void initialize() throws ServletException
    {
        setResourceProviders(questionnaireProvider);

        var corsConfig = new CorsConfiguration();

        corsConfig.addAllowedHeader("Origin");
        corsConfig.addAllowedHeader("Accept");
        corsConfig.addAllowedHeader("X-Requested-With");
        corsConfig.addAllowedHeader("Content-Type");

        corsConfig.addAllowedOrigin("*");

        corsConfig.addExposedHeader("Location");
        corsConfig.addExposedHeader("Content-Location");
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS"));

        var corsInterceptor = new CorsInterceptor(corsConfig);

        registerInterceptor(corsInterceptor);
    }
}

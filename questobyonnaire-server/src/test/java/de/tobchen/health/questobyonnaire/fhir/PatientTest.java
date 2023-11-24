package de.tobchen.health.questobyonnaire.fhir;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.hl7.fhir.r5.model.IdType;
import org.hl7.fhir.r5.model.Patient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import de.tobchen.health.questobyonnaire.fhir.providers.PatientProvider;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PatientTest
{
    @Autowired
    private PatientProvider provider;

    @Test
    void givenPatient_whenCreateWithNameAndFind_thenSuccess()
    {
        var familyName = "Albrecht";
        var givenName = "Alexandra";

        var patient = new Patient();
        patient.addName().setFamily(familyName).addGiven(givenName);

        var outcome = provider.create(patient);
        assertTrue(outcome.getCreated());

        var idLong = outcome.getId().getIdPartAsLong();
        var id = new IdType(idLong);

        var foundPatient = provider.read(id);
        assertNotNull(foundPatient);
        assertEquals(foundPatient.getIdElement().getIdPartAsLong(), idLong);

        var names = foundPatient.getName();
        assertNotNull(names);
        assertEquals(names.size(), 1);

        var name = names.get(0);
        assertNotNull(name);
        assertEquals(name.getFamily(), familyName);
        assertEquals(name.getGivenAsSingleString(), givenName);
    }
}

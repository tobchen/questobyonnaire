package de.tobchen.health.questobyonnaire.fhir.repositories;

import java.sql.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import de.tobchen.health.questobyonnaire.fhir.entities.PatientEntity;

public interface PatientRepository extends Repository<PatientEntity, Long> {
    PatientEntity save(PatientEntity entity);

    Optional<PatientEntity> findById(Long id);

    Iterable<PatientEntity> findAll();

    Optional<PatientEntity> findByMrnAndMrnSystem(String mrn, String mrnSystem);

    @Query("select p from PatientEntity p where (:family is null or p.familyName like concat(:family, '%'))"
        + " and (:given is null or p.givenName like concat(:given, '%'))"
        + " and (:birth is null or p.birthDate = :birth)")
    Iterable<PatientEntity> findAllByFamilyNameAndGivenNameAndBirthDate(
        @Param("family") String familyName, @Param("given") String givenName, @Param("birth") Date birthDate);
}

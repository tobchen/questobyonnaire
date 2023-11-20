package de.tobchen.health.questobyonnaire.fhir.entities;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"mrn", "mrnSystem"})})
public class PatientEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String mrn;
    private String mrnSystem;

    private String familyName;
    private String givenName;

    private Date birthDate;

    @ManyToOne
    private PatientEntity mergedInto;

    @Lob
    private String serialized;

    protected PatientEntity() {}

    public PatientEntity(String mrn, String mrnSystem, String familyName, String givenName, Date birthDate,
        PatientEntity mergedInto, String serialized)
    {
        this.mrn = mrn;
        this.mrnSystem = mrnSystem;

        this.familyName = familyName;
        this.givenName = givenName;

        this.birthDate = birthDate;

        this.mergedInto = mergedInto;

        this.serialized = serialized;
    }

    public Long getId() {
        return id;
    }

    public String getMrn() {
        return mrn;
    }

    public void setMrn(String mrn) {
        this.mrn = mrn;
    }

    public String getMrnSystem() {
        return mrnSystem;
    }

    public void setMrnSystem(String mrnSystem) {
        this.mrnSystem = mrnSystem;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public PatientEntity getMergedInto() {
        return mergedInto;
    }

    public void setMergedInto(PatientEntity mergedInto) {
        this.mergedInto = mergedInto;
    }

    public String getSerialized() {
        return serialized;
    }

    public void setSerialized(String serialized) {
        this.serialized = serialized;
    }
}

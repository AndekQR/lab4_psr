package model;

import java.io.Serializable;
import java.util.List;

public class Doctor implements Serializable {
    public Doctor(String name, String lastname, DoctorSpecializations specialization, List<Patient> patients, Double salary) {
        this.name=name;
        this.lastname=lastname;
        this.specialization=specialization;
        this.patients=patients;
        this.salary=salary;
    }

    public Doctor(){}

    private String name;
    private String lastname;
    private DoctorSpecializations specialization;
    private List<Patient> patients;
    private Double salary;

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients=patients;
    }

    public void addPatient(Patient patient) {
        this.patients.add(patient);
    }

    public DoctorSpecializations getSpecialization() {
        return specialization;
    }

    public void setSpecialization(DoctorSpecializations specialization) {
        this.specialization=specialization;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname=lastname;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary=salary;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", specialization=" + specialization +
                ", patients=" + patients +
                ", salary=" + salary +
                '}';
    }
}

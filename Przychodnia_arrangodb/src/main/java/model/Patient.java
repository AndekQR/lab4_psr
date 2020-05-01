package model;

import java.io.Serializable;

public class Patient implements Serializable {

    public Patient(String name, String lastname, HealthCard healthCard) {
        this.name=name;
        this.lastname=lastname;
        this.healthCard=healthCard;
    }

    public Patient(){}

    private String name;
    private String lastname;
    private HealthCard healthCard;

    public HealthCard getHealthCard() {
        return healthCard;
    }

    public void setHealthCard(HealthCard healthCard) {
        this.healthCard=healthCard;
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

    @Override
    public String toString() {
        return "Patient{" +
                "name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", healthCard=" + healthCard +
                '}';
    }
}

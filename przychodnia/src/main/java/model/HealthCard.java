package model;

import java.io.Serializable;

public class HealthCard implements Serializable {

    private Double weight;
    private Double growth;
    private int birthyear;

    public HealthCard(Double weight, Double growth, int birthyear) {
        this.weight=weight;
        this.growth=growth;
        this.birthyear=birthyear;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight=weight;
    }

    public Double getGrowth() {
        return growth;
    }

    public void setGrowth(Double growth) {
        this.growth=growth;
    }


    public int getBirthyear() {
        return birthyear;
    }

    public void setBirthyear(int birthyear) {
        this.birthyear=birthyear;
    }

    @Override
    public String toString() {
        return "HealthCard{" +
                "weight=" + weight +
                ", growth=" + growth +
                ", birthyear=" + birthyear +
                '}';
    }
}

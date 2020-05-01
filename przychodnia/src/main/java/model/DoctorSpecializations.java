package model;

public enum DoctorSpecializations {

    audiolog("Audilog"),
    chirurg("Chirurg"),
    dermatolog("Dermatolog"),
    okulista("Okulista"),
    kardiolog("Kardiolog");

    private String name;

    DoctorSpecializations(String name) {
        this.name=name;
    }
}

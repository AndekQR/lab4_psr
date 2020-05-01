import model.Doctor;
import model.DoctorSpecializations;
import model.HealthCard;
import model.Patient;

import java.util.ArrayList;
import java.util.List;

public class ExampleData {
    public static Doctor dcotor1() {

        HealthCard healthCard11=new HealthCard(65.0, 179.2, 2000);
        HealthCard healthCard12=new HealthCard(63.0, 179.2, 1996);
        HealthCard healthCard13=new HealthCard(101.0, 150.0, 1990);
        Patient patient11=new Patient("Zbigniew", "Duda", healthCard11);
        Patient patient12=new Patient("Władyswał", "Paciorek", healthCard12);
        Patient patient13=new Patient("Adam", "Adamowicz", healthCard13);
        List<Patient> patients=new ArrayList<>();
        patients.add(patient11);
        patients.add(patient12);
        patients.add(patient13);
        Doctor doctor1=new Doctor("Jan", "Kowalski", DoctorSpecializations.audiolog, patients, 3500D);
        return doctor1;
    }

    public static Doctor dcotor2() {

        HealthCard healthCard11=new HealthCard(65.0, 179.2, 2001);
        HealthCard healthCard12=new HealthCard(70.0, 179.2, 1996);
        HealthCard healthCard13=new HealthCard(101.0, 153.0, 1990);
        Patient patient11=new Patient("Zbigniew", "Stonoga", healthCard11);
        Patient patient12=new Patient("Adam", "Paciorek", healthCard12);
        Patient patient13=new Patient("Adam", "Kowalski", healthCard13);
        List<Patient> patients=new ArrayList<>();
        patients.add(patient11);
        patients.add(patient12);
        patients.add(patient13);
        Doctor doctor1=new Doctor("Dawid", "Macierewicz", DoctorSpecializations.dermatolog, patients, 3300D);
        return doctor1;
    }

    public static Doctor dcotor3() {

        HealthCard healthCard11=new HealthCard(65.0, 179.2, 2000);
        HealthCard healthCard12=new HealthCard(63.0, 179.2, 1996);
        HealthCard healthCard13=new HealthCard(101.0, 150.0, 1990);
        Patient patient11=new Patient("Iza", "Jabłońska", healthCard11);
        Patient patient12=new Patient("Władyswał", "Kaczyński", healthCard12);
        Patient patient13=new Patient("Adam", "Terlecki", healthCard13);
        List<Patient> patients=new ArrayList<>();
        patients.add(patient11);
        patients.add(patient12);
        patients.add(patient13);
        Doctor doctor1=new Doctor("Paweł", "Ryłko", DoctorSpecializations.kardiolog, patients, 2500D);
        return doctor1;
    }


    public static Doctor dcotor4() {

        List<Patient> patients=new ArrayList<>();
        Doctor doctor4=new Doctor("Janusz", "Majewski", DoctorSpecializations.audiolog, patients, 2500D);
        return doctor4;
    }

    public static Patient patient1() {
        HealthCard healthCard = new HealthCard(65D, 172D, 1996);
        return new Patient("Paweł", "Kowalski", healthCard);
    }
}

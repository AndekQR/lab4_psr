import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import model.Doctor;
import model.DoctorSpecializations;
import model.HealthCard;
import model.Patient;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class EntryPoint {


    public static void main(String[] args) {
        ArangoDB arangoDB=new ArangoDB.Builder().user("root").password("root").build();
        DoctorsService doctorsService=new DoctorsService(arangoDB);

        Scanner scanner=new Scanner(System.in);
        int choose;

        while (true) {
            System.out.println("[0] - usuń doktora\n[1] - pokaż doktora (id)\n[2] - dodaj pacjenta \n[3] - zwiększ pensje doktorom \n[4] - wprowadź losowych doktorów \n[5] - szukaj doktorów według specjalizacji \n[6] - znajdź doktora po imieniu i nazwisku \n[7] - wyświetl pacjentów doktora");
            choose=scanner.nextInt();
            scanner.nextLine();
            switch (choose) {
                case 0: {
                    System.out.println("Podaj id doktora");
                    try {
                        doctorsService.removeDoctor(scanner.nextLine());
                    } catch (Exception e) {
                        System.out.println("Usuwanie nieudane " + e.getMessage());
                    }
                    break;
                }
                case 1: {
                    try {

                        Doctor doctor=doctorsService.getDoctorById(scanner.nextLine());
                        System.out.println(doctor);
                    } catch (ArangoDBException e) {
                        System.out.println("Błąd: " + e.getMessage());
                    }
                    break;
                }
                case 2: {
                    HealthCard healthCard=new HealthCard(50.0, 140.0, 2001);
                    Patient patient=new Patient("Jan", "Polański", healthCard);
                    try {
                        doctorsService.addPatient(scanner.nextLine(), patient);
                    } catch (Exception e) {
                        System.out.println("Błąd: " + e.getMessage());
                    }
                    break;
                }
                case 3: {
                    System.out.println("O ile?");
                    doctorsService.increaseDoctorsSalary(scanner.nextDouble());
                    break;
                }
                case 4: {
                    doctorsService.addDoctor(ExampleData.dcotor1());
                    doctorsService.addDoctor(ExampleData.dcotor2());
                    doctorsService.addDoctor(ExampleData.dcotor3());
                    doctorsService.addDoctor(ExampleData.dcotor4());
                    break;
                }
                case 5: {
                    for (DoctorSpecializations value : DoctorSpecializations.values()) {
                        System.out.println(value);
                    }
                    System.out.println("Nazwa specjalizacji: ");
                    String name = scanner.nextLine();
                    try {
                        DoctorSpecializations doctorSpecializations=DoctorSpecializations.valueOf(name);
                        List<Doctor> doctors=doctorsService.findDoctorsBySpecialization(doctorSpecializations);
                        doctors.forEach(System.out::println);

                    } catch (IllegalArgumentException e) {
                        System.out.println("Nieprawidłowa nazwa specjalizaji");
                    }
                    break;
                }
                case 6: {
                    System.out.println("Imię: ");
                    String name=scanner.nextLine();
                    System.out.println("Nazwisko: ");
                    String lastname=scanner.nextLine();
                    Doctor doctor=doctorsService.getDoctor(name, lastname, Doctor.class);
                    System.out.println(doctor);
                    break;
                }
                case 7: {
                    System.out.println("Podaj id doktora: ");
                    List<Patient> patients=doctorsService.getDoctorPatientsById(scanner.nextLine());
                    patients.forEach(System.out::println);
                    break;
                }
                default: {
                    System.out.println("Nieprawidłowa akcja");
                    break;
                }
            }
        }
    }
}

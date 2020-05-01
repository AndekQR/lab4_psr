import model.Doctor;
import model.HealthCard;
import model.Patient;

import java.net.UnknownHostException;
import java.util.Optional;
import java.util.Scanner;

public class HEntryPoint {

    public static void main(String[] args) throws UnknownHostException {

        HMember hMember=new HMember();
        HClient hClient=new HClient();
        Scanner scanner=new Scanner(System.in);
        int choose;

        while (true) {
            System.out.println("[0] - usuń doktora\n[1] - pokaż doktora (id)\n[2] - dodaj pacjenta \n[3] - zwiększ pensje doktorom \n[4] - wprowadź losowych doktorów \n[5] - pokaż wszystkich doktorów \n[6] - znajdź doktora po imieniu i nazwisku");
            choose=scanner.nextInt();
            switch (choose) {
                case 0: {
                    System.out.println("Podaj id doktora");
                    hClient.removeDoctorById(scanner.nextLong());
                    break;
                }
                case 1: {
                    Optional<Doctor> doctor=hClient.getDoctorById(scanner.nextLong());
                    if (doctor.isPresent()) {
                        System.out.println(doctor);
                    } else {
                        System.out.println("brak doktora o podanym id");
                    }
                    break;
                }
                case 2: {
                    HealthCard healthCard=new HealthCard(50.0, 140.0, 2001);
                    Patient patient=new Patient("Jan", "Polański", healthCard);
                    Long id=scanner.nextLong();
                    Optional<Doctor> doctor=hClient.getDoctorById(id);
                    if (doctor.isPresent()) {
                        hClient.addPatient(id, patient);
                        System.out.println("Dodano " + patient.getName() + " " + patient.getLastname() + " do " + doctor.get().getLastname());
                    } else {
                        System.out.println("Błąd!");
                    }
                    break;
                }
                case 3: {
                    System.out.println("O ile?");
                    hClient.increaseDoctorsSalary(scanner.nextDouble());
                    break;
                }
                case 4: {
                    hMember.addDoctor(ExampleData.dcotor1());
                    hMember.addDoctor(ExampleData.dcotor2());
                    hMember.addDoctor(ExampleData.dcotor3());
                    break;
                }
                case 5: {
                    hClient.printAllDoctors();
                    break;
                }
                case 6: {
                    scanner.nextLine(); //\n
                    System.out.println("Imię: ");
                    String name = scanner.nextLine();
                    System.out.println("Nazwisko: ");
                    String lastname = scanner.nextLine();
                    Optional<Doctor> doctor = hClient.getDoctor(name, lastname);
                    if (doctor.isPresent()) {
                        System.out.println(doctor.get());
                    } else {
                        System.out.println("Brak doktora");
                    }
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

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.EntryProcessor;
import com.hazelcast.map.IMap;
import com.hazelcast.map.listener.EntryAddedListener;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.Predicates;
import model.Doctor;
import model.Patient;

import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Optional;

public class HClient {

    private HazelcastInstance client;

    public HClient() throws UnknownHostException {
        ClientConfig clientConfig=HConfig.getClientConfig();
        this.client=HazelcastClient.newHazelcastClient(clientConfig);
        this.addEntryListener();
    }

    public Optional<Doctor> getDoctor(String name, String lastname) {
        IMap<Long, Doctor> doctors=client.getMap("doctors");

        Predicate<Long, Doctor> namePredicate=Predicates.equal("name", name);
        Predicate<Long, Doctor> lastnamePredicate=Predicates.equal("lastname", lastname);
        Collection<Doctor> values=doctors.values(Predicates.and(namePredicate, lastnamePredicate));

        return values.stream().findFirst();
    }

    public void removeDoctorById(Long id) {
        Doctor removedDoctor=(Doctor) client.getMap("doctors").remove(id);

        //przeniesienie pacjentów
        IMap<Long, Doctor> doctors=client.getMap("doctors");
        for (int i=0; i < removedDoctor.getPatients().size(); i++) {
            for (Long doctorId : doctors.keySet()) {
                addPatient(doctorId, removedDoctor.getPatients().get(i));
                i++;

            }
        }
    }

    public void addPatient(Long doctorId, Patient patient) {
        IMap<Long, Doctor> doctors=client.getMap("doctors");

        Doctor doctor=doctors.get(doctorId);
        doctor.addPatient(patient);
        doctors.set(doctorId, doctor);

    }

    public void increaseDoctorsSalary(Double raise) {
        IMap<Long, Doctor> doctors=client.getMap("doctors");
        doctors.executeOnEntries((EntryProcessor<Long, Doctor, String>) entry -> {
            Doctor doctor=entry.getValue();
            doctor.setSalary(doctor.getSalary() + raise);
            entry.setValue(doctor);
            return doctor.getName() + " " + doctor.getLastname() + ": " + doctor.getSalary();
        });
    }

    public void printAllDoctors() {
        IMap<Long, Doctor> doctors=client.getMap("doctors");
        doctors.forEach((aLong, doctor) -> {
            System.out.println(aLong + ". " + doctor);
        });
    }

    private void addEntryListener() {
        IMap<Long, Doctor> clinics=client.getMap("clinics");
        clinics.addEntryListener((EntryAddedListener<Long, Doctor>) e -> {
            System.out.println(e);
        }, true);
    }

    public Optional<Doctor> getDoctorById(Long id) {
        Doctor doctor=(Doctor) client.getMap("doctors").get(id);
        return Optional.of(doctor);
    }

}

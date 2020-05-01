import com.arangodb.*;
import com.arangodb.entity.BaseDocument;
import com.arangodb.model.AqlQueryOptions;
import com.arangodb.util.MapBuilder;
import model.Doctor;
import model.DoctorSpecializations;
import model.Patient;
import sun.security.util.ArrayUtil;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DoctorsService {

    private ArangoDatabase db;
    private ArangoCollection doctorsCollection;
    private final String dbname="clinicDB";
    private final String doctorsCollectionName="doctors";

    public DoctorsService(final ArangoDB arangoDB) {
        if (!arangoDB.db(dbname).exists()) {
            arangoDB.createDatabase(dbname);
        }
        db=arangoDB.db(dbname);
        doctorsCollection=this.getDoctorsCollection();
    }

    private ArangoCollection getDoctorsCollection() {
        ArangoCollection col=db.collection(doctorsCollectionName);
        if (col.exists()) {
            return col;
        } else {
            db.createCollection(doctorsCollectionName);
            return db.collection(doctorsCollectionName);
        }
    }

    public <T> T getDoctor(String name, String lastname, Class<T> tClass) {
        ArangoCursor<T> cursor=db.query(
                "FOR i in " + doctorsCollectionName + " FILTER i.name == @name FILTER i.lastname == @lastname RETURN i",
                new MapBuilder().put("name", name).put("lastname", lastname).get(),
                new AqlQueryOptions(),
                tClass
        );
        if (cursor.hasNext()) return cursor.next();
        return null;
    }

    public Doctor getDoctorById(String id) throws ArangoDBException {
        return doctorsCollection.getDocument(id, Doctor.class);
    }

    public void addDoctor(Doctor doctor) {
        BaseDocument doctorInDb=this.getDoctor(doctor.getName(), doctor.getLastname(), BaseDocument.class);
        if (doctorInDb == null) {
            doctorsCollection.insertDocument(doctor);
        } else {
            doctorsCollection.replaceDocument(doctorInDb.getKey(), doctor);
        }
    }

    private String getDoctorKey(String name, String lastname) {
        ArangoCursor<String> cursor=db.query(
                "FOR i in " + doctorsCollectionName + " FILTER i.name == @name FILTER i.lastname == @lastname RETURN i._key",
                new MapBuilder().put("name", name).put("lastname", lastname).get(),
                new AqlQueryOptions(),
                String.class
        );
        if (cursor.hasNext()) return cursor.next();
        return null;
    }

    public void addPatient(String doctorKey, Patient patient) throws Exception {
        Doctor doctor=getDoctorById(doctorKey);

        if (doctor == null || doctorKey == null) throw new Exception("No such doctor");
        doctor.addPatient(patient);
        doctorsCollection.updateDocument(doctorKey, doctor);
    }

    public List<Patient> getDoctorPatients(String name, String lastname) {
        ArangoCursor<Patient[]> cursor=db.query(
                "FOR i in " + doctorsCollectionName + " FILTER i.name == @name FILTER i.lastname == @lastname RETURN i.patients",
                new MapBuilder().put("name", name).put("lastname", lastname).get(),
                new AqlQueryOptions(),
                Patient[].class
        );
        if (cursor.hasNext()) {
            return Arrays.asList(cursor.first());
        }
        return new ArrayList<>(0);
    }

    public List<Patient> getDoctorPatientsById(String id) {
        ArangoCursor<Patient[]> cursor=db.query(
                "FOR i in " + doctorsCollectionName + " FILTER i._key == @key RETURN i.patients",
                new MapBuilder().put("key", id).get(),
                new AqlQueryOptions(),
                Patient[].class
        );
        if (cursor.hasNext()) {
            return Arrays.asList(cursor.first());
        }
        return new ArrayList<>(0);
    }

    public List<Doctor> findDoctorsBySpecialization(DoctorSpecializations specialization) {
        ArangoCursor<Doctor> cursor=db.query(
                "FOR i in " + doctorsCollectionName + " FILTER i.specialization == @spec RETURN i",
                new MapBuilder().put("spec", specialization.toString()).get(),
                new AqlQueryOptions(),
                Doctor.class
        );
        ArrayList<Doctor> doctors=new ArrayList<>();
        for (; cursor.hasNext(); ) {
            doctors.add(cursor.next());
        }
        return doctors;
    }

    public void removeDoctor(String id) throws Exception {
        Doctor doctor=getDoctorById(id);
        if (doctor == null) return;
        List<Patient> patients=doctor.getPatients();

        List<Doctor> doctorsSameSpec=this.findDoctorsBySpecialization(doctor.getSpecialization());
        //usunięcie usuniętego doktora z kolekcji
        doctorsSameSpec.removeIf(doctor1 -> doctor1.getName().equals(doctor.getName()) && doctor1.getLastname().equals(doctor.getLastname()));
        if (doctorsSameSpec.isEmpty()) {
            ArangoCursor<String> cursor=db.query(
                    "FOR i in " + doctorsCollectionName + " LIMIT 1 RETURN i._key",
                    String.class
            );
            if (cursor.hasNext()) {
                String doctorKey=cursor.next();
                patients.forEach(patient -> {
                    try {
                        this.addPatient(doctorKey, patient);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                });
                doctorsCollection.deleteDocument(id);
            } else {
                throw new Exception("No doctors!");
            }
        } else {
            for (int i=0; i < patients.size(); ) {
                for (Doctor doc : doctorsSameSpec) {
                    this.addPatient(getDoctorKey(doc.getName(), doc.getLastname()), patients.get(i));
                    i++;
                }
            }
            doctorsCollection.deleteDocument(id);
        }
    }

    public void increaseDoctorsSalary(Double raise) {
        db.query("FOR doc in "+doctorsCollectionName+" UPDATE doc WITH { salary: doc.salary+@raise } IN "+doctorsCollectionName,
                new MapBuilder().put("raise", raise).get(),
                Object.class
                );
    }
}

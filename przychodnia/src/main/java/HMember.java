import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import model.Doctor;

import java.net.UnknownHostException;
import java.util.Map;
import java.util.Random;

public class HMember {

    private HazelcastInstance instance;
    final private static Random r=new Random(System.currentTimeMillis());

    public HMember() throws UnknownHostException {
        Config config=HConfig.getConfig();
        instance=Hazelcast.newHazelcastInstance(config);
    }

    public void addDoctor(Doctor doctor) throws IllegalArgumentException {
        Map<Long, Doctor> doctors=instance.getMap("doctors");
        if (doctors.isEmpty()) {
            Long key1=(long) Math.abs(r.nextInt());
            doctors.put(key1, doctor);
        } else {
            doctors.values().forEach(doc -> {
                if (doctor.getName().equals(doc.getName()) && doctor.getLastname().equals(doc.getLastname())) {
                    throw new IllegalArgumentException("Doctor " + doctor.getName() + " " + doctor.getLastname() + " already in database");
                } else {
                    Long key1=(long) Math.abs(r.nextInt());
                    doctors.put(key1, doctor);
                }
            });
        }
    }
}

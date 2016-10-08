package za.ac.kidsfountain.Entity;

import javax.persistence.*;

/**
 * Created by kidsfountain on 9/6/16.
 */
@Entity
@Table(name = "application")
public class Application {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String applicationStatus;
    private String registrationStatus;
    //*147#

    public Application() {
    }

    public long getId() {
        return id;
    }


    public Application(String applicationStatus, String registrationStatus) {
        this.applicationStatus = applicationStatus;
        this.registrationStatus = registrationStatus;
    }

    public String isApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public String isRegistrationStatus() {
        return registrationStatus;
    }

    public void setRegistrationStatus(String registrationStatus) {
        this.registrationStatus = registrationStatus;
    }
}

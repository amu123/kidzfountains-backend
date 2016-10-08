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
    private boolean applicationStatus;
    private boolean registrationStatus;
    //*147#

    public Application() {
    }

    public long getId() {
        return id;
    }


    public Application(boolean applicationStatus, boolean registrationStatus) {
        this.applicationStatus = applicationStatus;
        this.registrationStatus = registrationStatus;
    }

    public boolean isApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(boolean applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public boolean isRegistrationStatus() {
        return registrationStatus;
    }

    public void setRegistrationStatus(boolean registrationStatus) {
        this.registrationStatus = registrationStatus;
    }
}

package za.ac.kidsfountain.Entity;

import javax.persistence.*;

/**
 * Created by kidsfountain on 9/8/16.
 */
@Entity
@Table(name = "SuperAdmin")
public class SuperAdmin {
    @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private long id;

    private String password;
    private long adminNumber;

    public long getAdminNumber() {
        return adminNumber;
    }

    public void setAdminNumber(long adminNumber) {
        this.adminNumber = adminNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public SuperAdmin() {
    }

    public SuperAdmin(String password, long adminNumber) {
        this.password = password;
        this.adminNumber = adminNumber;
    }
}

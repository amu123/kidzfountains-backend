package za.ac.kidsfountain.Entity;

import javax.persistence.*;

/**
 * Created by kidsfountain on 8/20/16.
 */
@Entity
@Table(name="Admin")
public class Admin {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq_gen")
    @SequenceGenerator(name = "user_id", sequenceName = "user_id")
//    @Column(name="user_id")
    private long id;

    private String name;
    private String surname;
    private String idNumber;
    private String contact;
    private String address;
    private String email;
    private String password;
    private String staffNumber;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStaffNumber() {
        return staffNumber;
    }

    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public Admin() {
    }

    public Admin(String staffNumber, String password) {
        this.staffNumber = staffNumber;
        this.password = password;
    }

    public Admin(String name, String surname, String idNumber, String contact, String address, String email) {
        this.name = name;
        this.surname = surname;
        this.idNumber = idNumber;
        this.contact = contact;
        this.address = address;
        this.email = email;
    }

    public void setId(long id) {

        this.id = id;
    }
}

package za.ac.kidsfountain.Entity;

import javax.persistence.*;

/**
 * Created by kidsfountain on 8/12/16.
 */
@Entity
@Table(name="Users")
public class User {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="user_id")
    private long id;

    private String name;
    private String surname;
    private String idNumber;
    private String parentName;
    private String parentSurname;
    private String parentIdNumber;
    private String contact;
    private String address;
    private String email;
    private String applicationStatus;
    private String studentNumber;
    private String password;

    private boolean computerLessons,swimmingLessons,soccerLessons,dacingLessons;

    public boolean isComputerLessons() {
        return computerLessons;
    }

    public void setComputerLessons(boolean computerLessons) {
        this.computerLessons = computerLessons;
    }

    public boolean isSwimmingLessons() {
        return swimmingLessons;
    }

    public void setSwimmingLessons(boolean swimmingLessons) {
        this.swimmingLessons = swimmingLessons;
    }

    public boolean isSoccerLessons() {
        return soccerLessons;
    }

    public void setSoccerLessons(boolean soccerLessons) {
        this.soccerLessons = soccerLessons;
    }

    public boolean isDacingLessons() {
        return dacingLessons;
    }

    public void setDacingLessons(boolean dacingLessons) {
        this.dacingLessons = dacingLessons;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getParentIdNumber() {
        return parentIdNumber;
    }

    public void setParentIdNumber(String parentIdNumber) {
        this.parentIdNumber = parentIdNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Deprecated
    public long getId() {
        return id;
    }


    public User(String name, String surname, String idNumber, String parentName, String parentSurname, String address, String contactNumber,String parentIdNumber,String email) {
        this.name = name;
        this.surname = surname;
        this.idNumber = idNumber;
        this.parentName = parentName;
        this.parentSurname = parentSurname;
        this.address = address;
        this.contact = contactNumber;
        this.parentIdNumber = parentIdNumber;
        this.email = email;
    }

    public User() {
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentSurname() {
        return parentSurname;
    }

    public void setParentSurname(String parentSurname) {
        this.parentSurname = parentSurname;
    }



    public String getContactNumber() {
        return contact;
    }

    public void setContactNumber(String contactNumber) {
        this.contact = contactNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
}

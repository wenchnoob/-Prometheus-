package com.wench.prometheus.data.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * This class is intended to represent a user on the system.
 * This class is simply used as a JPA Entity to represent
 * the information of any persons using this application.
 *
 * @author Wenchy Dutreuil
 * @version 1
 * @since 2/1/2021
 * */
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @Column(unique = true)
    private String userName;

    private String firstName;

    private String lastName;

    private String passWord;

    private String email;


    /**
     * Default constructor
     * */
    public User() {}

    /**
     * Primary constructor used for making a user object.
     *
     * @param firstName The first name of the User.
     * @param lastName The last name of the User.
     * @param displayName  The User's display name.
     * @param userName The User's login name.
     * @param passWord The User's login password.
     * @param email The User's email (Intended for account recovery).
     */
    public User(String firstName, String lastName, String displayName,  String userName, String passWord, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.passWord = passWord;
        this.email = email;
    }

    // Setter Methods
    /*
    * These values are not intended to be modified after account creation
    * First Name
    * Last Name
    * User Name
    * */

    /**
     * This method is used to modify the user's nickname.
     * @param passWord
     * */
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    /**
     * This method is used to modify the user's email.
     * @param email
     * */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return Returns the users first name.
     * */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @return Returns the users last name.
     * */
    public String getLastName() {
        return lastName;
    }

    /**
     * @return Returns the username of the user.
     * */
    public String getUserName() {
        return userName;
    }

    /**
     * @return Returns the password of the user.
     * */
    public String getPassWord() {
        return passWord;
    }

    /**
     * @return Returns the email of the user.
     * */
    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

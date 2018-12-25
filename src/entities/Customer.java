/**
 * Copyright 2013 Mentor Graphics Corporation All Rights Reserved
 * THIS WORK CONTAINS TRADE SECRET AND PROPRIETARY INFORMATION WHICH IS THE PROPERTY OF MENTOR GRAPHICS
 * CORPORATION OR ITS LICENSORS AND IS SUBJECT TO LICENSE TERMS.
 */

package entities;

import javax.persistence.*;

/**
 * @author bbrownsh
 * @since 12/23/2018
 */
@Entity
@Table(name="Customers")
public class Customer {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="Customer_ID")
    private int customerID;

    @Column(name="First_name")
    private String firstName;

    @Column(name="Last_name")
    private String lastName;

    @Column(name="Mail")
    private String mailAddress;

    @Column(name="Phone")
    private String phoneNumber;


    public Customer() {
    }
    public Customer(String firstName, String lastName, String mailAddress, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mailAddress = mailAddress;
        this.phoneNumber = phoneNumber;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getMailAddress() {
        return mailAddress;
    }
    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public int getCustomerID() {
        return customerID;
    }
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
    public String toString(){
        return "Customer: ID: "+customerID+", First name: "+firstName+", last name: "+lastName+", phone: "+phoneNumber+", mail: "+mailAddress;
    }
}

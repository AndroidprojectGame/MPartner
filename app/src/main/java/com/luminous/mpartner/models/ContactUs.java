package com.luminous.mpartner.models;

public class ContactUs {

    public String ContactUsType, Address, email, Fax, PhoneNumber;


    public ContactUs(String contactUsType, String address, String email, String fax, String phoneNumber) {
        ContactUsType = contactUsType;
        Address = address;
        this.email = email;
        Fax = fax;
        PhoneNumber = phoneNumber;
    }
}

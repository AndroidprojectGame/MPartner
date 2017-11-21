package com.luminous.mpartner.models;


import java.io.Serializable;

public class Profile implements Serializable {

    public String id, Dealerid, Email, ContactNo, UserImage,address1, CustomerType, Dis_Sap_Code, Name;

    public Profile(String id, String dealerid, String email, String contactNo, String userImage,String Address1, String customerType, String dis_Sap_Code, String name) {
        this.id = id;
        Dealerid = dealerid;
        Email = email;
        ContactNo = contactNo;
        UserImage = userImage;
        address1 = Address1;
        CustomerType = customerType;
        Dis_Sap_Code = dis_Sap_Code;
        Name = name;
    }
}

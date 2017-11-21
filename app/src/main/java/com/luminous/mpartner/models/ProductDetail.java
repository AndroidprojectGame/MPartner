package com.luminous.mpartner.models;


import java.io.Serializable;

public class ProductDetail implements Serializable {

    public String id;
    public String LevelOneName;
    public String LevelTwoName;
   // public String LevelThreeName;
    public String ProductLevelThreeName;
    public String ProductCode;
    public String Image;
  //  public String FullFileName;
    public String MRP;
    public String Warranty;
    public String Keyfeature;
    public String Brochure;
   // public String Error;
    public String ProductDescriptions;

    public ProductDetail(String id, String levelOneName, String levelTwoName, String productLevelThreeName, String productCode, String ProductDescriptions,String mrp,String warranty,String keyfeature,String brochure, String image) {
        this.id = id;
        LevelOneName = levelOneName;
        LevelTwoName = levelTwoName;
       // LevelThreeName = levelThreeName;
        ProductLevelThreeName = productLevelThreeName;
        ProductCode = productCode;
        Image = image;
       // FullFileName = fullFileName;
        MRP = mrp;
        Warranty = warranty;
        Keyfeature = keyfeature;
        Brochure = brochure;
       // Error = error;
        this.ProductDescriptions = ProductDescriptions;
    }



}

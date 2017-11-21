package com.luminous.mpartner.models;


import java.io.Serializable;

public class Promotion implements Serializable {

    public String id, PromotionType, ProductCategory, ProductlevelOne, Descriptons, ImagePath;

    public Promotion(String id, String promotionType, String productCategory, String productlevelOne, String descriptons, String imagePath) {
        this.id = id;
        PromotionType = promotionType;
        ProductCategory = productCategory;
        ProductlevelOne = productlevelOne;
        Descriptons = descriptons;
        ImagePath = imagePath;
    }
}

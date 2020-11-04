package com.example.wastefree;


import java.util.Date;

public class Item {
    String category;
    String itemID;
    String itemPhoto;
    String itemDescription;
    public Date itemUploadDate;

    public Item(String category, String itemID, String itemPhoto, String itemDescription, Date itemUploadDate) {
        this.category = category;
        this.itemID = itemID;
        this.itemPhoto = itemPhoto;
        this.itemDescription = itemDescription;
        this.itemUploadDate = itemUploadDate;
    }

    public Item() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getItemPhoto() {
        return itemPhoto;
    }

    public void setItemPhoto(String itemPhoto) {
        this.itemPhoto = itemPhoto;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Date getItemUploadDate() {
        return itemUploadDate;
    }

    public void setItemUploadDate(Date itemUploadDate) {
        this.itemUploadDate = itemUploadDate;
    }
}

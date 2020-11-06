package com.example.wastefree;


import java.util.Date;

import static java.sql.Types.NULL;

public class Item {
    String category;
    String itemID;
    String itemPhoto;
    String itemDescription;
    String location;



    int rating;
    public Date itemUploadDate;


    public Item(String category, String itemID, String itemPhoto, String itemDescription,int rating, Date itemUploadDate, String location) {

        this.category = category;
        this.itemID = itemID;
        this.itemPhoto = itemPhoto;
        this.itemDescription = itemDescription;
        this.itemUploadDate = itemUploadDate;
        this.rating = rating;
        this.location = location;
    }

    public Item() {
    }

    public Item(String ItemId, String itemName, String quantity, int rating,String location) {
        this.category = itemName;
        this.itemID = ItemId;
        this.itemDescription = quantity;
        this.location = location;
        this.rating = rating;
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

    public void setLocation(String location) {
        location = location;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getLocation() {
        return location;
    }

    public int getRating() {
        if (this.rating != NULL){
            return rating;
        }
        else  {
            return 0;
        }
    }

    public String toString(){
        return this.itemID + "\n" + this.itemDescription;
    }

    public Date getItemUploadDate() {
        return itemUploadDate;
    }

    public void setItemUploadDate(Date itemUploadDate) {
        this.itemUploadDate = itemUploadDate;
    }
}

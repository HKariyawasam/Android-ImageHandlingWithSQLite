package com.example.uploadretrieveimage.data;

import android.graphics.Bitmap;

public class ModelClass {

    String imageName;
    Bitmap image;

    public ModelClass() {
    }

    public ModelClass(String imageName, Bitmap image) {
        this.imageName = imageName;
        this.image = image;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}

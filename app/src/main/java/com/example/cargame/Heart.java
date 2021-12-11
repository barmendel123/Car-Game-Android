package com.example.cargame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Heart {
    int height, width;
    Bitmap image;


    public Heart(Resources resources){
        this.image = BitmapFactory.decodeResource(resources, R.drawable.heart_icon);
        this.height = (int)image.getHeight()/10;
        this.width = (int)image.getWidth()/10;
        this.image = Bitmap.createScaledBitmap(this.image, this.width, this.height, false);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}

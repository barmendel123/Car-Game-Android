package com.example.cargame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Backround {
    int height, width;
    Bitmap image;

    public Backround(Resources resources){
        this.image = BitmapFactory.decodeResource(resources, R.drawable.backround);
        this.height = (int)image.getHeight();
        this.width = (int)image.getWidth();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Bitmap getImage() {
        return image;
    }
}

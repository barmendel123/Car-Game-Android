package com.example.cargame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Obstacle {
    int xLane, xPixel, yPixel, y;
    int height, width;
    Bitmap image;

    public Obstacle(int xPixel, int yPixel, int xLane, Resources resources ) {
        this.xPixel = xPixel;
        this.yPixel = yPixel;
        this.xLane =  xLane;
        this.image = BitmapFactory.decodeResource(resources, R.drawable.obstacle_icon);
        this.height = (int) this.image.getHeight() / 10;
        this.width = (int) this.image.getWidth() / 10;
        this.image = Bitmap.createScaledBitmap(this.image, this.width, this.height, false);
    }

    public Rect getRect(){
        return new Rect(getDrawX(), getDrawY(), getWidth() + getDrawX(), getDrawY() + getHeight());
    }

    public int getDrawX(){
        if(xLane == 0 )
            return xLane;

        else if(xLane == xPixel )
            return xLane- width;

        else
            return xLane-width/2;
    }


    public int getDrawY(){

        return y - height/2;
    }

    public int getxLane() {
        return xLane;
    }

    public void setxLane(int xLane) {
        this.xLane = xLane;
    }

    public int getxPixel() {
        return xPixel;
    }

    public void setxPixel(int xPixel) {
        this.xPixel = xPixel;
    }

    public int getyPixel() {
        return yPixel;
    }

    public void setyPixel(int yPixel) {
        this.yPixel = yPixel;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
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

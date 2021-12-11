package com.example.cargame;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Coin {
    int xLane, y, xPixels, yPixels, height, width;
    Bitmap image;

    public Coin(int xLane, int xPixels, int yPixels, Resources resources){
        this.xLane= xLane;
        this.xPixels = xPixels;
        this.yPixels = yPixels;
        this.image = BitmapFactory.decodeResource(resources, R.drawable.coin_icon);
        this.height = this.image.getHeight()/14;
        this.width = this.image.getWidth()/14;
        this.image = Bitmap.createScaledBitmap(this.image, this.width, this.height, false);
    }

    public int getDrawX(){
        if (xLane == 0)
            return xLane;
        else if(xLane == xPixels)
            return xLane - width;
        else
            return xLane - (width/2);
    }

    public int getDrawY(){
        return y-height/2;
    }

    public Rect getRect(){
        return new Rect(getDrawX(), getDrawY(), getDrawX() + width, getDrawY() + height);
    }

    public int getxLane() {
        return xLane;
    }

    public void setxLane(int xLane) {
        this.xLane = xLane;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getxPixels() {
        return xPixels;
    }

    public void setxPixels(int xPixels) {
        this.xPixels = xPixels;
    }

    public int getyPixels() {
        return yPixels;
    }

    public void setyPixels(int yPixels) {
        this.yPixels = yPixels;
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

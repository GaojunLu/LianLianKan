package com.example.lianliankan.view;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2015/9/18.
 */
public class Piece {
    //坐标、宽度、图片
    float X, Y, centerX, centerY;
    public static float width, height;
    int indexX, indexY;
    Bitmap bitmap;
    int bitmapId;

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getX() {
        return X;
    }

    public void setX(float x) {
        X = x;
    }

    public float getY() {
        return Y;
    }

    public void setY(float y) {
        Y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getCenterX() {
        centerX = X+width/2;
        return centerX;
    }

    public float getCenterY() {
        centerY = Y+height/2;
        return centerY;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getIndexY() {
        return indexY;
    }

    public void setIndexY(int indexY) {
        this.indexY = indexY;
    }

    public int getIndexX() {
        return indexX;
    }

    public void setIndexX(int indexX) {
        this.indexX = indexX;
    }

    public int getBitmapId() {
        return bitmapId;
    }

    public void setBitmapId(int bitmapId) {
        this.bitmapId = bitmapId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Piece piece = (Piece) o;

        return bitmapId == piece.bitmapId;

    }

    @Override
    public int hashCode() {
        return bitmapId;
    }
}

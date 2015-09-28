package com.example.lianliankan.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.lianliankan.R;
import com.example.lianliankan.view.GameView;
import com.example.lianliankan.view.Piece;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2015/9/17.
 */
public class ImageUtil {
    //获取所有drawable的p_开头图片id
    public static List<Integer> getImageIDs() {
        List<Integer> IDs = new ArrayList<Integer>();
        try {
            Field[] allIDs = R.drawable.class.getFields();
            for (Field field : allIDs) {
                if (field.getName().indexOf("p_") != -1) {
                    IDs.add(field.getInt(R.drawable.class));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return IDs;
    }

    //从p_图片中随机获取size张图片id
    public static List<Integer> getRandomImageIDs(int size) {
        List<Integer> randomIDs = new ArrayList<Integer>();
        if (size % 2 != 0) {
            Log.e("ImageUtil", "随机图片数需为偶数");
            return null;
        }
        List<Integer> IDs = getImageIDs();
        if (IDs.size() != 0) {
            Random random = new Random();
            int index;
            for (int i = 0; i < size / 2; i++) {
                index = random.nextInt(IDs.size());
                Integer integer = IDs.get(index);
                randomIDs.add(integer);
                randomIDs.add(integer);
            }
            Collections.shuffle(randomIDs);
        }
        return randomIDs;
    }

    //获取随机图片
    public static BitmapAndId getBitmap(Context context, int x, int y) {
        BitmapAndId bitmapAndId = new BitmapAndId();
        Bitmap[][] bitmaps = new Bitmap[x][y];
        List<Integer> bitmapIDs = getRandomImageIDs(x * y);
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                bitmaps[i][j] = BitmapFactory.decodeResource(context.getResources(), bitmapIDs.get(x * j + i).intValue());
            }
        }
        bitmapAndId.bitmaps = bitmaps;
        bitmapAndId.bitmapIDs = bitmapIDs;
        return bitmapAndId;
    }
    //获取piece矩阵，四周预留一圈的空位
    public static Piece[][] getPieces(Context context, GameView gameView, int x, int y) {
        Piece[][] pieces = new Piece[x][y];
        BitmapAndId bitmapAndId = getBitmap(context, x, y);
        Bitmap[][] bitmaps = bitmapAndId.bitmaps;
        float gameViewX = gameView.getLeft();
        float gameViewY = gameView.getTop();
        float width = (gameView.getRight()-gameViewX)/(x+2);//每张图片的宽度
        float height = (gameView.getBottom()-gameViewY)/(y+2);//每张图片的高度
        Piece.width = width;
        Piece.height = height;
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                Piece piece = new Piece();
                piece.setX((i+1)*width);
                piece.setY((j + 1) * height);
                piece.setBitmap(bitmaps[i][j]);
                piece.setIndexX(i);
                piece.setIndexY(j);
                piece.setBitmapId(bitmapAndId.bitmapIDs.get(x * j + i));
                pieces[i][j] = piece;
            }
        }
        return pieces;
    }

    static class BitmapAndId{
        Bitmap[][] bitmaps;
        List<Integer> bitmapIDs;
    }
}

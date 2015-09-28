package com.example.lianliankan;

import android.app.Application;
import android.graphics.Bitmap;
import android.test.ApplicationTestCase;

import com.example.lianliankan.activity.GameActivity;
import com.example.lianliankan.util.ImageUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testGetRandomImageIDs(){
        List<Integer> ids = new ArrayList<Integer>();
        assertEquals(0, ids.size());
        ids = ImageUtil.getRandomImageIDs(42);
        assertEquals(42, ids.size());
    }

    public void testGetBitmap(){
//        Bitmap[][] bitmaps = ImageUtil.getBitmap(Activity.this, 6, 7);

    }
}
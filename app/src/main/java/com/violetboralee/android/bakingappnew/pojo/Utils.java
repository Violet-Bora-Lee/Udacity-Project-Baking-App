package com.violetboralee.android.bakingappnew.pojo;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by bora on 20/11/2017.
 */

public class Utils {

    public String loadJSONFromAsset(Context context) {
        String json = null;

        try {
            InputStream is = context.getAssets().open("baking.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }
}

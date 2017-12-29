package com.violetboralee.android.bakingappnew.util;

import java.net.URLConnection;

/**
 * Created by bora on 29/12/2017.
 */

public class ImageUtil {
    public static boolean isImageFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("image");
    }
}

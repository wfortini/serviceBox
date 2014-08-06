
package br.com.servicebox.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;

public class ImageUtils {
    static final String TAG = ImageUtils.class.getSimpleName();

    public static final String TAG_DATETIME_ORIGINAL = "DateTimeOriginal";
    public static final String TAG_DATETIME_DIGITIZED = "DateTimeDigitized";
    public static final String TAG_DATETIME = ExifInterface.TAG_DATETIME;

    /**
     * decodes image and scales it to reduce memory consumption <br />
     * <br />
     * Source: http://stackoverflow
     * .com/questions/477572/android-strange-out-of-memory-issue/823966#823966
     * 
     * @param file File
     * @param requiredSize size that the image should have
     * @return image in required size
     */
    public static Bitmap decodeFile(File file, int requiredSize) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(file), null, o);

            // The new size we want to scale to
            final int REQUIRED_SIZE = requiredSize;

            // Find the correct scale value. It should be the power of 2.
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(file), null, o2);
        } catch (FileNotFoundException e) {
            GuiUtils.noAlertError(TAG, null, e);
        }
        return null;
    }

    /**
     * @param context the context
     * @param imageUri Uri of the image for which the file path should be
     *            returned
     * @return file path of the given imageUri
     */
    public static String getRealPathFromURI(Context context, Uri imageUri) {
        if (imageUri.getScheme().equals("file")) {
            return imageUri.getPath();
        }
        String[] proj = {
            MediaStore.Images.Media.DATA
        };
        Cursor cursor = context.getContentResolver().query(imageUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        if (cursor.moveToFirst()) {
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        } else {
            return null;
        }
    }

    /**
     * Returns number of milliseconds since Jan. 1, 1970, midnight. Returns -1
     * if the date time information if not available.
     * 
     * @param attributeName
     * @throws IOException
     */
    public static long getExifDateTime(String fileName) throws IOException {
        ExifInterface exif = new ExifInterface(fileName);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
        long result = getExifDateTime(exif, TAG_DATETIME_ORIGINAL, formatter);
        CommonUtils.debug(TAG, "getExifDateTime: getting %1$s", TAG_DATETIME_ORIGINAL);
        if (result == -1) {
            CommonUtils.debug(TAG, "getExifDateTime: getting %1$s", TAG_DATETIME_DIGITIZED);
            result = getExifDateTime(exif, TAG_DATETIME_DIGITIZED, formatter);
        }
        if (result == -1) {
            CommonUtils.debug(TAG, "getExifDateTime: getting %1$s", TAG_DATETIME);
            result = getExifDateTime(exif, TAG_DATETIME, formatter);
        }
        return result;
    }

    /**
     * Returns number of milliseconds since Jan. 1, 1970, midnight. Returns -1
     * if the date time information if not available.
     * 
     * @param exif
     * @param attributeName
     * @param formatter
     * @return
     */
    private static long getExifDateTime(ExifInterface exif, String attributeName,
            SimpleDateFormat formatter) {
        String dateTimeString = exif.getAttribute(attributeName);
        if (dateTimeString == null)
            return -1;

        ParsePosition pos = new ParsePosition(0);
        try {
            Date datetime = formatter.parse(dateTimeString, pos);
            if (datetime == null)
                return -1;
            return datetime.getTime();
        } catch (IllegalArgumentException ex) {
            return -1;
        }
    }
}

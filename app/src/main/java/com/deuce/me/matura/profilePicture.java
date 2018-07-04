package com.deuce.me.matura;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by ingli on 24.06.2018.
 */

public class profilePicture {

    private static final int QUALITY = 100;

    private boolean success = false;
    private Context Context;
    private Bitmap imageBitmap;
    private String BASE64;

    public profilePicture(Context context, Uri imageUri) {
        //...//
        this.Context = context;

        String path = getPath(imageUri);
        String filetype = getFileType(path);

        if(filetype.equals("img") || filetype.equals("jpeg") || filetype.equals("jpg") || filetype.equals("png")) {
            this.imageBitmap = getBitmap(path);
            this.BASE64 = encodeBASE64(Bitmap.CompressFormat.JPEG, QUALITY);
            this.success = true;
        } else {
            this.success = false;
        }
    }

    public profilePicture(Context context, Bitmap imageBitmap) {
        this.Context = context;
        this.imageBitmap = imageBitmap;
        this.BASE64 = encodeBASE64(Bitmap.CompressFormat.JPEG, QUALITY);
    }

    public profilePicture(Context context, String BASE64) {
        //...//
        this.Context = context;
        this.BASE64 = BASE64;
        this.imageBitmap = decodeBASE64(this.BASE64);
        this.success = true;
    }

    public void update(Uri imageUri) {
        //...//

        String path = getPath(imageUri);
        String filetype = getFileType(path);

        if(filetype.equals("img") || filetype.equals("jpeg") || filetype.equals("jpg") || filetype.equals("png")) {
            this.imageBitmap = getBitmap(path);
            this.BASE64 = encodeBASE64(Bitmap.CompressFormat.JPEG, QUALITY);
            this.success = true;
        } else {
            this.success = false;
        }
    }

    public void update(String BASE64) {
        //...//
        this.BASE64 = BASE64;
        this.imageBitmap = decodeBASE64(this.BASE64);
        this.success = true;
    }

    public void update(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
        this.BASE64 = encodeBASE64(Bitmap.CompressFormat.JPEG, QUALITY);
    }

    //https://stackoverflow.com/questions/9768611/encode-and-decode-bitmap-object-in-base64-string-in-android
    private String encodeBASE64(Bitmap.CompressFormat format, int quality) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        imageBitmap.compress(format, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    private Bitmap decodeBASE64(String BASE64) {
        byte[] decodedInput = Base64.decode(BASE64, 0);
        return BitmapFactory.decodeByteArray(decodedInput, 0, decodedInput.length);
    }

    private String getPath(Uri uri) {
        Cursor cursor = Context.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String documentID = cursor.getString(0);
        documentID = documentID.substring(documentID.lastIndexOf(":") + 1);
        cursor.close();

        cursor = Context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null,
                MediaStore.Images.Media._ID + " = ?",
                new String[]{documentID},
                null
        );

        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();
        return path;
        //int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        //cursor.moveToFirst();
        //return cursor.getString(column_index);
    }

    //https://colinyeoh.wordpress.com/2012/05/18/android-convert-image-uri-to-byte-array/
    private byte[] getByte(Uri uri, int quality) {
        byte[] data = null;
        try {
            ContentResolver cr = Context.getContentResolver();
            InputStream inputStream = cr.openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
            data = baos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

    private Bitmap getBitmap(String path) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        return bitmap;
    }

    private String getFileType(String path) {
        String filetype = path.substring(path.lastIndexOf(".") + 1);
        return filetype;
    }

    public boolean isSuccess() {
        return success;
    }

    public android.content.Context getContext() {
        return Context;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public String getBASE64() {
        return BASE64;
    }
}

package com.deuce.me.matura;

import android.*;
import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.util.List;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

/**
 * Created by ingli on 02.07.2018.
 */

public class chooseImage_activity extends AppCompatActivity {

    private userInfo clientInfo;
    private Bundle extras;
    private profilePicture picture;
    private File chosenFile;
    private Intent CameraIntent, GalleryIntent;
    private Uri imageURI;
    private ImageView profileImage_iv;

    private static final int REQUEST_PERMISSION_CODE = 1;
    private static final int STORAGE_PERMISSION_CODE = 2342;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooseimage_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.chooseimage_toolbar);
        toolbar.setTitle(R.string.chooseimage_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        extras = getIntent().getExtras();

        try {
            clientInfo = new JSONtoInfo(getBaseContext()).createNewItem(new JSONObject(extras.getString("clientInfo")));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        profileImage_iv = (ImageView) findViewById(R.id.chooseimageact_chosenimage_imageview);
        picture = new profilePicture(getBaseContext(), clientInfo.getProfilePictureBASE64());
        profileImage_iv.setImageBitmap(picture.getImageBitmap());

        int permission = ContextCompat.checkSelfPermission(chooseImage_activity.this, Manifest.permission.CAMERA);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.choose_image, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.choose_camera) {
            getCameraPermission();
            getStoragePermission();
            openChooserGalleryCamera();
        } else if(item.getItemId() == R.id.choose_image) {
            getStoragePermission();
            OpenGallery();
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*if (requestCode == 0 && resultCode == RESULT_OK) {
            CropImage();
        } else if(requestCode == 1 && data != null) {
            imageURI = data.getData();
            CropImage();
        } else */
        if(requestCode == 2 && data != null) {
            Bundle result = data.getExtras();
            Bitmap bitmap = result.getParcelable("data");
            profileImage_iv.setImageBitmap(bitmap);

            picture.update(bitmap);

            savesettings_pw_request request = new savesettings_pw_request(
                    clientInfo.getId(),
                    clientInfo.getFirstname(),
                    clientInfo.getName(),
                    clientInfo.getEmail(),
                    clientInfo.getSchool(),
                    clientInfo.getDescription(),
                    clientInfo.getPassword(),
                    clientInfo.getPassword(),
                    clientInfo.isGerman(),
                    clientInfo.isSpanish(),
                    clientInfo.isEnglish(),
                    clientInfo.isFrench(),
                    clientInfo.isBiology(),
                    clientInfo.isChemistry(),
                    clientInfo.isMusic(),
                    clientInfo.isMaths(),
                    clientInfo.isPhysics(),
                    picture.getBASE64(),
                    new settingsOverview.onSaveResponseListener(getBaseContext(), 1)
            );

            RequestQueue request_queue = Volley.newRequestQueue(chooseImage_activity.this); //Request Queue
            request_queue.add(request);
        } else {
            EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {

                //https://inthecheesefactory.com/blog/how-to-share-access-to-file-with-fileprovider-on-android-nougat/en
                @Override
                public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {
                    if(!imageFiles.isEmpty() && imageFiles.size() == 1) {
                        imageURI = FileProvider.getUriForFile(chooseImage_activity.this,
                                BuildConfig.APPLICATION_ID + ".provider",
                                imageFiles.get(0));

                        CropImage(imageURI);
                    } else {
                        System.out.println("::ERROR WITH IMAGE::");
                    }
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case REQUEST_PERMISSION_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    System.out.println("Permission Granted");
                } else {
                    System.out.println("Permission Denied");
                }
        }
    }

    private void CropImage(Uri uri) {

        try {
            Intent CropIntent = new Intent("com.android.camera.action.CROP");
            CropIntent.setDataAndType(uri, "image/*");

            CropIntent.putExtra("crop", "true");
            CropIntent.putExtra("outputX", R.dimen.crop_dimen);
            CropIntent.putExtra("outputY", R.dimen.crop_dimen);
            CropIntent.putExtra("aspectX",1);
            CropIntent.putExtra("aspectY",1);
            CropIntent.putExtra("scaleUpIfNeeded",true);
            CropIntent.putExtra("return-data", true);

            //CropIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.name());
            //CropIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(getExternalStorageTempStoreFilePath()));
            CropIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            CropIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(CropIntent, 2);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }


    //https://github.com/jkwiecien/EasyImage
    private void openChooserGalleryCamera() {
        EasyImage.openChooserWithGallery(chooseImage_activity.this, getString(R.string.chooseOption_caption), 0);
    }

    private void OpenGallery() {
        GalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(GalleryIntent, R.string.selectfromgallery_title + ""), 1);
    }

    private void OpenCamera() {
        CameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        CameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        chosenFile = new File(Environment.getExternalStorageDirectory(), "file" + String.valueOf(System.currentTimeMillis()) + ".jpg");
        imageURI = Uri.fromFile(chosenFile);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            CameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageURI);
        } else {
            Uri photoUri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", chosenFile);
            CameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        }

        CameraIntent.putExtra("return-data", true);
        startActivityForResult(CameraIntent,0);
    }

    private void getCameraPermission() {
        if(ContextCompat.checkSelfPermission(chooseImage_activity.this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(chooseImage_activity.this, Manifest.permission.CAMERA)) {
                System.out.println("::CAMERA PERMISSION GRANTED::");
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION_CODE);
                System.out.println("::CAMERA PERMISSION GRANTED::");
            }
        }
    }

    private void getStoragePermission() {
        if(ContextCompat.checkSelfPermission(chooseImage_activity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(chooseImage_activity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                System.out.println("::R STORAGE PERMISSION GRANTED::");
            } else {
                ActivityCompat.requestPermissions(chooseImage_activity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                System.out.println("::R STORAGE PERMISSION GRANTED::");
            }
        }

        if(ContextCompat.checkSelfPermission(chooseImage_activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(chooseImage_activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                System.out.println("::W STORAGE PERMISSION GRANTED::");
            } else {
                ActivityCompat.requestPermissions(chooseImage_activity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                System.out.println("::W STORAGE PERMISSION GRANTED::");
            }
        }
    }
}

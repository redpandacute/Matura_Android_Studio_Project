package com.deuce.me.matura.trash;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import com.deuce.me.matura.BuildConfig;
import com.deuce.me.matura.R;
import com.deuce.me.matura.activities.ProfilesettingsActivity;
import com.deuce.me.matura.activities.SettingsoverviewActivity;
import com.deuce.me.matura.models.ProfilePictureModel;
import com.deuce.me.matura.models.UserModel;
import com.deuce.me.matura.requests.SaveSettingsRequest;
import com.deuce.me.matura.util.JSONtoInfo;
import com.deuce.me.matura.util.tempFileGenerator;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

/**
 * Created by ingli on 02.07.2018.
 */

public class chooseImage_activity extends AppCompatActivity {

    private UserModel clientInfo;
    private Bundle extras;
    private ProfilePictureModel picture_big, picture_small;
    private File chosenFile, tempFile, tempDir;
    private Intent CameraIntent, GalleryIntent;
    private Uri imageURI, tempUri;
    private ImageView profileImage_iv;


    private static final int CROP_CODE = 1;
    private static final int REQUEST_PERMISSION_CODE = 1;
    private static final int STORAGE_PERMISSION_CODE = 2342;
    private static final int QUALITY_BIG = 70, QUALITY_SMALL = 40;
    private static final int SCALE_BIG = 400, SCALE_SMALL = 100;

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
        picture_big = new ProfilePictureModel(getBaseContext(),new File(clientInfo.getTempProfilePicturePath()));
        profileImage_iv.setImageBitmap(picture_big.getImageBitmap());

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
        } else if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(chooseImage_activity.this, ProfilesettingsActivity.class);
            intent.putExtra("clientInfo", extras.getString("clientInfo"));
            startActivity(intent);
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
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                picture_big.updateWithPath(tempFile.getPath());
                picture_big.compress(QUALITY_BIG);
                picture_big.downscale(SCALE_BIG);
                profileImage_iv.setImageBitmap(picture_big.getImageBitmap());
                String small_temp_path = new tempFileGenerator().getTempFilePath(getBaseContext(), picture_big.getBASE64());
                picture_small = new ProfilePictureModel(getBaseContext(), new File(small_temp_path));
                picture_small.compress(QUALITY_SMALL);
                picture_small.downscale(SCALE_SMALL);

                SaveSettingsRequest request = new SaveSettingsRequest(
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
                        picture_big.getBASE64(),
                        picture_small.getBASE64(),
                        new SettingsoverviewActivity.onSaveResponseListener(getBaseContext(), 1)
                );

                RequestQueue request_queue = Volley.newRequestQueue(chooseImage_activity.this); //Request Queue
                request_queue.add(request);
            }
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
        tempDir = getCacheDir();
        try {
            tempFile = File.createTempFile("file" + String.valueOf(System.currentTimeMillis()), ".png", tempDir);
        } catch (IOException e) {
            e.printStackTrace();
            tempFile = new File(tempDir, "file" + String.valueOf(System.currentTimeMillis())+ ".png");
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            tempUri = Uri.fromFile(tempFile);
        } else {
            tempUri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", tempFile);
        }

        try {
            Intent cropIntent = CropImage.activity(uri)
                    .setCropShape(CropImageView.CropShape.OVAL)
                    .setOutputCompressFormat(Bitmap.CompressFormat.PNG)
                    .setFixAspectRatio(true)
                    .setOutputUri(tempUri)
                    .setOutputCompressQuality(QUALITY_BIG)
                    .setRequestedSize(R.dimen.crop_dimen, R.dimen.crop_dimen, CropImageView.RequestSizeOptions.RESIZE_INSIDE)
                    .setAutoZoomEnabled(true)
                    .setActivityTitle(getString(R.string.crop_image_activity_title))
                    .setGuidelines(CropImageView.Guidelines.ON).getIntent(this);

            startActivityForResult(cropIntent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
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

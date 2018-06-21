package com.deuce.me.matura;

import android.*;
import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;


//import com.android.internal.http.multipart.MultipartEntity;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

/*import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.entity.mime.*;*/
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class profileSettings extends AppCompatActivity {

    EditText firstname_et, name_et, description_et;
    CheckBox german_cb, spanish_cb, french_cb, english_cb, biology_cb, music_cb, chemistry_cb, maths_cb, physics_cb;
    Spinner school_sp;
    Button save_bt;
    Bundle extras;
    userInfo clientInfo;
    ImageView profilePicture_iv;
    String profilepicturePath;
    final String uploadHTTPAddress = "https://lsdfortheelderly.000webhostapp.com/pictureupload_php.php";
    final int MAX_RETRIES = 2;

    private static final int STORAGE_PERMISSION_CODE = 2342;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);
        getSupportActionBar().setTitle(R.string.profileSettings_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        profilePicture_iv = (ImageView) findViewById(R.id.profsettings_profilepic_imageview);

        firstname_et = (EditText) findViewById(R.id.profsettings_firstname_edittext);
        name_et = (EditText) findViewById(R.id.profsettings_name_edittext);
        description_et = (EditText) findViewById(R.id.profsettings_description_edittext);

        school_sp = (Spinner) findViewById(R.id.profsettings_school_spinner);

        german_cb = (CheckBox) findViewById(R.id.profsettings_german_checkbox);
        spanish_cb = (CheckBox) findViewById(R.id.profsettings_spanish_checkbox);
        english_cb = (CheckBox) findViewById(R.id.profsettings_english_checkbox);
        french_cb = (CheckBox) findViewById(R.id.profsettings_french_checkbox);
        biology_cb = (CheckBox) findViewById(R.id.profsettings_biology_checkbox);
        music_cb = (CheckBox) findViewById(R.id.profsettings_music_checkbox);
        chemistry_cb = (CheckBox) findViewById(R.id.profsettings_chemics_checkbox);
        maths_cb = (CheckBox) findViewById(R.id.profsettings_maths_checkbox);
        physics_cb = (CheckBox) findViewById(R.id.profsettings_physics_checkbox);

        save_bt = findViewById(R.id.profsettings_save_bt);
        save_bt.setOnClickListener(new onSaveListener());

        profilePicture_iv.setOnClickListener(new onProfilePictureListener());

        extras = getIntent().getExtras();
        try {
            clientInfo = new JSONtoInfo().createNewItem(new JSONObject(extras.getString("clientInfo")));
            firstname_et.setText(clientInfo.getFirstname());
            name_et.setText(clientInfo.getName());
            description_et.setText(clientInfo.getDescription());

            german_cb.setChecked(clientInfo.isGerman());
            spanish_cb.setChecked(clientInfo.isSpanish());
            english_cb.setChecked(clientInfo.isEnglish());
            french_cb.setChecked(clientInfo.isFrench());
            biology_cb.setChecked(clientInfo.isBiology());
            music_cb.setChecked(clientInfo.isMusic());
            chemistry_cb.setChecked(clientInfo.isChemistry());
            maths_cb.setChecked(clientInfo.isMaths());
            physics_cb.setChecked(clientInfo.isPhysics());

        } catch(JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(profileSettings.this, settingsOverview.class);
                intent.putExtra("clientInfo", getIntent().getExtras().getString("clientInfo"));
                startActivity(intent);
                System.out.println("::BACK BUTTON::");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class onProfilePictureListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            requestStoragePermission();
            Intent pictureIntent = new Intent();
            pictureIntent.setType("image/*");
            pictureIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(pictureIntent, "Choose your profilepicture:"), 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1 && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            //profilepicturePath = imageUri.getPath();
            profilepicturePath = getPath(imageUri);
            System.out.println(imageUri);
            System.out.println(profilepicturePath);
            String filetype = profilepicturePath.substring(profilepicturePath.lastIndexOf(".") + 1);
            if (filetype.equals("img") || filetype.equals("png") || filetype.equals("jpg") || filetype.equals("jpeg")) {
                Bitmap bitmap = BitmapFactory.decodeFile(profilepicturePath);
                profilePicture_iv.setImageBitmap(bitmap);

                uploadImage(/*imageUri*/);
            }
        }
    }

    //https://stackoverflow.com/questions/9768611/encode-and-decode-bitmap-object-in-base64-string-in-android
    public String encodeBASE64(Bitmap imageBitmap, Bitmap.CompressFormat format, int quality) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        imageBitmap.compress(format, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    public Bitmap decodeBASE64(String base64String) {
        byte[] decodedInput = Base64.decode(base64String, 0);
        return BitmapFactory.decodeByteArray(decodedInput, 0, decodedInput.length);
    }


    //https://www.youtube.com/watch?v=odmC3aa210Q
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permssions, @NonNull int[] grantResults) {
        if(requestCode == STORAGE_PERMISSION_CODE) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                System.out.println("STORAGE ACCESS: TRUE");
            } else {
                System.out.println("STORAGE ACCESS: FALSE");
            }
        }
    }

    //https://www.youtube.com/watch?v=odmC3aa210Q
    private boolean easyUpload() {

        String uploadID = UUID.randomUUID().toString();

        try {
            new MultipartUploadRequest(this, uploadID, uploadHTTPAddress)
                    .addFileToUpload(profilepicturePath, "image")
                    .addParameter("user_username", clientInfo.getUsername())
                    .addParameter("user_password", clientInfo.getPassword())
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(MAX_RETRIES)
                    .startUpload();
            System.out.println("REEEEEEEEEEEEEEEEEEEEEE!");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //https://www.androidpit.com/forum/626144/android-image-uploading-to-server-from-gallery
    //https://www.youtube.com/watch?v=odmC3aa210Q
    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String documentID = cursor.getString(0);
        documentID = documentID.substring(documentID.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
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
    public byte[] uriToByte(Uri uri) {
        byte[] data = null;
        try {
            ContentResolver cr = getBaseContext().getContentResolver();
            InputStream inputStream = cr.openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            data = baos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

    class onSaveListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            String name = name_et.getText().toString();
            String firstname = firstname_et.getText().toString();
            String description = description_et.getText().toString();

            // String school = school_sp.getSelectedItem().toString();
            String school = "";

            boolean german = german_cb.isChecked();
            boolean spanish = spanish_cb.isChecked();
            boolean english = english_cb.isChecked();
            boolean french = french_cb.isChecked();
            boolean biology = biology_cb.isChecked();
            boolean chemistry = chemistry_cb.isChecked();
            boolean maths = maths_cb.isChecked();
            boolean physics = physics_cb.isChecked();
            boolean music = music_cb.isChecked();

            if (!name.isEmpty() && !firstname.isEmpty()) {

                System.out.println("Making save request");

                savesettings_pw_request save_request = new savesettings_pw_request(clientInfo.getId(),
                        firstname,
                        name,
                        clientInfo.getEmail(),
                        school,
                        description,
                        clientInfo.getPassword(),
                        clientInfo.getPassword(),
                        german,
                        spanish,
                        english,
                        french,
                        biology,
                        chemistry,
                        music,
                        maths,
                        physics,
                        new onResponseListener(getApplicationContext()));

                RequestQueue request_queue = Volley.newRequestQueue(profileSettings.this); //Request Queue
                request_queue.add(save_request);
            }
        }
    }
}

package com.deuce.me.matura;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Flo on 18.02.2018.
 */

public class userInfo {
    // private ?? pb;

    private Context context;
    private String JSON;

    private int id;
    private String username;
    private String name;
    private String firstname;
    private String school;
    private int yearofbirth;
    private String description;
    private String email = null;

    private String temp_profilepicture_path;

    private String passwordHash = null;
    private String salt = null;

    private boolean french;
    private boolean spanish;
    private boolean english;
    private boolean music;
    private boolean chemistry;
    private boolean biology;
    private boolean maths;
    private boolean physics;
    private boolean german;

    public userInfo(int id, String username, String name, String firstname, int yearofbirth, String description,
                    boolean french, boolean spanish, boolean music, boolean english, boolean chemistry, boolean biology, boolean maths, boolean german, boolean physics,
                    String temp_profilepicture_path, String JSON
    ) {
        this.context = context;
        this.JSON = JSON;

        this.id = id;
        this.username = username;
        this.name = name;
        this.firstname = firstname;
        this.yearofbirth = yearofbirth;
        this.description = description;

        this.french = french;
        this.spanish = spanish;
        this.english = english;
        this.music = music;
        this.chemistry = chemistry;
        this.biology = biology;
        this.maths = maths;
        this.physics = physics;
        this.german = german;

        this.temp_profilepicture_path = temp_profilepicture_path;
    }

    public userInfo(int id, String username, String name, String firstname, int yearofbirth, String description, String email,
                    boolean french, boolean spanish, boolean music, boolean english, boolean chemistry, boolean biology, boolean maths, boolean german, boolean physics,
                    String passwordHash, String salt,
                    String temp_profilepicture_path, String JSON
    ) {
        this.id = id;
        this.JSON = JSON;

        this.username = username;
        this.name = name;
        this.firstname = firstname;
        this.yearofbirth = yearofbirth;
        this.description = description;
        this.email = email;

        this.french = french;
        this.spanish = spanish;
        this.english = english;
        this.music = music;
        this.chemistry = chemistry;
        this.biology = biology;
        this.maths = maths;
        this.physics = physics;
        this.german = german;

        this.passwordHash = passwordHash;
        this.salt = salt;

        this.temp_profilepicture_path = temp_profilepicture_path;
    }
    /*
    private Bitmap decodeProfilePicture(String encodedProfilePicture) {
        if(!encodedProfilePicture.equals("0")) {
            byte[] decodedString = Base64.decode(encodedProfilePicture, Base64.DEFAULT);
            Bitmap data = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            return data;
        } else {
            Bitmap data = BitmapFactory.decodeResource(context.getResources(), R.drawable.bi_medal);
            return data;
        }
    }
*/

    public String getJSON() {
        return JSON;
    }

    public void setJSON(String JSON) {
        this.JSON = JSON;
    }

    public void updateJSON() throws JSONException {
        JSONObject jsn = new JSONObject();
        jsn.put("user_username", username);
        jsn.put("user_firstname", firstname);
        jsn.put("user_name", name);
        jsn.put("user_school", school);
        jsn.put("user_yearofbirth", yearofbirth);
        jsn.put("user_description", description);
        jsn.put("user_email", email);
        jsn.put("user_id", id);

        if(german){jsn.put("subj_german",1);} else {jsn.put("subj_german",0);}
        if(spanish){jsn.put("subj_spanish",1);} else {jsn.put("subj_spanish",0);}
        if(english){jsn.put("subj_english",1);} else {jsn.put("subj_english",0);}
        if(french){jsn.put("subj_french",1);} else {jsn.put("subj_french",0);}
        if(biology){jsn.put("subj_biology",1);} else {jsn.put("subj_biology",0);}
        if(chemistry){jsn.put("subj_chemistry",1);} else {jsn.put("subj_chemistry",0);}
        if(music){jsn.put("subj_music",1);} else {jsn.put("subj_music",0);}
        if(physics){jsn.put("subj_physics",1);} else {jsn.put("subj_physics",0);}
        if(maths){jsn.put("subj_maths",1);} else {jsn.put("subj_maths",0);}

        jsn.put("hash_password", passwordHash);
        jsn.put("hash_salt", salt);
        jsn.put("temp_profilepicture_path", temp_profilepicture_path);

        this.JSON = jsn.toString();
    }

    public String getTempProfilePicturePath() {
        return temp_profilepicture_path;
    }

    public void setTempProfilePicturePath(String temp_profilepicture_path) {
        this.temp_profilepicture_path = temp_profilepicture_path;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSchool() {
        return "KSA Pfäffikon SZ";
        //return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public int getYearofbirth() {
        return yearofbirth;
    }

    public void setYearofbirth(int yearofbirth) {
        this.yearofbirth = yearofbirth;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFrench() {
        return french;
    }

    public void setFrench(boolean french) {
        this.french = french;
    }

    public boolean isSpanish() {
        return spanish;
    }

    public void setSpanish(boolean spanish) {
        this.spanish = spanish;
    }

    public boolean isEnglish() {
        return english;
    }

    public void setEnglish(boolean english) {
        this.english = english;
    }

    public boolean isMusic() {
        return music;
    }

    public void setMusic(boolean music) {
        this.music = music;
    }

    public boolean isChemistry() {
        return chemistry;
    }

    public void setChemistry(boolean chemistry) {
        this.chemistry = chemistry;
    }

    public boolean isBiology() {
        return biology;
    }

    public void setBiology(boolean biology) {
        this.biology = biology;
    }

    public boolean isMaths() {
        return maths;
    }

    public void setMaths(boolean maths) {
        this.maths = maths;
    }

    public boolean isPhysics() {
        return physics;
    }

    public void setPhysics(boolean physics) {
        this.physics = physics;
    }

    public boolean isGerman() {
        return german;
    }

    public void setGerman(boolean german) {
        this.german = german;
    }

    public String getPassword() {
        return passwordHash;
    }

    public void setPassword(String password) {
        this.passwordHash = password;
    }
}

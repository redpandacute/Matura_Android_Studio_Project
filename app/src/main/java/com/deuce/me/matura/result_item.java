package com.deuce.me.matura;

/**
 * Created by Flo on 18.02.2018.
 */

public class result_item {
    // private ?? pb;

    private int id;
    private String username;
    private String name;
    private String firstname;
    //private String school;
    private int yearofbirth;
    private String description;

    private boolean french;
    private boolean spanish;
    private boolean english;
    private boolean music;
    private boolean chemistry;
    private boolean biology;
    private boolean maths;
    private boolean physics;
    private boolean german;

    public result_item(int id, String username, String name, String firstname, int yearofbirth, String description, boolean french, boolean spanish, boolean music, boolean english, boolean chemistry, boolean biology, boolean maths, boolean german, boolean physics) {
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
}

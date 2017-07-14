package com.mukul.panorbit;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Mukul on 19-06-2017.
 */

public class SessionManager {
    String TAG = SessionManager.class.getSimpleName();

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "panorbit";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
    private static final String NAME = "name";
    private static final String SOS = "mobilenumber";
    private static final String SOSName = "sosname";

    private static final String AGE = "age";
    private static final String IMAGE_URL = "uri";

    public SessionManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = preferences.edit();
    }

    public void setLogin(boolean isLoggedIn) {
        if (!isLoggedIn)
            clearAll();
        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        editor.commit();
    }

    public boolean isLoggedIn() {
        return preferences.getBoolean(KEY_IS_LOGGEDIN, false);
    }



    public void setSOS(String number) {
        editor.putString(SOS, number);
        editor.commit();
    }

    public String getSOS() {
        return preferences.getString(SOS, "");
    }
    public void setSOSName(String name) {
        editor.putString(SOSName, name);
        editor.commit();
    }

    public String getSOSName() {
        return preferences.getString(SOSName, "");
    }

    public void setName(String name) {
        editor.putString(NAME, name);
        editor.commit();
    }

    public String getName() {
        return preferences.getString(NAME, "");
    }

    public void setAge(String age) {
        editor.putString(AGE, age);
        editor.commit();
    }

    public String getAge() {
        return preferences.getString(AGE, "");
    }

    public void setImageUri(String uri) {
        editor.putString(IMAGE_URL, uri);
        editor.commit();
    }

    public String getImageUri() {
        return preferences.getString(IMAGE_URL, "");
    }


    public void clearAll() {
        editor.clear();
        editor.commit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return preferences.getBoolean(IS_FIRST_TIME_LAUNCH, true);
//        return true;
    }


}
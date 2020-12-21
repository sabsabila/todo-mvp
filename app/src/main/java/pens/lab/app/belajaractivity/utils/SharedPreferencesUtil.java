package pens.lab.app.belajaractivity.utils;

import android.content.SharedPreferences;

public class SharedPreferencesUtil {
    private SharedPreferences sharedPreferences;

    public SharedPreferencesUtil(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void setUsername(String username){
        sharedPreferences.edit().putString("username", username).commit();
    }

    public String getUsername(){
        return sharedPreferences.getString("username", null);
    }

    public void clear(){
        sharedPreferences.edit().clear().commit();
    }
}

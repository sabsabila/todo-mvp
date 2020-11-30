package pens.lab.app.belajaractivity.utils.session;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import pens.lab.app.belajaractivity.model.User;

public class UserSessionRepositoryRepository implements SessionRepository<User> {
    private static String SESSION_USER = "SessionUser";
    private SharedPreferences sharedPref;

    public UserSessionRepositoryRepository(Context context) {
        sharedPref = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public User initialize(User sessionData) {
        //save to shared preference
        setSessionData(sessionData);

        //load from shared preference
        return getSessionData();
    }

    @Override
    public User getSessionData() {
        String sessionDataJson = sharedPref.getString(SESSION_USER, null);
        if (sessionDataJson != null) {
            return new Gson().fromJson(sessionDataJson, User.class);
        }
        return null;
    }

    @Override
    public void setSessionData(User sessionData) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(SESSION_USER, new Gson().toJson(sessionData));
        editor.apply();
    }

    @Override
    public void destroy() {
        sharedPref.edit().clear().apply();
    }

    @Override
    public void update(User sessionData) {
        destroy();
        setSessionData(sessionData);
    }

}

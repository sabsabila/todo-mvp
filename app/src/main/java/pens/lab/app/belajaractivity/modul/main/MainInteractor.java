package pens.lab.app.belajaractivity.modul.main;

import pens.lab.app.belajaractivity.utils.SharedPreferencesUtil;

public class MainInteractor implements MainContract.Interactor{
    private SharedPreferencesUtil sharedPreferencesUtil;

    public MainInteractor(SharedPreferencesUtil sharedPreferencesUtil) {
        this.sharedPreferencesUtil = sharedPreferencesUtil;
    }

    @Override
    public boolean isUserLogin() {
        if(sharedPreferencesUtil.getToken() != null){
            return true;
        }
        else {
            return false;
        }
    }
}

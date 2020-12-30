package pens.lab.app.belajaractivity.modul.login;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import pens.lab.app.belajaractivity.constant.ApiConstant;
import pens.lab.app.belajaractivity.response.LoginResponse;
import pens.lab.app.belajaractivity.utils.RequestCallback;
import pens.lab.app.belajaractivity.utils.SharedPreferencesUtil;

public class LoginInteractor implements LoginContract.Interactor{
    private SharedPreferencesUtil sharedPreferencesUtil;

    public LoginInteractor(SharedPreferencesUtil sharedPreferencesUtil) {
        this.sharedPreferencesUtil = sharedPreferencesUtil;
    }

    @Override
    public void requestLogin(String email, String password, final RequestCallback<String> callback) {
        AndroidNetworking.post(ApiConstant.BASE_URL + "login")
                .addBodyParameter("email", email)
                .addBodyParameter("password", password)
                .build()
                .getAsObject(LoginResponse.class, new ParsedRequestListener<LoginResponse>() {
                    @Override
                    public void onResponse(LoginResponse response) {
                        callback.requestSuccess(response.token);
                    }
                    @Override
                    public void onError(ANError error) {
                        callback.requestFailed(error.getMessage());
                    }
                });
    }

    @Override
    public void saveToken(String token) {
        sharedPreferencesUtil.setToken(token);
    }

}

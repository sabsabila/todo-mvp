package pens.lab.app.belajaractivity.modul.login;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import pens.lab.app.belajaractivity.constant.ApiConstant;
import pens.lab.app.belajaractivity.api_response.ErrorResponse;
import pens.lab.app.belajaractivity.api_response.LoginResponse;
import pens.lab.app.belajaractivity.callback.RequestCallback;
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
                        if(error.getErrorCode() == 401)
                            callback.requestFailed(ErrorResponse.wrongCredentials);
                        else
                            callback.requestFailed(ErrorResponse.requestFailed);
                    }
                });
    }

    @Override
    public void requestGoogleLogin(String email, String name, final RequestCallback<String> callback) {
        AndroidNetworking.post(ApiConstant.BASE_URL + "login/google")
                .addBodyParameter("email", email)
                .addBodyParameter("name", name)
                .build()
                .getAsObject(LoginResponse.class, new ParsedRequestListener<LoginResponse>() {
                    @Override
                    public void onResponse(LoginResponse response) {
                        callback.requestSuccess(response.token);
                    }
                    @Override
                    public void onError(ANError error) {
                        if(error.getErrorCode() == 401)
                            callback.requestFailed(ErrorResponse.wrongCredentials);
                        else
                            callback.requestFailed(ErrorResponse.requestFailed);
                        Log.d("tag", error.getMessage() + error.getErrorCode());
                    }
                });
    }

    @Override
    public void saveToken(String token) {
        sharedPreferencesUtil.setToken(token);
    }

}

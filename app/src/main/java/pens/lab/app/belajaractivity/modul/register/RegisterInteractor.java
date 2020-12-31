package pens.lab.app.belajaractivity.modul.register;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

import pens.lab.app.belajaractivity.constant.ApiConstant;
import pens.lab.app.belajaractivity.model.User;
import pens.lab.app.belajaractivity.api_response.ErrorResponse;
import pens.lab.app.belajaractivity.api_response.ResponseMessage;
import pens.lab.app.belajaractivity.callback.RequestCallback;
import pens.lab.app.belajaractivity.utils.SharedPreferencesUtil;

public class RegisterInteractor implements RegisterContract.Interactor{
    private SharedPreferencesUtil sharedPreferencesUtil;

    public RegisterInteractor(SharedPreferencesUtil sharedPreferencesUtil) {
        this.sharedPreferencesUtil = sharedPreferencesUtil;
    }

    @Override
    public void requestRegister(User user, final RequestCallback<String> callback) {
        AndroidNetworking.post(ApiConstant.BASE_URL + "register")
                .addBodyParameter("name", user.getName())
                .addBodyParameter("email", user.getEmail())
                .addBodyParameter("password", user.getPassword())
                .build()
                .getAsObject(ResponseMessage.class, new ParsedRequestListener<ResponseMessage>() {
                    @Override
                    public void onResponse(ResponseMessage response) {
                        callback.requestSuccess(response.message);
                    }
                    @Override
                    public void onError(ANError error) {
                        if(error.getErrorCode() == 401)
                            callback.requestFailed(ErrorResponse.unauthorized);
                        else
                            callback.requestFailed(ErrorResponse.requestFailed);
                    }
                });
    }

}

package pens.lab.app.belajaractivity.modul.input;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

import pens.lab.app.belajaractivity.constant.ApiConstant;
import pens.lab.app.belajaractivity.model.Task;
import pens.lab.app.belajaractivity.response.ResponseMessage;
import pens.lab.app.belajaractivity.utils.RequestCallback;
import pens.lab.app.belajaractivity.utils.SharedPreferencesUtil;

public class InputInteractor implements InputContract.Interactor{
    private SharedPreferencesUtil sharedPreferencesUtil;

    public InputInteractor(SharedPreferencesUtil sharedPreferencesUtil) {
        this.sharedPreferencesUtil = sharedPreferencesUtil;
    }

    @Override
    public void requestAddTask(Task task, final RequestCallback<String> callback) {
        AndroidNetworking.post(ApiConstant.BASE_URL + "task")
                .addHeaders("Authorization", "Bearer " + sharedPreferencesUtil.getToken())
                .addBodyParameter("title", task.getTitle())
                .addBodyParameter("description", task.getDescription())
                .build()
                .getAsObject(ResponseMessage.class, new ParsedRequestListener<ResponseMessage>() {
                    @Override
                    public void onResponse(ResponseMessage response) {
                        callback.requestSuccess(response.message);
                    }
                    @Override
                    public void onError(ANError error) {
                        callback.requestFailed(error.getMessage());
                        Log.d("tag", error.getMessage() + error.getErrorCode());
                    }
                });
    }
}

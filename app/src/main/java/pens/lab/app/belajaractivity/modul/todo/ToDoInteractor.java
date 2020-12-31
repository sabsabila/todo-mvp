package pens.lab.app.belajaractivity.modul.todo;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import pens.lab.app.belajaractivity.constant.ApiConstant;
import pens.lab.app.belajaractivity.model.Task;
import pens.lab.app.belajaractivity.model.User;
import pens.lab.app.belajaractivity.api_response.ErrorResponse;
import pens.lab.app.belajaractivity.api_response.ResponseMessage;
import pens.lab.app.belajaractivity.api_response.ListTaskResponse;
import pens.lab.app.belajaractivity.api_response.UserResponse;
import pens.lab.app.belajaractivity.callback.RequestCallback;
import pens.lab.app.belajaractivity.utils.SharedPreferencesUtil;

public class ToDoInteractor implements ToDoContract.Interactor{
    private SharedPreferencesUtil sharedPreferencesUtil;

    public ToDoInteractor(SharedPreferencesUtil sharedPreferencesUtil) {
        this.sharedPreferencesUtil = sharedPreferencesUtil;
    }

    @Override
    public void requestCheck(List<Integer> id, String check,final RequestCallback<String> callback) {
        JSONObject idObject = getJson(id);
        AndroidNetworking.put(ApiConstant.BASE_URL + "task/check/" + check)
                .addHeaders("Authorization", "Bearer " + sharedPreferencesUtil.getToken())
                .addJSONObjectBody(idObject)
                .build()
                .getAsObject(ResponseMessage.class, new ParsedRequestListener<ResponseMessage>() {
                    @Override
                    public void onResponse(ResponseMessage response) {
                        callback.requestSuccess(response.message);
                    }
                    @Override
                    public void onError(ANError error) {
                        callback.requestFailed(ErrorResponse.requestFailed);
                    }
                });
    }

    private JSONObject getJson(List<Integer> id) {
        JSONArray jsonArray = new JSONArray();
        for(int i = 0; i < id.size(); i++){
            jsonArray.put(id.get(i));
        }
        JSONObject idObj = new JSONObject();
        try {
            idObj.put("id", jsonArray);
            return idObj;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return idObj;
    }

    @Override
    public void requestTasks(int check, final RequestCallback<List<Task>> callback) {
        AndroidNetworking.get(ApiConstant.BASE_URL + "task/check/" + check)
                .addHeaders("Authorization", "Bearer " + sharedPreferencesUtil.getToken())
                .build()
                .getAsObject(ListTaskResponse.class, new ParsedRequestListener<ListTaskResponse>() {
                    @Override
                    public void onResponse(ListTaskResponse response) {
                        callback.requestSuccess(response.tasks);
                    }
                    @Override
                    public void onError(ANError error) {
                        callback.requestFailed(ErrorResponse.requestFailed);
                        Log.d("tag", error.getMessage() + error.getErrorCode());
                    }
                });
    }

    @Override
    public void requestUser(final RequestCallback<User> callback) {
        AndroidNetworking.get(ApiConstant.BASE_URL + "user")
                .addHeaders("Authorization", "Bearer " + sharedPreferencesUtil.getToken())
                .build()
                .getAsObject(UserResponse.class, new ParsedRequestListener<UserResponse>() {
                    @Override
                    public void onResponse(UserResponse response) {
                        callback.requestSuccess(response.user);
                    }
                    @Override
                    public void onError(ANError error) {
                        callback.requestFailed(ErrorResponse.requestFailed);
                        Log.d("tag", error.getMessage() + error.getErrorCode());
                    }
                });
    }

    @Override
    public void requestDelete(int id, final RequestCallback<String> callback) {
        AndroidNetworking.delete(ApiConstant.BASE_URL + "task/" + id)
                .addHeaders("Authorization", "Bearer " + sharedPreferencesUtil.getToken())
                .build()
                .getAsObject(ResponseMessage.class, new ParsedRequestListener<ResponseMessage>() {
                    @Override
                    public void onResponse(ResponseMessage response) {
                        callback.requestSuccess(response.message);
                    }
                    @Override
                    public void onError(ANError error) {
                        callback.requestFailed(ErrorResponse.requestFailed);
                    }
                });
    }

    @Override
    public void logout() {
        sharedPreferencesUtil.clear();
    }
}

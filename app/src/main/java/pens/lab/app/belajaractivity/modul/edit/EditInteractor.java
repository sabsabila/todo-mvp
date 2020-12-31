package pens.lab.app.belajaractivity.modul.edit;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

import pens.lab.app.belajaractivity.constant.ApiConstant;
import pens.lab.app.belajaractivity.model.Task;
import pens.lab.app.belajaractivity.api_response.ErrorResponse;
import pens.lab.app.belajaractivity.api_response.ResponseMessage;
import pens.lab.app.belajaractivity.api_response.TaskResponse;
import pens.lab.app.belajaractivity.callback.RequestCallback;
import pens.lab.app.belajaractivity.utils.SharedPreferencesUtil;

public class EditInteractor implements EditContract.Interactor{
    private SharedPreferencesUtil sharedPreferencesUtil;

    public EditInteractor(SharedPreferencesUtil sharedPreferencesUtil) {
        this.sharedPreferencesUtil = sharedPreferencesUtil;
    }

    @Override
    public void requestEdit(int id, Task task, final RequestCallback<String> callback) {
        AndroidNetworking.put(ApiConstant.BASE_URL + "task/" + id)
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
                        callback.requestFailed(ErrorResponse.requestFailed);
                    }
                });
    }

    @Override
    public void requestTask(int id, final RequestCallback<Task> callback) {
        AndroidNetworking.get(ApiConstant.BASE_URL + "task/" + id)
                .addHeaders("Authorization", "Bearer " + sharedPreferencesUtil.getToken())
                .build()
                .getAsObject(TaskResponse.class, new ParsedRequestListener<TaskResponse>() {
                    @Override
                    public void onResponse(TaskResponse task) {
                        callback.requestSuccess(task.task);
                    }
                    @Override
                    public void onError(ANError error) {
                        callback.requestFailed(ErrorResponse.requestFailed);
                    }
                });
    }
}

package pens.lab.app.belajaractivity.modul.edit;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import pens.lab.app.belajaractivity.constant.ApiConstant;
import pens.lab.app.belajaractivity.model.Task;
import pens.lab.app.belajaractivity.response.PostTaskResponse;

public class EditPresenter implements EditContract.Presenter{

    private final EditContract.View view;

    public EditPresenter(EditContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {}

    @Override
    public void performEdit(int id, Task task){
        JSONObject taskJson = createTaskJson(task);
        AndroidNetworking.put(ApiConstant.BASE_URL + "/" + id)
                .addJSONObjectBody(taskJson)
                .build()
                .getAsObject(PostTaskResponse.class, new ParsedRequestListener<PostTaskResponse>() {
                    @Override
                    public void onResponse(PostTaskResponse response) {
                        Log.d("tag", response.status);
                        view.returnSuccess(response.status);
                        view.endLoading();
                    }
                    @Override
                    public void onError(ANError error) {
                        view.showError("Failed to save data !");
                        view.endLoading();
                        Log.d("tag", error.getMessage() + error.getErrorCode());
                    }
                });
    }

    @Override
    public void getTask(int id) {
        view.startLoading();
        AndroidNetworking.get(ApiConstant.BASE_URL + "/" + id)
                .build()
                .getAsObject(PostTaskResponse.class, new ParsedRequestListener<PostTaskResponse>() {
                    @Override
                    public void onResponse(PostTaskResponse response) {
                        Log.d("tag", response.status);
                        view.setTask(response.data);
                        view.endLoading();
                    }
                    @Override
                    public void onError(ANError error) {
                        view.showError("Failed to load data !");
                        view.endLoading();
                        Log.d("tag", error.getMessage() + error.getErrorCode());
                    }
                });
    }

    private JSONObject createTaskJson(Task newTask){
        JSONObject task = new JSONObject();
        JSONObject taskObj = new JSONObject();
        try{
            task.put("title", newTask.getTitle());
            task.put("description", newTask.getDescription());
            taskObj.put("task", task);
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return  taskObj;
    }
}

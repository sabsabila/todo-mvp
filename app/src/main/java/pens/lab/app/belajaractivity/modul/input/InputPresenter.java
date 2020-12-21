package pens.lab.app.belajaractivity.modul.input;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import pens.lab.app.belajaractivity.constant.ApiConstant;
import pens.lab.app.belajaractivity.model.Task;
import pens.lab.app.belajaractivity.response.PostTaskResponse;

public class InputPresenter implements InputContract.Presenter{

    private final InputContract.View view;

    public InputPresenter(InputContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {}

    @Override
    public void performAdd(Task task){
        JSONObject taskJson = createTaskJson(task);
        AndroidNetworking.post(ApiConstant.BASE_URL)
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

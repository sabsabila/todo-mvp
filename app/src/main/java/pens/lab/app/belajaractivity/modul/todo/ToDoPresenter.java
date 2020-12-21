package pens.lab.app.belajaractivity.modul.todo;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

import pens.lab.app.belajaractivity.constant.ApiConstant;
import pens.lab.app.belajaractivity.response.DeleteTaskResponse;
import pens.lab.app.belajaractivity.response.TaskResponse;

public class ToDoPresenter implements ToDoContract.Presenter{
    private final ToDoContract.View view;

    public ToDoPresenter(ToDoContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {}

    @Override
    public void inputItem(){
        view.redirectToInput();
    }

    @Override
    public void getTasks() {
        AndroidNetworking.get(ApiConstant.BASE_URL)
                .build()
                .getAsObject(TaskResponse.class, new ParsedRequestListener<TaskResponse>() {
                    @Override
                    public void onResponse(TaskResponse task) {
                        if(task == null){
                            view.showError("Null Response");
                            Log.d("tag", "response null");
                            view.endLoading();
                        }
                        else {
                            view.setTask(task.data);
                            view.endLoading();
                            Log.d("tag", "bisa gan");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        view.showError("Error loading task");
                        Log.d("tag", "error gan" + anError.getMessage() + anError.getErrorCode());
                        view.endLoading();
                    }
                });
    }

    public void editList(int id){
        view.redirectToEdit(id);
    }

    public void deleteItem(int id){
        AndroidNetworking.delete(ApiConstant.BASE_URL + "/" + id)
                .build()
                .getAsObject(DeleteTaskResponse.class, new ParsedRequestListener<DeleteTaskResponse>() {
                    @Override
                    public void onResponse(DeleteTaskResponse response) {
                        Log.d("tag", response.status);
                        view.returnSuccess(response.status);
                        view.endLoading();
                    }
                    @Override
                    public void onError(ANError error) {
                        view.showError("Failed to delete data !");
                        view.endLoading();
                        Log.d("tag", error.getMessage() + error.getErrorCode());
                    }
                });
    }

}

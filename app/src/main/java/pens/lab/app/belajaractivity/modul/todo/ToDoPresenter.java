package pens.lab.app.belajaractivity.modul.todo;

import java.util.List;

import pens.lab.app.belajaractivity.model.Task;
import pens.lab.app.belajaractivity.model.User;
import pens.lab.app.belajaractivity.utils.RequestCallback;

public class ToDoPresenter implements ToDoContract.Presenter{
    private final ToDoContract.View view;
    private final ToDoContract.Interactor interactor;
    private String message;

    public ToDoPresenter(ToDoContract.View view, ToDoContract.Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void start() {}

    @Override
    public void inputItem(){
        view.redirectToInput();
    }

    @Override
    public void getTasks() {
        interactor.requestTasks(0, new RequestCallback<List<Task>>() {
            @Override
            public void requestSuccess(List<Task> data) {
                view.setTask(data);
                view.endLoading();
            }

            @Override
            public void requestFailed(String errorMessage) {
                view.showError(errorMessage);
                view.endLoading();
            }
        });

        interactor.requestTasks(1, new RequestCallback<List<Task>>() {
            @Override
            public void requestSuccess(List<Task> data) {
                view.setCheckedTask(data);
                view.endLoading();
            }

            @Override
            public void requestFailed(String errorMessage) {
                view.showError(errorMessage);
                view.endLoading();
            }
        });
    }

    public void editList(int id){
        view.redirectToEdit(id);
    }

    public void deleteItem(int id){
        interactor.requestDelete(id, new RequestCallback<String>() {
            @Override
            public void requestSuccess(String data) {
                view.returnSuccess(data);
                view.endLoading();
            }

            @Override
            public void requestFailed(String errorMessage) {
                view.showError(errorMessage);
                view.endLoading();
            }
        });
    }

    @Override
    public void checkTasks(List<Integer> id) {
        view.startLoading();
        for(int i = 0; i < id.size(); i++){
            interactor.requestCheck(id.get(i), "1", new RequestCallback<String>() {
                @Override
                public void requestSuccess(String data) {
                    setMessage("success");
                }

                @Override
                public void requestFailed(String errorMessage) {
                    setMessage("error");
                }
            });
        }
        view.checkSuccess();
    }

    @Override
    public void uncheckTask(List<Integer> id) {

    }

    @Override
    public void getUser() {
        interactor.requestUser(new RequestCallback<User>() {
            @Override
            public void requestSuccess(User data) {
                view.setUser(data);
            }

            @Override
            public void requestFailed(String errorMessage) {
                view.showError(errorMessage);
            }
        });
    }

    @Override
    public void logout() {
        interactor.logout();
        view.logout();
    }

}

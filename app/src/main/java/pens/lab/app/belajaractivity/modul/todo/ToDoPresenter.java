package pens.lab.app.belajaractivity.modul.todo;

import java.util.List;

import pens.lab.app.belajaractivity.model.Task;
import pens.lab.app.belajaractivity.model.User;
import pens.lab.app.belajaractivity.callback.RequestCallback;

public class ToDoPresenter implements ToDoContract.Presenter{
    private final ToDoContract.View view;
    private final ToDoContract.Interactor interactor;

    public ToDoPresenter(ToDoContract.View view, ToDoContract.Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void start() {}

    @Override
    public void inputItem(){
        view.redirectToInput();
    }

    @Override
    public void goToList(int tag) {
        view.redirectToList(tag);
    }

    @Override
    public void getTasks(int tag) {
        view.startLoading();
        interactor.requestTasks(tag, new RequestCallback<List<Task>>() {
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
    }

    public void editList(int id){
        view.redirectToEdit(id);
    }

    public void deleteItem(int id){
        view.startLoading();
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
    public void checkTasks(List<Integer> id, int tag) {
        view.startLoading();
        interactor.requestCheck(id, String.valueOf(tag), new RequestCallback<String>() {
            @Override
            public void requestSuccess(String data) {
                view.checkSuccess();
            }
            @Override
            public void requestFailed(String errorMessage) {
                view.showError(errorMessage);
            }
        });
    }

    @Override
    public void getUser() {
        view.startLoading();
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

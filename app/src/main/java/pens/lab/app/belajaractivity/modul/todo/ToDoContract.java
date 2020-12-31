package pens.lab.app.belajaractivity.modul.todo;

import java.util.List;

import pens.lab.app.belajaractivity.base.BasePresenter;
import pens.lab.app.belajaractivity.base.BaseView;
import pens.lab.app.belajaractivity.model.Task;
import pens.lab.app.belajaractivity.model.User;
import pens.lab.app.belajaractivity.callback.RequestCallback;

public interface ToDoContract {
    interface View extends BaseView<Presenter> {
        void redirectToInput();
        void redirectToEdit(int id);
        void showAlertDialog(final int position);
        void showLogoutAlert();
        void setTask(List<Task> task);
        void setCheckedTask(List<Task> task);
        void startLoading();
        void endLoading();
        void showError(String message);
        void returnSuccess(String message);
        void checkSuccess();
        void setUser(User user);
        void logout();
    }

    interface Presenter extends BasePresenter {
        void inputItem();
        void getTasks();
        void editList(final int index);
        void deleteItem(final int position);
        void checkTasks(List<Integer> id);
        void uncheckTasks(List<Integer> id);
        void getUser();
        void logout();
    }

    interface Interactor{
        void requestCheck(List<Integer> id, String check, RequestCallback<String> callback);
        void requestTasks(int check, RequestCallback<List<Task>> callback);
        void requestUser(RequestCallback<User> callback);
        void requestDelete(int id, RequestCallback<String> callback);
        void logout();
    }
}

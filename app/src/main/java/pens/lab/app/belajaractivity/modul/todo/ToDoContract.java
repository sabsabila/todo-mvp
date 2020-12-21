package pens.lab.app.belajaractivity.modul.todo;

import java.util.ArrayList;
import java.util.List;

import pens.lab.app.belajaractivity.base.BasePresenter;
import pens.lab.app.belajaractivity.base.BaseView;
import pens.lab.app.belajaractivity.model.Task;

public interface ToDoContract {
    interface View extends BaseView<Presenter> {
        void redirectToInput();
        void redirectToEdit(int id);
        void showAlertDialog(final int position);
        void setTask(List<Task> task);
        void startLoading();
        void endLoading();
        void showError(String message);
        void returnSuccess(String message);
    }

    interface Presenter extends BasePresenter {
        void inputItem();
        void getTasks();
        void editList(final int index);
        void deleteItem(final int position);
    }
}

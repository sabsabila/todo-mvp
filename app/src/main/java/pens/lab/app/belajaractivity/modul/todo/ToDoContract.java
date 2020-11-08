package pens.lab.app.belajaractivity.modul.todo;

import java.util.ArrayList;

import pens.lab.app.belajaractivity.base.BasePresenter;
import pens.lab.app.belajaractivity.base.BaseView;
import pens.lab.app.belajaractivity.model.Task;

public interface ToDoContract {
    interface View extends BaseView<Presenter> {
        void redirectToInput();
        void emptyList();
        void showInputBox(Task oldItem, final int index);
        void showAlertDialog(final int position);
    }

    interface Presenter extends BasePresenter {
        void inputItem();
        void clearList();
        void editList(Task oldItem, final int index);
        void deleteItem(final int position);
    }
}

package pens.lab.app.belajaractivity.modul.todo;

import pens.lab.app.belajaractivity.base.BasePresenter;
import pens.lab.app.belajaractivity.base.BaseView;

public interface ToDoContract {
    interface View extends BaseView<Presenter> {
        void redirectToInput();
        void emptyList();
        void showInputBox(String oldItem, final int index);
        void showAlertDialog(final int position);
    }

    interface Presenter extends BasePresenter {
        void inputItem();
        void clearList();
        void editList(String oldItem, final int index);
        void deleteItem(final int position);
    }
}

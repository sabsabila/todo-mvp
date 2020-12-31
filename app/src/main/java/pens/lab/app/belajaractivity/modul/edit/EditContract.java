package pens.lab.app.belajaractivity.modul.edit;

import pens.lab.app.belajaractivity.base.BasePresenter;
import pens.lab.app.belajaractivity.base.BaseView;
import pens.lab.app.belajaractivity.model.Task;
import pens.lab.app.belajaractivity.callback.RequestCallback;

public interface EditContract {
    interface View extends BaseView<Presenter> {
        void startLoading();
        void endLoading();
        void showError(String message);
        void returnSuccess(String message);
        void setTask(Task task);
    }

    interface Presenter extends BasePresenter {
        void performEdit(int id, Task task);
        void getTask(int id);
    }

    interface Interactor{
        void requestEdit(int id, Task task, RequestCallback<String> callback);
        void requestTask(int id, RequestCallback<Task> callback);
    }
}

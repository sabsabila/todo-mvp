package pens.lab.app.belajaractivity.modul.input;

import pens.lab.app.belajaractivity.base.BasePresenter;
import pens.lab.app.belajaractivity.base.BaseView;
import pens.lab.app.belajaractivity.model.Task;
import pens.lab.app.belajaractivity.callback.RequestCallback;

public interface InputContract {
    interface View extends BaseView<Presenter> {
        void startLoading();
        void endLoading();
        void redirectToList(int tag);
        void showError(String message);
        void returnSuccess(String message);
    }

    interface Presenter extends BasePresenter {
        void performAdd(Task task);
        void goToList(int tag);
    }

    interface Interactor {
        void requestAddTask(Task task, RequestCallback<String> callback);
    }
}

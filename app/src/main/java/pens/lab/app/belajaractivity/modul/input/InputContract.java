package pens.lab.app.belajaractivity.modul.input;

import pens.lab.app.belajaractivity.base.BasePresenter;
import pens.lab.app.belajaractivity.base.BaseView;
import pens.lab.app.belajaractivity.model.Task;
import pens.lab.app.belajaractivity.utils.RequestCallback;

public interface InputContract {
    interface View extends BaseView<Presenter> {
        void startLoading();
        void endLoading();
        void showError(String message);
        void returnSuccess(String message);
    }

    interface Presenter extends BasePresenter {
        void performAdd(Task task);
    }

    interface Interactor {
        void requestAddTask(Task task, RequestCallback<String> callback);
    }
}

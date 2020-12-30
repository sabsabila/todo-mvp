package pens.lab.app.belajaractivity.modul.register;

import pens.lab.app.belajaractivity.base.BasePresenter;
import pens.lab.app.belajaractivity.base.BaseView;
import pens.lab.app.belajaractivity.model.User;
import pens.lab.app.belajaractivity.utils.RequestCallback;

public interface RegisterContract {
    interface View extends BaseView<Presenter> {
        void redirectToLogin();
        void showError(String message);
        void returnSuccess(String message);
        void startLoading();
        void endLoading();
    }

    interface Presenter extends BasePresenter {
        void performRegister(User user);
    }

    interface Interactor{
        void requestRegister(User user, RequestCallback<String> callback);
    }
}

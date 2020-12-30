package pens.lab.app.belajaractivity.modul.login;

import pens.lab.app.belajaractivity.base.BasePresenter;
import pens.lab.app.belajaractivity.base.BaseView;
import pens.lab.app.belajaractivity.utils.RequestCallback;

public interface LoginContract {
    interface View extends BaseView<Presenter> {
        void redirectToList();
        void showError(String message);
        void startLoading();
        void endLoading();
    }

    interface Presenter extends BasePresenter {
        void performLogin(String email, String password);
    }

    interface Interactor{
        void requestLogin(String email, String password, RequestCallback<String> callback);
        void saveToken(String token);
    }
}

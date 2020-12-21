package pens.lab.app.belajaractivity.modul.login;

import pens.lab.app.belajaractivity.base.BasePresenter;
import pens.lab.app.belajaractivity.base.BaseView;

/**
 * Created by fahrul on 13/03/19.
 */

public interface LoginContract {
    interface View extends BaseView<Presenter> {
        void redirectToList();
    }

    interface Presenter extends BasePresenter {
        void performLogin(String username, String password);
    }
}

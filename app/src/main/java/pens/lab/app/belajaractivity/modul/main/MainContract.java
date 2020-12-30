package pens.lab.app.belajaractivity.modul.main;

import pens.lab.app.belajaractivity.base.BasePresenter;
import pens.lab.app.belajaractivity.base.BaseView;

public interface MainContract{
    interface View extends BaseView<Presenter> {
        void whenUserLogin();
        void whenUserNotLogin();
    }

    interface Presenter extends BasePresenter {
        void checkIsUserLogin();
    }

    interface Interactor {
        boolean isUserLogin();
    }
}

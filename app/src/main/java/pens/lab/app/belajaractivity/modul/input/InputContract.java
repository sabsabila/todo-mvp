package pens.lab.app.belajaractivity.modul.input;

import pens.lab.app.belajaractivity.base.BasePresenter;
import pens.lab.app.belajaractivity.base.BaseView;

public interface InputContract {
    interface View extends BaseView<Presenter> {
        void addElement();
    }

    interface Presenter extends BasePresenter {
        void performAdd();
    }
}

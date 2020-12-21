package pens.lab.app.belajaractivity.modul.login;

import pens.lab.app.belajaractivity.utils.UtilProvider;

public class LoginPresenter implements LoginContract.Presenter{
    private final LoginContract.View view;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {}

    @Override
    public void performLogin(final String username, final String password){
        UtilProvider.getSharedPreferencesUtil().setUsername(username);
        view.redirectToList();
    }

}

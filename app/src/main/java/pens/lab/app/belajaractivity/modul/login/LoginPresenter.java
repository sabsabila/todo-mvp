package pens.lab.app.belajaractivity.modul.login;

import pens.lab.app.belajaractivity.utils.Database;

/**
 * Created by fahrul on 13/03/19.
 */

public class LoginPresenter implements LoginContract.Presenter{
    private final LoginContract.View view;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {}

    @Override
    public void performLogin(final String email, final String password){
        //proses login
        //if login success call redirect to profile
        Database.getInstance().setLoggedInUser(email);
        view.redirectToList();
    }

}

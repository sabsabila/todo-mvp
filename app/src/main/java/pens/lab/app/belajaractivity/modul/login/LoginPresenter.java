package pens.lab.app.belajaractivity.modul.login;

import pens.lab.app.belajaractivity.model.User;
import pens.lab.app.belajaractivity.utils.Database;
import pens.lab.app.belajaractivity.utils.session.SessionRepository;

/**
 * Created by fahrul on 13/03/19.
 */

public class LoginPresenter implements LoginContract.Presenter{
    private final LoginContract.View view;
    private final SessionRepository sessionRepository;

    public LoginPresenter(LoginContract.View view, SessionRepository sessionRepository) {
        this.view = view;
        this.sessionRepository = sessionRepository;
    }

    @Override
    public void start() {}

    @Override
    public void performLogin(final String email, final String password){
        //proses login
        //if login success call redirect to profile
        User loggedUser = new User(email, "TOKEN123456");                                    //new
        sessionRepository.setSessionData(loggedUser);
        Database.getInstance().setLoggedInUser(email);
        view.redirectToList();
    }

}

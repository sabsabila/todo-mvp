package pens.lab.app.belajaractivity.modul.main;

public class MainPresenter implements MainContract.Presenter{
    private MainContract.View view;
    private MainInteractor interactor;

    public MainPresenter(MainContract.View view, MainInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void checkIsUserLogin() {
        if (interactor.isUserLogin()) {
            view.whenUserLogin();
        } else {
            view.whenUserNotLogin();
        }
    }

    @Override
    public void start() {

    }
}

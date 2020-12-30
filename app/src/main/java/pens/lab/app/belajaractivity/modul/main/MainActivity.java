package pens.lab.app.belajaractivity.modul.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import pens.lab.app.belajaractivity.R;
import pens.lab.app.belajaractivity.modul.login.LoginActivity;
import pens.lab.app.belajaractivity.modul.todo.ToDoActivity;
import pens.lab.app.belajaractivity.utils.UtilProvider;

public class MainActivity extends AppCompatActivity implements  MainContract.View{
    private MainContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this, new MainInteractor(UtilProvider.getSharedPreferencesUtil()));
        presenter.checkIsUserLogin();
    }

    @Override
    public void whenUserLogin() {
        finish();
        startActivity(new Intent(getApplicationContext(), ToDoActivity.class));
    }

    @Override
    public void whenUserNotLogin() {
        finish();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {

    }
}

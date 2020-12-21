package pens.lab.app.belajaractivity.modul.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import pens.lab.app.belajaractivity.R;
import pens.lab.app.belajaractivity.base.BaseFragment;
import pens.lab.app.belajaractivity.modul.todo.ToDoActivity;

public class LoginFragment extends BaseFragment<LoginActivity, LoginContract.Presenter> implements LoginContract.View {

    EditText etEmail;
    EditText etPassword;
    Button btnLogin;


    public LoginFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        fragmentView = inflater.inflate(R.layout.fragment_login, container, false);
        mPresenter = new LoginPresenter(this);
        mPresenter.start();

        etEmail = fragmentView.findViewById(R.id.et_email);
        etPassword = fragmentView.findViewById(R.id.et_password);
        btnLogin = fragmentView.findViewById(R.id.bt_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBtLoginClick();
            }
        });
        return fragmentView;
    }

    public void setBtLoginClick(){
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        mPresenter.performLogin(email,password);
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void redirectToList() {
            Intent intent = new Intent(activity, ToDoActivity.class);
            startActivity(intent);
            activity.finish();
    }
}

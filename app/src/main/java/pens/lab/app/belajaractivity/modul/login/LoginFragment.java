package pens.lab.app.belajaractivity.modul.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import pens.lab.app.belajaractivity.R;
import pens.lab.app.belajaractivity.base.BaseFragment;
import pens.lab.app.belajaractivity.modul.register.RegisterActivity;
import pens.lab.app.belajaractivity.modul.todo.ToDoActivity;
import pens.lab.app.belajaractivity.utils.UtilProvider;

public class LoginFragment extends BaseFragment<LoginActivity, LoginContract.Presenter> implements LoginContract.View {

    EditText etEmail;
    EditText etPassword;
    Button btnLogin;
    Button btnRegister;
    Button googleBtn;
    GoogleSignInClient mGoogleSignInClient;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        fragmentView = inflater.inflate(R.layout.fragment_login, container, false);
        initView();
        setOnClickListener();
        initGSO();
        return fragmentView;
    }

    private void initGSO(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.server_client_id))
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(activity);
        if(account != null)
            mGoogleSignInClient.signOut();
    }

    private void setOnClickListener() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBtLoginClick();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, RegisterActivity.class));
            }
        });
        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignIn();
            }
        });
    }

    private void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            mPresenter.performGoogleLogin(account.getEmail(), account.getDisplayName());
        } catch (ApiException e) {
            Toast.makeText(activity, "Failed to sign in with google", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView(){
        mPresenter = new LoginPresenter(this, new LoginInteractor(UtilProvider.getSharedPreferencesUtil()));
        mPresenter.start();
        etEmail = fragmentView.findViewById(R.id.et_email);
        etPassword = fragmentView.findViewById(R.id.et_password);
        btnLogin = fragmentView.findViewById(R.id.bt_login);
        btnRegister = fragmentView.findViewById(R.id.bt_register);
        googleBtn = fragmentView.findViewById(R.id.google_button);
    }

    @Override
    public void startLoading() {
        fragmentView.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
    }

    @Override
    public void endLoading() {
        fragmentView.findViewById(R.id.progress_bar).setVisibility(View.GONE);
    }

    public void setBtLoginClick(){
        if(etEmail.getText() != null && etPassword.getText() != null){
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            mPresenter.performLogin(email,password);
        }else
            Toast.makeText(activity, "Email & Password Must be Filled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void redirectToList() {
            Intent intent = new Intent(activity, ToDoActivity.class);
            intent.putExtra("tag", 0);
            startActivity(intent);
            activity.finish();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }
}

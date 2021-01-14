package pens.lab.app.belajaractivity.modul.login;

import android.view.View;

import pens.lab.app.belajaractivity.base.BaseFragmentHolderActivity;


public class LoginActivity extends BaseFragmentHolderActivity {
    LoginFragment loginFragment;
    private final int UPDATE_REQUEST = 2019;

    @Override
    protected void initializeFragment() {
        initializeView();

        toolbar.setVisibility(View.GONE);
        vMenuBarShadow.setVisibility(View.GONE);
        btBack.setVisibility(View.GONE);
        btOptionMenu.setVisibility(View.GONE);

        loginFragment = new LoginFragment();
        setCurrentFragment(loginFragment, false);
    }
}

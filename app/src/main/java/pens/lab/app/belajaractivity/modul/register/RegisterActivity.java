package pens.lab.app.belajaractivity.modul.register;

import android.view.View;

import pens.lab.app.belajaractivity.base.BaseFragmentHolderActivity;


public class RegisterActivity extends BaseFragmentHolderActivity {
    RegisterFragment registerFragment;
    private final int UPDATE_REQUEST = 2019;

    @Override
    protected void initializeFragment() {
        initializeView();

        toolbar.setVisibility(View.GONE);
        vMenuBarShadow.setVisibility(View.GONE);
        btBack.setVisibility(View.GONE);
        btOptionMenu.setVisibility(View.GONE);

        registerFragment = new RegisterFragment();
        setCurrentFragment(registerFragment, false);

    }




}

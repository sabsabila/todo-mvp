package pens.lab.app.belajaractivity.modul.input;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import pens.lab.app.belajaractivity.base.BaseFragmentHolderActivity;
import pens.lab.app.belajaractivity.model.Task;


public class InputActivity extends BaseFragmentHolderActivity {
    InputFragment inputFragment;
    private final int UPDATE_REQUEST = 2019;

    @Override
    protected void initializeFragment() {
        initializeView();

        //btBack.setVisibility(View.GONE);
        btOptionMenu.setVisibility(View.GONE);
        ivIcon.setVisibility(View.VISIBLE);

        inputFragment = new InputFragment();
        setCurrentFragment(inputFragment, true);

    }

}

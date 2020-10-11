package pens.lab.app.belajaractivity.modul.input;

import android.content.Intent;
import android.view.View;

import java.util.ArrayList;

import pens.lab.app.belajaractivity.base.BaseFragmentHolderActivity;


public class InputActivity extends BaseFragmentHolderActivity {
    InputFragment inputFragment;
    private final int UPDATE_REQUEST = 2019;

    @Override
    protected void initializeFragment() {
        initializeView();
        Intent intent = getIntent();

        ArrayList<String> toDoList = intent.getStringArrayListExtra("todo");
        //btBack.setVisibility(View.GONE);
        btOptionMenu.setVisibility(View.GONE);
        ivIcon.setVisibility(View.VISIBLE);

        inputFragment = new InputFragment(toDoList);
        setCurrentFragment(inputFragment, true);

    }

}

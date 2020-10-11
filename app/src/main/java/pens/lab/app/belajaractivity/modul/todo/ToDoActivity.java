package pens.lab.app.belajaractivity.modul.todo;

import android.content.Intent;
import android.view.View;

import java.util.ArrayList;

import pens.lab.app.belajaractivity.base.BaseFragmentHolderActivity;


public class ToDoActivity extends BaseFragmentHolderActivity {
    ToDoFragment toDoFragment;
    private final int UPDATE_REQUEST = 2019;

    @Override
    protected void initializeFragment() {
        initializeView();

        Intent dataIntent = getIntent();
        ArrayList<String> returnedData = dataIntent.getStringArrayListExtra("returnData");

        btBack.setVisibility(View.GONE);
        btOptionMenu.setVisibility(View.GONE);
        ivIcon.setVisibility(View.VISIBLE);

        toDoFragment = new ToDoFragment(returnedData);
        setCurrentFragment(toDoFragment, true);

    }

}

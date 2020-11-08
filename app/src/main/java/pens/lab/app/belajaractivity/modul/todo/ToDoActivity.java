package pens.lab.app.belajaractivity.modul.todo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import pens.lab.app.belajaractivity.base.BaseFragmentHolderActivity;
import pens.lab.app.belajaractivity.model.Task;


public class ToDoActivity extends BaseFragmentHolderActivity {
    ToDoFragment toDoFragment;
    private final int UPDATE_REQUEST = 2019;

    @Override
    protected void initializeFragment() {
        initializeView();
        ArrayList<Task> returnedData = new ArrayList<>();

        btBack.setVisibility(View.GONE);
        btOptionMenu.setVisibility(View.GONE);
        ivIcon.setVisibility(View.VISIBLE);

        toDoFragment = new ToDoFragment();
        setCurrentFragment(toDoFragment, true);

    }

}

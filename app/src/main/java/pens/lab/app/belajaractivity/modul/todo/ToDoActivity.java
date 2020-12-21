package pens.lab.app.belajaractivity.modul.todo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import pens.lab.app.belajaractivity.base.BaseFragmentHolderActivity;
import pens.lab.app.belajaractivity.model.Task;


public class ToDoActivity extends BaseFragmentHolderActivity {
    @Override
    protected void initializeFragment() {
        initializeView();

        btBack.setVisibility(View.GONE);
        btOptionMenu.setVisibility(View.GONE);

        ToDoFragment toDoFragment = new ToDoFragment();
        setCurrentFragment(toDoFragment, true);

    }

}

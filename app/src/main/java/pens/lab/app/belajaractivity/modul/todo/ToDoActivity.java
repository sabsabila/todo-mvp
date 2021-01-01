package pens.lab.app.belajaractivity.modul.todo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

import pens.lab.app.belajaractivity.R;
import pens.lab.app.belajaractivity.base.BaseFragmentHolderActivity;
import pens.lab.app.belajaractivity.model.Task;


public class ToDoActivity extends BaseFragmentHolderActivity {
    @Override
    protected void initializeFragment() {
        initializeView();

        btBack.setImageResource(R.drawable.logout_icon);
        btOptionMenu.setVisibility(View.GONE);

        ToDoFragment toDoFragment = new ToDoFragment(btBack);
        setCurrentFragment(toDoFragment, true);

    }

}

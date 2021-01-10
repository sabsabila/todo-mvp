package pens.lab.app.belajaractivity.modul.todo;

import android.content.Intent;
import android.view.View;
import pens.lab.app.belajaractivity.R;
import pens.lab.app.belajaractivity.base.BaseFragmentHolderActivity;

public class ToDoActivity extends BaseFragmentHolderActivity {

    @Override
    protected void initializeFragment() {
        initializeView();

        btBack.setImageResource(R.drawable.logout_icon);
        btOptionMenu.setVisibility(View.GONE);

        Intent intent = getIntent();
        int tag = intent.getIntExtra("tag", 0);

        ToDoFragment toDoFragment = new ToDoFragment(btBack, tag);
        setCurrentFragment(toDoFragment, true);
    }
}

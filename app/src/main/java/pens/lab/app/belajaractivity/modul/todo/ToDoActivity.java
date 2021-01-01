package pens.lab.app.belajaractivity.modul.todo;

import android.view.View;
import pens.lab.app.belajaractivity.R;
import pens.lab.app.belajaractivity.base.BaseFragmentHolderActivity;

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

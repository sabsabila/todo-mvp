package pens.lab.app.belajaractivity.modul.edit;

import android.content.Intent;
import android.view.View;

import pens.lab.app.belajaractivity.base.BaseFragmentHolderActivity;
import pens.lab.app.belajaractivity.modul.input.InputFragment;


public class EditActivity extends BaseFragmentHolderActivity {
    @Override
    protected void initializeFragment() {
        initializeView();

        //btBack.setVisibility(View.GONE);
        btOptionMenu.setVisibility(View.GONE);

        Intent intent = getIntent();

        EditFragment editFragment = new EditFragment(intent.getIntExtra("id",0));
        setCurrentFragment(editFragment, true);
    }
}

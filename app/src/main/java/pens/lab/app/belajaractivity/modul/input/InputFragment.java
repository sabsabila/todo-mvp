package pens.lab.app.belajaractivity.modul.input;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import pens.lab.app.belajaractivity.R;
import pens.lab.app.belajaractivity.base.BaseFragment;
import pens.lab.app.belajaractivity.model.Task;
import pens.lab.app.belajaractivity.modul.todo.ToDoActivity;
import pens.lab.app.belajaractivity.utils.Database;

public class InputFragment extends BaseFragment<InputActivity, InputContract.Presenter> implements InputContract.View {

    Button inputButton;
    EditText newDataTitle;
    EditText newDataDescription;
    ArrayList<Task> todo;
    Database db;

    public InputFragment() {
        this.db = Database.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        fragmentView = inflater.inflate(R.layout.fragment_input, container, false);
        mPresenter = new InputPresenter(this);
        mPresenter.start();

        todo = db.getTasks();
        inputButton = fragmentView.findViewById(R.id.btnInput);
        newDataTitle = fragmentView.findViewById(R.id.inputTitle);
        newDataDescription = fragmentView.findViewById(R.id.inputDescription);

        inputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               mPresenter.performAdd();
            }
        });

        setTitle("Input New Item");

        return fragmentView;
    }

    @Override
    public void addElement(){
        if(newDataTitle.getText() != null && newDataDescription.getText() != null)
            db.addTask(newDataTitle.getText().toString(), newDataDescription.getText().toString());
        Intent returnIntent = new Intent(activity, ToDoActivity.class);
        startActivity(returnIntent);
    }

    @Override
    public void setPresenter(InputContract.Presenter presenter) {
        mPresenter = presenter;
    }

}

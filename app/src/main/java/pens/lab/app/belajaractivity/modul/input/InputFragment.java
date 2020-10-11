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
import pens.lab.app.belajaractivity.modul.todo.ToDoActivity;

public class InputFragment extends BaseFragment<InputActivity, InputContract.Presenter> implements InputContract.View {

    Button inputButton;
    EditText newData;
    ArrayList<String> todo;

    public InputFragment(ArrayList<String> toDo) {
        this.todo = toDo;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        fragmentView = inflater.inflate(R.layout.fragment_input, container, false);
        mPresenter = new InputPresenter(this);
        mPresenter.start();

        inputButton = fragmentView.findViewById(R.id.btnInput);
        newData = fragmentView.findViewById(R.id.inputData);

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
        if(newData.getText() != null)
            todo.add(newData.getText().toString());
        Intent returnIntent = new Intent(activity, ToDoActivity.class);
        returnIntent.putStringArrayListExtra("returnData", todo);
        startActivity(returnIntent);
    }

    @Override
    public void setPresenter(InputContract.Presenter presenter) {
        mPresenter = presenter;
    }

}

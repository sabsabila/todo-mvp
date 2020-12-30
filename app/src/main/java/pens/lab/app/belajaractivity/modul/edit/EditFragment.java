package pens.lab.app.belajaractivity.modul.edit;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import pens.lab.app.belajaractivity.R;
import pens.lab.app.belajaractivity.base.BaseFragment;
import pens.lab.app.belajaractivity.model.Task;
import pens.lab.app.belajaractivity.modul.input.InputActivity;
import pens.lab.app.belajaractivity.modul.todo.ToDoActivity;
import pens.lab.app.belajaractivity.utils.UtilProvider;

public class EditFragment extends BaseFragment<InputActivity, EditContract.Presenter> implements EditContract.View {

    Button inputButton;
    EditText newDataTitle;
    EditText newDataDescription;
    int id;

    public EditFragment(int id) {
        this.id = id;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        fragmentView = inflater.inflate(R.layout.fragment_input, container, false);
        initView();
        setOnClickListener();
        return fragmentView;
    }

    private void setOnClickListener() {
        inputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(newDataTitle.getText() != null && newDataDescription.getText() != null){
                    Task newTask = new Task(newDataTitle.getText().toString(), newDataDescription.getText().toString());
                    startLoading();
                    mPresenter.performEdit(id, newTask);
                }
            }
        });
    }

    private void initView() {
        mPresenter = new EditPresenter(this, new EditInteractor(UtilProvider.getSharedPreferencesUtil()));
        mPresenter.start();
        mPresenter.getTask(id);
        inputButton = fragmentView.findViewById(R.id.btnInput);
        inputButton.setText("Save");
        newDataTitle = fragmentView.findViewById(R.id.inputTitle);
        newDataDescription = fragmentView.findViewById(R.id.inputDescription);
        setTitle("Edit Item");
    }

    @Override
    public void startLoading() {
        fragmentView.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
    }

    @Override
    public void endLoading() {
        fragmentView.findViewById(R.id.progress_bar).setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(activity, message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void returnSuccess(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(activity, ToDoActivity.class));
    }

    @Override
    public void setTask(Task task) {
        newDataTitle.setText(task.getTitle());
        newDataDescription.setText(task.getDescription());
    }

    @Override
    public void setPresenter(EditContract.Presenter presenter) {

    }
}

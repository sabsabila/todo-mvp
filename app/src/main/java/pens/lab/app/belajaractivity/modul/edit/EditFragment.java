package pens.lab.app.belajaractivity.modul.edit;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Calendar;

import pens.lab.app.belajaractivity.R;
import pens.lab.app.belajaractivity.base.BaseFragment;
import pens.lab.app.belajaractivity.model.Task;
import pens.lab.app.belajaractivity.modul.input.InputActivity;
import pens.lab.app.belajaractivity.modul.todo.ToDoActivity;
import pens.lab.app.belajaractivity.utils.UtilProvider;

public class EditFragment extends BaseFragment<InputActivity, EditContract.Presenter> implements EditContract.View {

    Button saveButton;
    EditText dataTitle;
    EditText dataDescription;
    EditText dueDateEt;
    ImageButton taskListButton;
    ImageButton finishedTaskButton;
    String dueDate;
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
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task newTask = null;
                if(dataTitle.getText() != null && dataDescription.getText() != null){
                    if(dueDate != null)
                        newTask = new Task(dataTitle.getText().toString(), dataDescription.getText().toString(), dueDate);
                    else
                        newTask = new Task(dataTitle.getText().toString(), dataDescription.getText().toString(), "null");
                    mPresenter.performEdit(id, newTask);
                }else
                    Toast.makeText(activity, "Title & Description Cannot be Empty", Toast.LENGTH_SHORT).show();
            }
        });
        dueDateEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDueDateClick();
            }
        });
        taskListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.goToList(0);
            }
        });
        finishedTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.goToList(1);
            }
        });
    }

    private void initView() {
        mPresenter = new EditPresenter(this, new EditInteractor(UtilProvider.getSharedPreferencesUtil()));
        mPresenter.start();
        saveButton = fragmentView.findViewById(R.id.btnInput);
        saveButton.setText("Save");
        dataTitle = fragmentView.findViewById(R.id.inputTitle);
        taskListButton = fragmentView.findViewById(R.id.task_button);
        finishedTaskButton = fragmentView.findViewById(R.id.finished_task_button);
        dataDescription = fragmentView.findViewById(R.id.inputDescription);
        dueDateEt = fragmentView.findViewById(R.id.inputDueDate);
        mPresenter.getTask(id);
        setTitle("Edit Item");
    }

    private void onDueDateClick() {
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(activity, R.style.MyDatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month+1;
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month-1);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                dueDate = year+"-"+month+"-"+day;
                CharSequence date = DateFormat.format("EEE, d MMM yyyy", calendar);
                dueDateEt.setText(date);
            }
        }, year, month, day);
        final long today = System.currentTimeMillis() - 1000;
        datePickerDialog.getDatePicker().setMinDate(today);
        datePickerDialog.show();
    }

    @Override
    public void redirectToList(int tag) {
        Intent intent = new Intent(activity, ToDoActivity.class);
        intent.putExtra("tag", tag);
        startActivity(intent);
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
        dataTitle.setText(task.getTitle());
        dataDescription.setText(task.getDescription());
        if(task.getDue_date() != null) {
            String[] date = task.getDue_date().split("-");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, Integer.parseInt(date[0]));
            calendar.set(Calendar.MONTH, Integer.parseInt(date[1])-1);
            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date[2]));
            CharSequence dateFormatted = DateFormat.format("EEE, d MMM yyyy", calendar);
            dueDateEt.setText(dateFormatted);
            dueDate = task.getDue_date();
        }
    }

    @Override
    public void setPresenter(EditContract.Presenter presenter) {

    }
}

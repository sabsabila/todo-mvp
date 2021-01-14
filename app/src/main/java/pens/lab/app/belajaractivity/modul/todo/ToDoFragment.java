package pens.lab.app.belajaractivity.modul.todo;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import pens.lab.app.belajaractivity.R;
import pens.lab.app.belajaractivity.base.BaseFragment;
import pens.lab.app.belajaractivity.model.Task;
import pens.lab.app.belajaractivity.model.User;
import pens.lab.app.belajaractivity.modul.edit.EditActivity;
import pens.lab.app.belajaractivity.modul.input.InputActivity;
import pens.lab.app.belajaractivity.modul.login.LoginActivity;
import pens.lab.app.belajaractivity.adapter.ListTaskAdapter;
import pens.lab.app.belajaractivity.utils.AlarmReceiver;
import pens.lab.app.belajaractivity.utils.UtilProvider;

public class ToDoFragment extends BaseFragment<ToDoActivity, ToDoContract.Presenter> implements ToDoContract.View, ListTaskAdapter.MyClickListener, ListTaskAdapter.MyOnCheckedListener{

    ImageButton addButton, btBack, taskListButton, finishedTaskButton;
    ImageView emptyIcon;
    TextView titleTaskTv, tvNoTask;
    Button actionButton;
    TextView title;
    RecyclerView taskListRecyclerView;
    List<Task> tasks;
    ListTaskAdapter taskListAdapter;
    int checkTag;
    private RecyclerView.LayoutManager mLayoutManager;

    public ToDoFragment(ImageButton btBack, int tag) {
        this.btBack = btBack;
        this.checkTag = tag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        fragmentView = inflater.inflate(R.layout.fragment_todolist, container, false);
        initView();
        setOnClickListener();
        return fragmentView;
    }

    private void initView() {
        mPresenter = new ToDoPresenter(this, new ToDoInteractor(UtilProvider.getSharedPreferencesUtil()));
        mPresenter.start();
        findViewById();
        taskListRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(activity);
        taskListRecyclerView.setLayoutManager(mLayoutManager);
        startLoading();
        mPresenter.getTasks(checkTag);
        mPresenter.getUser();
        adjustView();
        createNotificationChannel();
    }

    private void adjustView(){
        setTitle("To Do List");
        if(checkTag == 1){
            actionButton.setText("Unfinish Task");
            titleTaskTv.setText("Finished Tasks");
            finishedTaskButton.setBackgroundResource(R.color.navbar);
        }else{
            taskListButton.setBackgroundResource(R.color.navbar);
        }
    }

    private void findViewById(){
        taskListButton = fragmentView.findViewById(R.id.task_button);
        titleTaskTv = fragmentView.findViewById(R.id.titleTask);
        finishedTaskButton = fragmentView.findViewById(R.id.finished_task_button);
        addButton = fragmentView.findViewById(R.id.btnAdd);
        actionButton = fragmentView.findViewById(R.id.taskButtonAction);
        title = fragmentView.findViewById(R.id.titleText);
        taskListRecyclerView = fragmentView.findViewById(R.id.recyclerViewTodolist);
        emptyIcon = fragmentView.findViewById(R.id.emptyIcon);
        tvNoTask = fragmentView.findViewById(R.id.tvNotask);
    }

    private void setOnClickListener() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.inputItem();
            }
        });
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutAlert();
            }
        });
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAction();
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

    @Override
    public void redirectToList(int tag){
        Intent intent = new Intent(activity, ToDoActivity.class);
        intent.putExtra("tag", tag);
        startActivity(intent);
    }

    private void checkAction(){
        int tag = 0;
        List<Integer> id = new ArrayList<Integer>();
        for(int i = 0; i < tasks.size(); i++){
            if(checkTag == 0){
                tag = 1;
                if(tasks.get(i).getChecked() == 1)
                    id.add(tasks.get(i).getTask_id());
            }else{
                if(tasks.get(i).getChecked() == 0)
                    id.add(tasks.get(i).getTask_id());
            }
        }
        if(id.size() > 0)
            mPresenter.checkTasks(id, tag);
        else
            Toast.makeText(activity, "None is selected.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAlertDialog(final int position){
        final int index = position;

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setIcon(android.R.drawable.ic_delete);
        builder.setTitle("Delete Item");
        builder.setMessage("Are you sure you want to delete this item ?");
        builder.setPositiveButton(Html.fromHtml("<font color='#20a860'>Yes</font>"), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                startLoading();
                mPresenter.deleteItem(index);
            }
        });
        builder.setNegativeButton(Html.fromHtml("<font color='#eb5334'>No</font>"), null);
        builder.create();
        builder.show();
    }

    @Override
    public void setTask(List<Task> task) {
        if(task.size() > 0){
            String adapterTag = "";
            this.tasks = task;
            if(checkTag == 0)
                adapterTag = "uncheck";
            else
                adapterTag = "check";
            taskListAdapter = new ListTaskAdapter(tasks, this, this, adapterTag);
            taskListRecyclerView.setAdapter(taskListAdapter);
        }else{
            tvNoTask.setVisibility(View.VISIBLE);
            emptyIcon.setVisibility(View.VISIBLE);
            actionButton.setVisibility(View.GONE);
        }
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
    public void setAlarmSuccess(String message, String time, int index) {
        Toast.makeText(activity, message,Toast.LENGTH_SHORT).show();
        AlarmManager alarmMgr = (AlarmManager) activity.getSystemService(activity.ALARM_SERVICE);
        Intent receiverIntent = new Intent(activity, AlarmReceiver.class);
        receiverIntent.putExtra("title", tasks.get(index).getTitle());
        receiverIntent.putExtra("description", tasks.get(index).getDescription());
        receiverIntent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(activity, 0, receiverIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        String[] alarmTime = time.split(":");
        String[] date = tasks.get(index).getDue_date().split("-");
        Calendar cal=Calendar.getInstance();
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.YEAR, 2021);
        cal.set(Calendar.DAY_OF_MONTH, 14);
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(alarmTime[0]));
        cal.set(Calendar.MINUTE, Integer.parseInt(alarmTime[1]));
        cal.set(Calendar.SECOND, 0);
        long timeInput = cal.getTimeInMillis();
        alarmMgr.set(AlarmManager.RTC_WAKEUP, timeInput, alarmIntent);

        Intent intent = new Intent(activity, ToDoActivity.class);
        intent.putExtra("tag", checkTag);
        startActivity(intent);
    }

    private void createNotificationChannel(){
        CharSequence name = "TodoListAlarm";
        String description = "Channel for alarm";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("notification", name, importance);
        channel.setDescription(description);

        NotificationManager manager = activity.getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);
    }

    @Override
    public void returnSuccess(String message) {
        Toast.makeText(activity, message,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(activity, ToDoActivity.class);
        intent.putExtra("tag", checkTag);
        startActivity(intent);
    }

    @Override
    public void checkSuccess() {
        Toast.makeText(activity, "Tasks updated successfully", Toast.LENGTH_SHORT).show();
        startActivity(activity.getIntent());
    }

    @Override
    public void setUser(User user) {
        title.setText("Hi, " + user.getName());
    }

    @Override
    public void showLogoutAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setIcon(android.R.drawable.ic_delete);
        builder.setTitle("Log Out");
        builder.setMessage("Are you sure you want to log out ?");
        builder.setPositiveButton(Html.fromHtml("<font color='#20a860'>Yes</font>"), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                mPresenter.logout();
            }
        });
        builder.setNegativeButton(Html.fromHtml("<font color='#eb5334'>No</font>"), null);
        builder.create();
        builder.show();
    }

    @Override
    public void logout() {
        activity.finish();
        startActivity(new Intent(activity, LoginActivity.class));
    }

    @Override
    public void setPresenter(ToDoContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void redirectToInput() {
        Intent intent = new Intent(activity, InputActivity.class);
        startActivity(intent);
    }

    @Override
    public void redirectToEdit(int id) {
        Intent intent = new Intent(activity, EditActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    @Override
    public void onEditClick(int position, View v, String tag) {
        mPresenter.editList(tasks.get(position).getTask_id());
    }

    @Override
    public void onDeleteClick(int position, View v, String tag) {
        showAlertDialog(tasks.get(position).getTask_id());
    }

    @Override
    public void onShareClick(int position, View v, String tag) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);

        String text;
        text = tasks.get(position).getTitle() + " - " + tasks.get(position).getDescription();
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, "Share Task Via :");
        startActivity(shareIntent);
    }

    @Override
    public void onAlarmClick(final int position, View v, String tag) {
        if(tasks.get(position).getDue_date() != null){
            Calendar mcurrentTime = Calendar.getInstance();
            final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(activity, R.style.MyTimePickerDialogTheme, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    String selectedTime = String.valueOf(selectedHour) + ":" + String.valueOf(selectedMinute) + ":" + "00";
                    mPresenter.setAlarm(tasks.get(position).getTask_id(), selectedTime, position);
                }
            }, hour, minute, true);
            mTimePicker.show();
        }else
            Toast.makeText(activity, "Please set due date before setting alarm.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemChecked(int idx, CompoundButton buttonView, boolean isChecked, String tag) {
        Log.d("tag", tag );
        if (isChecked){
            if(tag.equalsIgnoreCase("uncheck"))
                tasks.get(idx).setChecked(1);
            else
                tasks.get(idx).setChecked(0);
            //Toast.makeText(activity, "checklist", Toast.LENGTH_SHORT).show();
        } else {
            if(tag.equalsIgnoreCase("uncheck"))
                tasks.get(idx).setChecked(0);
            else
                tasks.get(idx).setChecked(1);
            //Toast.makeText(activity, "unchecklist", Toast.LENGTH_SHORT).show();
        }
    }
}

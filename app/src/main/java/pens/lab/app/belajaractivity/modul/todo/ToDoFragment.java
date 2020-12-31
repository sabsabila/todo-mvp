package pens.lab.app.belajaractivity.modul.todo;

import android.app.AlertDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pens.lab.app.belajaractivity.R;
import pens.lab.app.belajaractivity.base.BaseFragment;
import pens.lab.app.belajaractivity.model.Task;
import pens.lab.app.belajaractivity.model.User;
import pens.lab.app.belajaractivity.modul.edit.EditActivity;
import pens.lab.app.belajaractivity.modul.input.InputActivity;
import pens.lab.app.belajaractivity.modul.login.LoginActivity;
import pens.lab.app.belajaractivity.adapter.ListTaskAdapter;
import pens.lab.app.belajaractivity.utils.UtilProvider;

public class ToDoFragment extends BaseFragment<ToDoActivity, ToDoContract.Presenter> implements ToDoContract.View, ListTaskAdapter.MyClickListener, ListTaskAdapter.MyOnCheckedListener{

    ImageButton addButton;
    ImageButton btBack;
    Button finishButton;
    Button unfinishButton;
    TextView title;
    RecyclerView uncheckedRecyclerView;
    RecyclerView checkedRecyclerView;
    List<Task> uncheckedTasks;
    List<Task> checkedTasks;
    ListTaskAdapter uncheckedAdapter;
    ListTaskAdapter checkedAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.LayoutManager mLayoutManager2;

    public ToDoFragment(ImageButton btBack) {
        this.btBack = btBack;
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
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Integer> id = new ArrayList<Integer>();
                for(int i = 0; i < uncheckedTasks.size(); i++){
                    if(uncheckedTasks.get(i).getChecked() == 1)
                        id.add(uncheckedTasks.get(i).getTask_id());
                }
                mPresenter.checkTasks(id);
            }
        });
        unfinishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Integer> id = new ArrayList<Integer>();
                for(int i = 0; i < checkedTasks.size(); i++){
                    if(checkedTasks.get(i).getChecked() == 0)
                        id.add(checkedTasks.get(i).getTask_id());
                }
                mPresenter.uncheckTasks(id);
            }
        });
    }

    private void initView() {
        mPresenter = new ToDoPresenter(this, new ToDoInteractor(UtilProvider.getSharedPreferencesUtil()));
        mPresenter.start();
        addButton = fragmentView.findViewById(R.id.btnAdd);
        finishButton = fragmentView.findViewById(R.id.finishBtn);
        unfinishButton = fragmentView.findViewById(R.id.unfinishButton);
        title = fragmentView.findViewById(R.id.titleText);
        uncheckedRecyclerView = fragmentView.findViewById(R.id.recyclerViewTodolist);
        uncheckedRecyclerView.setHasFixedSize(true);
        checkedRecyclerView = fragmentView.findViewById(R.id.TodolistFinished);
        checkedRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(activity);
        mLayoutManager2 = new LinearLayoutManager(activity);
        uncheckedRecyclerView.setLayoutManager(mLayoutManager);
        checkedRecyclerView.setLayoutManager(mLayoutManager2);
        startLoading();
        mPresenter.getTasks();
        mPresenter.getUser();
        setTitle("To Do List");
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
        this.uncheckedTasks = task;
        uncheckedAdapter = new ListTaskAdapter(uncheckedTasks, this, this, "uncheck");
        uncheckedRecyclerView.setAdapter(uncheckedAdapter);
    }

    @Override
    public void setCheckedTask(List<Task> task) {
        this.checkedTasks = task;
        checkedAdapter = new ListTaskAdapter(checkedTasks, this, this, "check");
        checkedRecyclerView.setAdapter(checkedAdapter);
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
        Toast.makeText(activity, message,Toast.LENGTH_SHORT).show();
        startActivity(new Intent(activity, ToDoActivity.class));
    }

    @Override
    public void checkSuccess() {
        //Toast.makeText(activity, "Tasks updated successfully", Toast.LENGTH_SHORT).show();
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
        if(tag.equalsIgnoreCase("uncheck"))
            mPresenter.editList(uncheckedTasks.get(position).getTask_id());
        else
            mPresenter.editList(checkedTasks.get(position).getTask_id());
    }

    @Override
    public void onDeleteClick(int position, View v, String tag) {
        if(tag.equalsIgnoreCase("uncheck"))
            showAlertDialog(uncheckedTasks.get(position).getTask_id());
        else
            showAlertDialog(checkedTasks.get(position).getTask_id());
    }

    @Override
    public void onShareClick(int position, View v, String tag) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);

        String text;
        if(tag.equalsIgnoreCase("uncheck"))
            text = uncheckedTasks.get(position).getTitle() + " - " + uncheckedTasks.get(position).getDescription();
        else
            text = checkedTasks.get(position).getTitle() + " - " + checkedTasks.get(position).getDescription();
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    @Override
    public void onItemChecked(int idx, CompoundButton buttonView, boolean isChecked, String tag) {
        Log.d("tag", tag );
        if (isChecked){
            if(tag.equalsIgnoreCase("uncheck"))
                uncheckedTasks.get(idx).setChecked(1);
            else
                checkedTasks.get(idx).setChecked(0);
            //Toast.makeText(activity, "checklist", Toast.LENGTH_SHORT).show();
        } else {
            if(tag.equalsIgnoreCase("uncheck"))
                uncheckedTasks.get(idx).setChecked(0);
            else
                checkedTasks.get(idx).setChecked(1);
            //Toast.makeText(activity, "unchecklist", Toast.LENGTH_SHORT).show();
        }
    }
}

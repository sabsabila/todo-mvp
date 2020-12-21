package pens.lab.app.belajaractivity.modul.todo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

import pens.lab.app.belajaractivity.R;
import pens.lab.app.belajaractivity.base.BaseFragment;
import pens.lab.app.belajaractivity.model.Task;
import pens.lab.app.belajaractivity.modul.edit.EditActivity;
import pens.lab.app.belajaractivity.modul.input.InputActivity;
import pens.lab.app.belajaractivity.utils.Database;
import pens.lab.app.belajaractivity.utils.RecyclerViewAdapterTodolist;
import pens.lab.app.belajaractivity.utils.UtilProvider;

public class ToDoFragment extends BaseFragment<ToDoActivity, ToDoContract.Presenter> implements ToDoContract.View, RecyclerViewAdapterTodolist.MyClickListener , RecyclerViewAdapterTodolist.MyLongClickListener{

    ImageButton addButton;
    TextView title;
    RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private String username;

    public ToDoFragment() {
        this.username = UtilProvider.getSharedPreferencesUtil().getUsername();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        fragmentView = inflater.inflate(R.layout.fragment_todolist, container, false);
        mPresenter = new ToDoPresenter(this);
        mPresenter.start();
        addButton = fragmentView.findViewById(R.id.btnAdd);
        title = fragmentView.findViewById(R.id.titleText);
        title.setText("Hi, " + username);

        mRecyclerView = fragmentView.findViewById(R.id.recyclerViewTodolist);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(activity);
        mRecyclerView.setLayoutManager(mLayoutManager);

        setTitle("Todo List");

        startLoading();
        mPresenter.getTasks();
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.inputItem();
            }
        });
        setTitle("To Do List");

        return fragmentView;
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
        mRecyclerView.setAdapter(new RecyclerViewAdapterTodolist(task, this, this));
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
        Toast.makeText(activity, message,Toast.LENGTH_SHORT);
    }

    @Override
    public void returnSuccess(String message) {
        Toast.makeText(activity, message,Toast.LENGTH_SHORT);
        startActivity(new Intent(activity, ToDoActivity.class));
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
    public void onItemClick(int position, View v) {
        mPresenter.editList(position);
    }

    @Override
    public void onItemLongClick(int position, View v) {
        showAlertDialog(position);
    }
}

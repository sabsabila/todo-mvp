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

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

import pens.lab.app.belajaractivity.R;
import pens.lab.app.belajaractivity.base.BaseFragment;
import pens.lab.app.belajaractivity.model.Task;
import pens.lab.app.belajaractivity.modul.input.InputActivity;
import pens.lab.app.belajaractivity.utils.Database;
import pens.lab.app.belajaractivity.utils.RecyclerViewAdapterTodolist;

public class ToDoFragment extends BaseFragment<ToDoActivity, ToDoContract.Presenter> implements ToDoContract.View {

    ImageButton addButton;
    ImageButton clearButton;
    TextView title;
    ArrayList<Task> data;
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Database db;
    private String username;

    public ToDoFragment() {
        this.db = Database.getInstance();
        this.username = db.getLoggedInUser();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        fragmentView = inflater.inflate(R.layout.fragment_todolist, container, false);
        mPresenter = new ToDoPresenter(this);
        mPresenter.start();

        data = db.getTasks();
        addButton = fragmentView.findViewById(R.id.btnAdd);
        clearButton = fragmentView.findViewById(R.id.btnClear);
        title = fragmentView.findViewById(R.id.titleText);
        title.setText("Hi, " + username);

        mRecyclerView = fragmentView.findViewById(R.id.recyclerViewTodolist);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(activity);
        mRecyclerView.setLayoutManager(mLayoutManager);

        setTitle("Todo List");

        mAdapter = new RecyclerViewAdapterTodolist(data);
        mRecyclerView.setAdapter(mAdapter);

        ((RecyclerViewAdapterTodolist) mAdapter).setOnItemClickListener(new RecyclerViewAdapterTodolist.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                mPresenter.editList(data.get(position), position);
            }
        });

        ((RecyclerViewAdapterTodolist) mAdapter).setOnItemLongClickListener(new RecyclerViewAdapterTodolist.MyLongClickListener() {
            @Override
            public void onItemLongClick(int position, View v) {
                mPresenter.deleteItem(position);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.inputItem();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.clearList();
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
                db.deleteTask(index);
                mAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton(Html.fromHtml("<font color='#eb5334'>No</font>"), null);
        builder.create();
        builder.show();
    }

    @Override
    public void showInputBox(final Task oldItem, final int index) {
        final Dialog dialog = new Dialog(activity);
        dialog.setTitle("Input Box");
        dialog.setContentView(R.layout.edit_dialog);

        TextView txtMessage = (TextView) dialog.findViewById(R.id.txtmessage);
        txtMessage.setText("Update item");
        txtMessage.setTextColor(Color.parseColor("#000000"));

        final EditText title = (EditText) dialog.findViewById(R.id.txtTitle);
        final EditText desc = (EditText) dialog.findViewById(R.id.txtDescription);
        title.setText(oldItem.getTitle());
        desc.setText(oldItem.getDescription());
        Button bt = (Button) dialog.findViewById(R.id.btdone);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.editById(oldItem.getId(), title.getText().toString(), desc.getText().toString());
                mAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        dialog.show();
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
    public void emptyList(){
        db.deleteAll();
        mAdapter.notifyDataSetChanged();
    }

}

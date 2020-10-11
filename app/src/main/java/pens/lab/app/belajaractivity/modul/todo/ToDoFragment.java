package pens.lab.app.belajaractivity.modul.todo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;

import pens.lab.app.belajaractivity.R;
import pens.lab.app.belajaractivity.base.BaseFragment;
import pens.lab.app.belajaractivity.modul.input.InputActivity;
import android.widget.ArrayAdapter;

public class ToDoFragment extends BaseFragment<ToDoActivity, ToDoContract.Presenter> implements ToDoContract.View {

    ListView listView;
    Button addButton;
    Button clearButton;
    String[] ListElements = new String[] {"Belajar Android", "Mengerjakan Tugas", "Membuat Rangkuman", "Mentoring WPPL"};
    ArrayAdapter adapter;
    ArrayList<String> ListElementsArrayList;
    ArrayList<String> returnedList;

    public ToDoFragment(ArrayList<String> returnedData) {
        this.returnedList = returnedData;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        fragmentView = inflater.inflate(R.layout.fragment_todolist, container, false);
        mPresenter = new ToDoPresenter(this);
        mPresenter.start();

        listView = fragmentView.findViewById(R.id.listView1);
        addButton = fragmentView.findViewById(R.id.btnAdd);
        clearButton = fragmentView.findViewById(R.id.btnClear);

        //if(returnedList == null)
            ListElementsArrayList = new ArrayList<>(Arrays.asList(ListElements));
        //else{
        //    ListElementsArrayList = returnedList;
        //    adapter.notifyDataSetChanged();
        //}

        adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, ListElementsArrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPresenter.editList(ListElementsArrayList.get(position), position);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mPresenter.deleteItem(position);
                return true;
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

        new AlertDialog.Builder(activity)
                .setIcon(android.R.drawable.ic_delete)
                .setTitle("Delete Item")
                .setMessage("Are you sure you want to delete this item ?")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ListElementsArrayList.remove(index);
                        adapter.notifyDataSetChanged();
                    }
                })
                .show();
    }

    @Override
    public void showInputBox(String oldItem, final int index) {
        final Dialog dialog = new Dialog(activity);
        dialog.setTitle("Input Box");
        dialog.setContentView(R.layout.edit_dialog);

        TextView txtMessage = (TextView) dialog.findViewById(R.id.txtmessage);
        txtMessage.setText("Update item");
        txtMessage.setTextColor(Color.parseColor("#000000"));

        final EditText editText = (EditText) dialog.findViewById(R.id.txtinput);
        editText.setText(oldItem);
        Button bt = (Button) dialog.findViewById(R.id.btdone);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListElementsArrayList.set(index, editText.getText().toString());
                adapter.notifyDataSetChanged();
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
        intent.putStringArrayListExtra("todo", ListElementsArrayList);
        startActivity(intent);
    }

    @Override
    public void emptyList(){
        ListElementsArrayList.clear();
        adapter.notifyDataSetChanged();
    }

}

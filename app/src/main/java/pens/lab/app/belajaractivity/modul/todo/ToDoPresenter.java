package pens.lab.app.belajaractivity.modul.todo;

import java.util.ArrayList;

import pens.lab.app.belajaractivity.model.Task;

public class ToDoPresenter implements ToDoContract.Presenter{
    private final ToDoContract.View view;

    public ToDoPresenter(ToDoContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {}

    @Override
    public void inputItem(){
        view.redirectToInput();
    }

    public void clearList(){
        view.emptyList();
    }

    public void editList(Task oldItem, final int index){
        view.showInputBox(oldItem, index);
    }

    public void deleteItem(final int position){
        view.showAlertDialog(position);
    }

}

package pens.lab.app.belajaractivity.modul.todo;

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

    public void editList(String oldItem, final int index){
        view.showInputBox(oldItem, index);
    }

    public void deleteItem(final int position){
        view.showAlertDialog(position);
    }

}

package pens.lab.app.belajaractivity.modul.input;

public class InputPresenter implements InputContract.Presenter{

    private final InputContract.View view;

    public InputPresenter(InputContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {}

    @Override
    public void performAdd(){
        view.addElement();
    }

}

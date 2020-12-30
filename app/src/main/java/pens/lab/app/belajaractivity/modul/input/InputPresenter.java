package pens.lab.app.belajaractivity.modul.input;
import pens.lab.app.belajaractivity.model.Task;
import pens.lab.app.belajaractivity.utils.RequestCallback;

public class InputPresenter implements InputContract.Presenter{

    private final InputContract.View view;
    private final InputContract.Interactor interactor;

    public InputPresenter(InputContract.View view, InputContract.Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void start() {}

    @Override
    public void performAdd(Task task){
        interactor.requestAddTask(task, new RequestCallback<String>() {
            @Override
            public void requestSuccess(String data) {
                view.returnSuccess(data);
                view.endLoading();
            }

            @Override
            public void requestFailed(String errorMessage) {
                view.showError(errorMessage);
                view.endLoading();
            }
        });
    }
}

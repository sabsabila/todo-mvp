package pens.lab.app.belajaractivity.modul.edit;

import pens.lab.app.belajaractivity.model.Task;
import pens.lab.app.belajaractivity.callback.RequestCallback;

public class EditPresenter implements EditContract.Presenter{

    private final EditContract.View view;
    private final EditContract.Interactor interactor;

    public EditPresenter(EditContract.View view, EditContract.Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void start() {}

    @Override
    public void performEdit(int id, Task task){
        view.startLoading();
        interactor.requestEdit(id, task, new RequestCallback<String>() {
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

    @Override
    public void getTask(int id) {
        view.startLoading();
        interactor.requestTask(id, new RequestCallback<Task>() {
            @Override
            public void requestSuccess(Task data) {
                view.setTask(data);
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

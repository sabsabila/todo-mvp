package pens.lab.app.belajaractivity.utils;

public interface RequestCallback<T> {
    void requestSuccess(T data);
    void requestFailed(String errorMessage);
}
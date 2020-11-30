package pens.lab.app.belajaractivity.utils.session;

public interface SessionRepository<T> {
    public final String SHARED_PREFERENCE_NAME = "SessionSharedPreferences";

    abstract T initialize(T sessionData);
    abstract T getSessionData();
    abstract void setSessionData(T sessionData);
    abstract void destroy();
    abstract void update(T sessionData);
}

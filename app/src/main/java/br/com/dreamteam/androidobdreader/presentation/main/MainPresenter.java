package br.com.dreamteam.androidobdreader.presentation.main;


import android.util.Log;

import javax.inject.Inject;

import br.com.dreamteam.androidobdreader.model.usecase.UseCaseCallback;
import br.com.dreamteam.androidobdreader.model.usecase.version.GetAppVersion;
import br.com.dreamteam.androidobdreader.presentation.BasePresenter;

/**
 * Example of presenter iImplementation fully unit tested.
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */
public class MainPresenter extends BasePresenter implements MainActivityContract.Presenter {

    private static final String TAG = "MainPresenter";
    @Inject
    GetAppVersion getAppVersion;

    private MainActivityContract.View view;

    @Inject
    public MainPresenter() {
        //Empty Constructor for DI
    }

    @Override
    public void onViewResume(MainActivityContract.View view) {
        Log.d(TAG, "onViewResume: ");
        attachView(view);

        loadProperties();
    }

    private void loadProperties() {
        loadAppVersion();
    }

    void loadAppVersion() {
        Log.d(TAG, "loadAppVersion: ");
        getAppVersion.execute(new UseCaseCallback<String>() {
            @Override
            public void onSuccess(String data) {
                Log.d(TAG, "loadAppVersion:  onSuccess: " + data);
                if (hasViewAttached()) {
                    view.showAppVersion(data);
                }
            }

            @Override
            public void onError(Exception e) {
                Log.d(TAG, "loadAppVersion: onError: ");
                defaultErrorHandling(TAG, e);
            }
        });
    }


    private boolean hasViewAttached() {
        return view != null;
    }

    private void attachView(MainActivityContract.View view) {
        Log.d(TAG, "attachView: " + view.toString());
        this.view = view;
    }

    @Override
    public void onViewPause(MainActivityContract.View view) {
        Log.d(TAG, "onViewPaused: ");
        detachView(view);
    }


    private void detachView(MainActivityContract.View view) {
        Log.d(TAG, "detachView: " + view.toString());
        this.view = null;
    }

    MainActivityContract.View getView() {
        return view;
    }
}

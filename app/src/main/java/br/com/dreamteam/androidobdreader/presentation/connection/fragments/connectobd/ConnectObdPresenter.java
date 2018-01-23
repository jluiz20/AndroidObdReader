package br.com.dreamteam.androidobdreader.presentation.connection.fragments.connectobd;


import android.util.Log;

import javax.inject.Inject;

import br.com.dreamteam.androidobdreader.presentation.BasePresenter;

/**
 * Example of presenter iImplementation fully unit tested.
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */
public class ConnectObdPresenter extends BasePresenter implements ConnectObdContract.Presenter {

    private static final String TAG = "ConnectObdPresenter";

    private ConnectObdContract.View view;

    @Inject
    public ConnectObdPresenter() {
        //Empty Constructor for DI
    }

    @Override
    public void onViewResume(ConnectObdContract.View view) {
        Log.d(TAG, "onViewResume: ");
        attachView(view);
    }


    private void attachView(ConnectObdContract.View view) {
        Log.d(TAG, "attachView: " + view.toString());
        this.view = view;
    }

    @Override
    public void onViewPause(ConnectObdContract.View view) {
        Log.d(TAG, "onViewPaused: ");
        detachView(view);
    }


    private void detachView(ConnectObdContract.View view) {
        Log.d(TAG, "detachView: " + view.toString());
        this.view = null;
    }

    ConnectObdContract.View getView() {
        return view;
    }
}

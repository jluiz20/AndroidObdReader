package br.com.dreamteam.androidobdreader.presentation.connection;


import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import br.com.dreamteam.androidobdreader.model.usecase.obd.GetConnectionState;
import br.com.dreamteam.androidobdreader.model.usecase.obd.GetDiscoveredDevices;
import br.com.dreamteam.androidobdreader.model.usecase.obd.SetStartDiscovery;
import br.com.dreamteam.androidobdreader.presentation.BasePresenter;

/**
 * Example of presenter iImplementation fully unit tested.
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */
public class ConnectionStepsPresenter extends BasePresenter implements ConnectionStepsContract.Presenter {

    private static final String TAG = "ConnectionStepsPresente";

    @Inject
    SetStartDiscovery setStartDiscovery;

    @Inject
    EventBus eventBus;

    @Inject
    GetDiscoveredDevices getDiscoveredDevices;

    @Inject
    GetConnectionState getConnectionState;

    private ConnectionStepsContract.View view;

    @Inject
    public ConnectionStepsPresenter() {
        //Empty Constructor for DI
    }

    @Override
    public void onViewResume(ConnectionStepsContract.View view) {
        Log.d(TAG, "onViewResume: ");
        attachView(view);
    }

    private void attachView(ConnectionStepsContract.View view) {
        Log.d(TAG, "attachView: " + view.toString());
        this.view = view;
    }

    @Override
    public void onViewPause(ConnectionStepsContract.View view) {
        Log.d(TAG, "onViewPaused: ");
        detachView(view);
    }


    private void detachView(ConnectionStepsContract.View view) {
        Log.d(TAG, "detachView: " + view.toString());
        this.view = null;
    }

    ConnectionStepsContract.View getView() {
        return view;
    }
}

package br.com.dreamteam.androidobdreader.presentation.connection.fragments.connecting;


import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import br.com.dreamteam.androidobdreader.model.datasource.obd.states.ObdConnectionFailedChanged;
import br.com.dreamteam.androidobdreader.model.datasource.obd.states.ObdConnectionSuccessfulChanged;
import br.com.dreamteam.androidobdreader.presentation.BasePresenter;

/**
 * Example of presenter iImplementation fully unit tested.
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */
public class ConnectingPresenter extends BasePresenter implements ConnectingContract.Presenter {

    private static final String TAG = "ConnectingPresenter";
    @Inject
    EventBus eventBus;
    private ConnectingContract.View view;

    @Inject
    public ConnectingPresenter() {
        //Empty Constructor for DI
    }

    @Override
    public void onViewResume(ConnectingContract.View view) {
        Log.d(TAG, "onViewResume: ");
        eventBus.register(this);
        attachView(view);
    }


    private void attachView(ConnectingContract.View view) {
        Log.d(TAG, "attachView: " + view.toString());
        this.view = view;
    }

    @Override
    public void onViewPause(ConnectingContract.View view) {
        Log.d(TAG, "onViewPaused: ");
        eventBus.unregister(this);
        detachView(view);
    }


    private void detachView(ConnectingContract.View view) {
        Log.d(TAG, "detachView: " + view.toString());
        this.view = null;
    }

    ConnectingContract.View getView() {
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onObdConnectionSuccessfulChanged(ObdConnectionSuccessfulChanged event) {
        Log.d(TAG, "ObdConnectionSuccessfulChanged: " + event.getDispatchTime());
        view.showConnectionSuccess();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onObdConnectionFailedChanged(ObdConnectionFailedChanged event) {
        Log.d(TAG, "ObdConnectionFailedChanged: " + event.getDispatchTime());
        view.showConnectionFailed();
    }
}

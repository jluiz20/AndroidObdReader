package br.com.dreamteam.androidobdreader.presentation.connection.fragments.discovery;


import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Set;

import javax.inject.Inject;

import br.com.dreamteam.androidobdreader.model.BluetoothDeviceWrapper;
import br.com.dreamteam.androidobdreader.model.datasource.obd.states.ConnectionState;
import br.com.dreamteam.androidobdreader.model.datasource.obd.states.ObdDiscoveryStateChanged;
import br.com.dreamteam.androidobdreader.model.usecase.UseCaseCallback;
import br.com.dreamteam.androidobdreader.model.usecase.obd.GetConnectionState;
import br.com.dreamteam.androidobdreader.model.usecase.obd.GetDiscoveredDevices;
import br.com.dreamteam.androidobdreader.model.usecase.obd.GetPairedDevices;
import br.com.dreamteam.androidobdreader.model.usecase.obd.SetConnectToDevice;
import br.com.dreamteam.androidobdreader.model.usecase.obd.SetStartDiscovery;
import br.com.dreamteam.androidobdreader.presentation.BasePresenter;

/**
 * Example of presenter iImplementation fully unit tested.
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */
public class DiscoveryPresenter extends BasePresenter implements DiscoveryContract.Presenter {
    private static final String TAG = "DiscoveryPresenter";

    @Inject
    SetStartDiscovery setStartDiscovery;

    @Inject
    EventBus eventBus;

    @Inject
    GetDiscoveredDevices getDiscoveredDevices;

    @Inject
    GetPairedDevices getPairedDevices;

    @Inject
    GetConnectionState getConnectionState;

    @Inject
    SetConnectToDevice setConnectToDevice;

    private DiscoveryContract.View view;

    @Inject
    public DiscoveryPresenter() {
        //Empty Constructor for DI
    }

    @Override
    public void onViewResume(DiscoveryContract.View view) {
        Log.d(TAG, "onViewResume: ");
        attachView(view);
        loadProperties();
        eventBus.register(this);
    }

    private void loadProperties() {
        loadPairedDevices();
    }

    private boolean hasViewAttached() {
        return view != null;
    }

    private void attachView(DiscoveryContract.View view) {
        Log.d(TAG, "attachView: " + view.toString());
        this.view = view;
    }

    @Override
    public void onViewPause(DiscoveryContract.View view) {
        Log.d(TAG, "onViewPaused: ");
        eventBus.unregister(this);
        detachView(view);
    }

    void loadPairedDevices() {
        Log.d(TAG, "loadDiscoveredDevices: ");
        getPairedDevices.execute(new UseCaseCallback<Set<BluetoothDeviceWrapper>>() {
            @Override
            public void onSuccess(Set<BluetoothDeviceWrapper> data) {
                Log.d(TAG, "loadDiscoveredDevices:  onSuccess: " + data);
                if (hasViewAttached()) {
                    view.showPairedDevices(data);
                }
            }

            @Override
            public void onError(Exception e) {
                Log.d(TAG, "loadDiscoveredDevices: onError: ");
                defaultErrorHandling(TAG, e);
            }
        });
    }

    void loadDiscoveredDevices() {
        Log.d(TAG, "loadDiscoveredDevices: ");
        getDiscoveredDevices.execute(new UseCaseCallback<Set<BluetoothDeviceWrapper>>() {
            @Override
            public void onSuccess(Set<BluetoothDeviceWrapper> data) {
                Log.d(TAG, "loadDiscoveredDevices:  onSuccess: " + data);
                if (hasViewAttached()) {
                    view.showDiscoveredDevices(data);
                }
            }

            @Override
            public void onError(Exception e) {
                Log.d(TAG, "loadDiscoveredDevices: onError: ");
                defaultErrorHandling(TAG, e);
            }
        });
    }

    void loadConnectionState() {
        Log.d(TAG, "loadConnectionState: ");
        getConnectionState.execute(new UseCaseCallback<ConnectionState>() {
            @Override
            public void onSuccess(ConnectionState data) {
                Log.d(TAG, "loadConnectionState:  onSuccess: " + data);
                if (hasViewAttached()) {
                    view.updateConnectionState(data);
                }
            }

            @Override
            public void onError(Exception e) {
                Log.d(TAG, "loadConnectionState: onError: ");
                defaultErrorHandling(TAG, e);
            }
        });
    }

    @Override
    public void onUserStartConnection() {
        setStartDiscovery.execute(new UseCaseCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                Log.d(TAG, "onSuccess: onUserStartConnection");
            }

            @Override
            public void onError(Exception e) {
                defaultErrorHandling(TAG, e);
            }
        });
    }

    @Override
    public void onUserSelectedDeviceToConnect(BluetoothDeviceWrapper bluetoothDevice) {
        setConnectToDevice.execute(bluetoothDevice, new UseCaseCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                Log.d(TAG, "onUserSelectedDeviceToConnect:  onSuccess: " + data);
                if (hasViewAttached()) {
                    view.showConnectionInProgress();
                }
            }

            @Override
            public void onError(Exception e) {
                Log.d(TAG, "onUserSelectedDeviceToConnect: onError: ");
                defaultErrorHandling(TAG, e);
            }
        });
    }


    private void detachView(DiscoveryContract.View view) {
        Log.d(TAG, "detachView: " + view.toString());
        this.view = null;
    }

    DiscoveryContract.View getView() {
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onObdDiscoveryStateChanged(ObdDiscoveryStateChanged event) {
        Log.d(TAG, "onMessageEvent: " + event.getDispatchTime());
        loadDiscoveredDevices();
        loadConnectionState();
    }
}

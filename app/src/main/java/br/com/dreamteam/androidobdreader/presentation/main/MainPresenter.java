package br.com.dreamteam.androidobdreader.presentation.main;


import android.util.Log;

import com.github.pires.obd.commands.ObdCommand;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;

import javax.inject.Inject;

import br.com.dreamteam.androidobdreader.model.datasource.obd.states.ObdSendCommand;
import br.com.dreamteam.androidobdreader.model.usecase.UseCaseCallback;
import br.com.dreamteam.androidobdreader.model.usecase.obd.GetConnectedDeviceAddress;
import br.com.dreamteam.androidobdreader.model.usecase.obd.GetConnectedDeviceName;
import br.com.dreamteam.androidobdreader.model.usecase.obd.GetIsBluetoothSupported;
import br.com.dreamteam.androidobdreader.model.usecase.version.GetAppVersion;
import br.com.dreamteam.androidobdreader.presentation.BasePresenter;

/**
 * Example of presenter iImplementation fully unit tested.
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */
public class MainPresenter extends BasePresenter implements MainContract.Presenter {

    private static final String TAG = "MainPresenter";
    @Inject
    GetAppVersion getAppVersion;

    @Inject
    GetIsBluetoothSupported getIsBluetoothSupported;

    @Inject
    GetConnectedDeviceName getConnectedDeviceName;

    @Inject
    GetConnectedDeviceAddress getConnectedDeviceAddress;

    @Inject
    EventBus eventBus;

    private MainContract.View view;

    @Inject
    public MainPresenter() {
        //Empty Constructor for DI
    }

    @Override
    public void onViewResume(MainContract.View view) {
        Log.d(TAG, "onViewResume: ");
        attachView(view);
        loadIsBluetoothSupported();
        loadProperties();
    }

    private void loadProperties() {
        loadAppVersion();
        loadConnectedDeviceName();
        loadConnectedDeviceAddress();
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

    void loadIsBluetoothSupported() {
        Log.d(TAG, "loadIsBluetoothSupported: ");
        getIsBluetoothSupported.execute(new UseCaseCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
                Log.d(TAG, "loadIsBluetoothSupported:  onSuccess: " + data);
                if (hasViewAttached() && !data) {
                    view.showBluetoothIsNotSupported();
                }
            }

            @Override
            public void onError(Exception e) {
                Log.d(TAG, "loadAppVersion: onError: ");
                defaultErrorHandling(TAG, e);
            }
        });
    }

    void loadConnectedDeviceName() {
        Log.d(TAG, "loadConnectedDeviceName: ");
        getConnectedDeviceName.execute(new UseCaseCallback<String>() {
            @Override
            public void onSuccess(String data) {
                Log.d(TAG, "loadConnectedDeviceName:  onSuccess: " + data);
                if (hasViewAttached()) {
                    view.showConnectedDeviceName(data);
                }
            }

            @Override
            public void onError(Exception e) {
                Log.d(TAG, "loadConnectedDeviceName: onError: ");
                defaultErrorHandling(TAG, e);
            }
        });
    }

    void loadConnectedDeviceAddress() {
        Log.d(TAG, "loadConnectedDeviceAddress: ");
        getConnectedDeviceAddress.execute(new UseCaseCallback<String>() {
            @Override
            public void onSuccess(String data) {
                Log.d(TAG, "loadConnectedDeviceAddress:  onSuccess: " + data);
                if (hasViewAttached()) {
                    view.showConnectedDeviceAddress(data);
                }
            }

            @Override
            public void onError(Exception e) {
                Log.d(TAG, "loadConnectedDeviceAddress: onError: ");
                defaultErrorHandling(TAG, e);
            }
        });
    }

    private boolean hasViewAttached() {
        return view != null;
    }

    private void attachView(MainContract.View view) {
        Log.d(TAG, "attachView: " + view.toString());
        this.view = view;
    }

    @Override
    public void onViewPause(MainContract.View view) {
        Log.d(TAG, "onViewPaused: ");
        detachView(view);
    }

    @Override
    public void onUserWantToSendCommand(ObdCommand command) {
        this.eventBus.post(new ObdSendCommand(Calendar.getInstance(), command));
    }

    private void detachView(MainContract.View view) {
        Log.d(TAG, "detachView: " + view.toString());
        this.view = null;
    }

    MainContract.View getView() {
        return view;
    }
}

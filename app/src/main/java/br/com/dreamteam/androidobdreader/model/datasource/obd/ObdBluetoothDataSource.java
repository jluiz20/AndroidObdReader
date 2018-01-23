package br.com.dreamteam.androidobdreader.model.datasource.obd;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.dreamteam.androidobdreader.model.BluetoothDeviceWrapper;
import br.com.dreamteam.androidobdreader.model.datasource.obd.states.ConnectionState;
import br.com.dreamteam.androidobdreader.model.datasource.obd.states.ObdConnectionSuccessfulChanged;
import br.com.dreamteam.androidobdreader.model.datasource.obd.states.ObdDiscoveryStateChanged;

/**
 * Responsible for bluetooth connection
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */

public class ObdBluetoothDataSource implements ObdDataSource {

    private static final String TAG = "ObdBluetoothDataSource";
    private final EventBus eventBus;
    BluetoothAdapter mBluetoothAdapter;
    @Inject
    @Named("applicationContext")
    Context context;
    private ConnectionState connectionState = ConnectionState.UNKNOWN;
    private final BroadcastReceiver mDiscoveryFinished = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(TAG, "onReceive: Finished" + action);
            connectionState = ConnectionState.DISCOVERY_FINISHED;
            notifyStatusChanged();
        }
    };
    private Set<BluetoothDeviceWrapper> discoveredDevices = new HashSet<>();
    private final BroadcastReceiver mDiscoveryStarted = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(TAG, "onReceive Started: " + action);
            connectionState = ConnectionState.DISCOVERY_STARTED;
            discoveredDevices = new HashSet<>();
            notifyStatusChanged();
        }
    };
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                discoveredDevices.add(new BluetoothDeviceWrapper(device));
                Log.d(TAG, "onReceive: FOUND: " + deviceName);
                notifyStatusChanged();
            }
        }
    };

    @Inject
    public ObdBluetoothDataSource(EventBus eventBus) {
        //Inject
        this.eventBus = eventBus;
        this.eventBus.register(this);
    }

    private void getBluetoothAdapter() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    private void enableBluetooth() {
        getBluetoothAdapter();
        if (!mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.enable();
        }
    }

    @Override
    public boolean isBluetoothSupported() {
        getBluetoothAdapter();
        return mBluetoothAdapter != null;
    }

    @Override
    public Set<BluetoothDeviceWrapper> getPairedDevices() {
        getBluetoothAdapter();
        Set<BluetoothDeviceWrapper> bluetoothDeviceWrappers = new HashSet<>();
        if (mBluetoothAdapter.getBondedDevices().isEmpty()) {
            for (BluetoothDevice device : mBluetoothAdapter.getBondedDevices()) {
                bluetoothDeviceWrappers.add(new BluetoothDeviceWrapper(device));
            }
        }
        return bluetoothDeviceWrappers;
    }

    @Override
    public Set<BluetoothDeviceWrapper> getDiscoveredDevices() {
        return discoveredDevices;
    }

    @Override
    public ConnectionState getConnectionState() {
        return this.connectionState;
    }

    @Override
    public void startDiscovery() {
        enableBluetooth();
        mBluetoothAdapter.startDiscovery();
        context.registerReceiver(mReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        context.registerReceiver(mDiscoveryStarted, new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED));
        context.registerReceiver(mDiscoveryFinished, new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED));
    }

    @Override
    public void connectToDevice(BluetoothDeviceWrapper bluetoothDeviceWrapper) {
        getBluetoothAdapter();
        mBluetoothAdapter.cancelDiscovery();
        ConnectThread connectThread = new ConnectThread(bluetoothDeviceWrapper.getBluetoothDevice(), eventBus);
        connectThread.start();
    }

    private void notifyStatusChanged() {
        eventBus.postSticky(new ObdDiscoveryStateChanged(Calendar.getInstance()));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onObdConnectionSuccessfulChanged(ObdConnectionSuccessfulChanged event) {
        Log.d(TAG, "onMessageEvent: " + event.getDispatchTime());
    }
}

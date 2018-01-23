package br.com.dreamteam.androidobdreader.model.repository.obd;


import java.util.Set;

import javax.inject.Inject;

import br.com.dreamteam.androidobdreader.model.BluetoothDeviceWrapper;
import br.com.dreamteam.androidobdreader.model.datasource.obd.ObdBluetoothDataSource;
import br.com.dreamteam.androidobdreader.model.datasource.obd.states.ConnectionState;

/**
 * Implementation of {@link ObdRepository}
 * * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */

public class ObdRepositoryImpl implements ObdRepository {

    @Inject
    ObdBluetoothDataSource bluetoothDataSource;


    @Inject
    public ObdRepositoryImpl() {
        //Injected
    }

    @Override
    public boolean getIsBluetoothSupported() {
        return bluetoothDataSource.isBluetoothSupported();
    }

    @Override
    public Set<BluetoothDeviceWrapper> getPairedDevices() {
        return bluetoothDataSource.getPairedDevices();
    }

    @Override
    public Set<BluetoothDeviceWrapper> getDiscoveredDevices() {
        return bluetoothDataSource.getDiscoveredDevices();
    }

    @Override
    public void startDiscovery() {
        bluetoothDataSource.startDiscovery();
    }

    @Override
    public ConnectionState getConnectionState() {
        return bluetoothDataSource.getConnectionState();
    }

    @Override
    public void connectToDevice(BluetoothDeviceWrapper bluetoothDevice) {
        bluetoothDataSource.connectToDevice(bluetoothDevice);
    }
}

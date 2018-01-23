package br.com.dreamteam.androidobdreader.model.datasource.obd;

import java.util.Set;

import br.com.dreamteam.androidobdreader.model.BluetoothDeviceWrapper;
import br.com.dreamteam.androidobdreader.model.datasource.obd.states.ConnectionState;

/**
 * This class refers to all information of the
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */

public interface ObdDataSource {

    /**
     * checks if bluetooth is supported by the device
     *
     * @return true if supported
     */
    boolean isBluetoothSupported();


    /**
     * Returns the list of devices already paired
     */
    Set<BluetoothDeviceWrapper> getPairedDevices();

    /**
     * Returns the list of devices discovered
     */
    Set<BluetoothDeviceWrapper> getDiscoveredDevices();

    /**
     * get the actual state of the connection
     *
     * @return the state
     */
    ConnectionState getConnectionState();

    /**
     * Start the discovery of new devices
     */
    void startDiscovery();

    /**
     * Start the connectoin process to the device
     *
     * @param bluetoothDevice the device to connect
     */
    void connectToDevice(BluetoothDeviceWrapper bluetoothDevice);

}
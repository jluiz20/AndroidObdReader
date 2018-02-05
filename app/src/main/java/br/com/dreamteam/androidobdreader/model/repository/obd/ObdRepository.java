package br.com.dreamteam.androidobdreader.model.repository.obd;

import java.util.Set;

import br.com.dreamteam.androidobdreader.model.BluetoothDeviceWrapper;
import br.com.dreamteam.androidobdreader.model.datasource.obd.states.ConnectionState;

/**
 * This class refers to all information of the
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */

public interface ObdRepository {

    /**
     * checks if bluetooth is supported by the device
     *
     * @return true if supported
     */
    boolean getIsBluetoothSupported();

    /**
     * Returns the list of devices already paired
     */
    Set<BluetoothDeviceWrapper> getPairedDevices();


    /**
     * Returns the list of devices discovered
     */
    Set<BluetoothDeviceWrapper> getDiscoveredDevices();


    /**
     * Start the discovery of new devices
     */
    void startDiscovery();

    /**
     * get the actual state of the connection
     *
     * @return the state
     */
    ConnectionState getConnectionState();

    /**
     * Start the connectoin process to the device
     *
     * @param bluetoothDevice the device to connect
     */
    void connectToDevice(BluetoothDeviceWrapper bluetoothDevice);

    /**
     * @return the name of the device that is connected
     */
    String getConnectedDeviceName();

    /**
     * @return the address of the device that is connected
     */
    String getConnectedDeviceAddress();

}

package br.com.dreamteam.androidobdreader.model;

import android.bluetooth.BluetoothDevice;

import javax.inject.Inject;

/**
 * A wrapper for the BluetoothDevice.class for testing purpose
 *
 * @author João Luiz Vieira <joao.vieira@pixida.com.br>.
 */

public class BluetoothDeviceWrapper {

    private BluetoothDevice bluetoothDevice;

    @Inject
    public BluetoothDeviceWrapper() {
        //empty constructor
    }

    public BluetoothDeviceWrapper(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
    }

    public BluetoothDevice getBluetoothDevice() {
        return bluetoothDevice;
    }

    public String getName() {
        return bluetoothDevice.getName();
    }

    public String getAddress() {
        return bluetoothDevice.getAddress();
    }
}

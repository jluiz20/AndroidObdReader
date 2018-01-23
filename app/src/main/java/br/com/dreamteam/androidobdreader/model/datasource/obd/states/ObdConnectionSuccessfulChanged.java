package br.com.dreamteam.androidobdreader.model.datasource.obd.states;


import android.bluetooth.BluetoothSocket;

import java.util.Calendar;

import br.com.dreamteam.androidobdreader.model.datasource.ModelEvent;

/**
 * Connection state has changed
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */

public class ObdConnectionSuccessfulChanged extends ModelEvent {
    private final BluetoothSocket bluetoothSocket;

    public ObdConnectionSuccessfulChanged(Calendar dispatchTime, BluetoothSocket socket) {
        super(dispatchTime);
        this.bluetoothSocket = socket;
    }

    public BluetoothSocket getBluetoothSocket() {
        return bluetoothSocket;
    }
}

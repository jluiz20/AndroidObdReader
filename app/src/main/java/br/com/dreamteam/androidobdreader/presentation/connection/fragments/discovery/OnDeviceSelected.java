package br.com.dreamteam.androidobdreader.presentation.connection.fragments.discovery;

import br.com.dreamteam.androidobdreader.model.BluetoothDeviceWrapper;

/**
 * Used to identfy if a device was selected
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */

public interface OnDeviceSelected {

    void onDeviceSelected(BluetoothDeviceWrapper item);

}

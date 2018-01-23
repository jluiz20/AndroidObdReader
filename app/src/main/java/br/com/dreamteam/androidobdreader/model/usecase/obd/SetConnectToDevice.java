package br.com.dreamteam.androidobdreader.model.usecase.obd;

import javax.inject.Inject;

import br.com.dreamteam.androidobdreader.model.BluetoothDeviceWrapper;
import br.com.dreamteam.androidobdreader.model.repository.obd.ObdRepository;
import br.com.dreamteam.androidobdreader.model.usecase.UseCaseCallback;

/**
 * Start the discovery for new OBD
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */

public class SetConnectToDevice {

    private ObdRepository repository;

    @Inject
    public SetConnectToDevice(ObdRepository repository) {
        this.repository = repository;
    }

    public void execute(BluetoothDeviceWrapper bluetoothDevice, UseCaseCallback<Void> callback) {
        try {
            repository.connectToDevice(bluetoothDevice);
            callback.onSuccess(null);
        } catch (Exception e) {
            callback.onError(e);
        }
    }
}

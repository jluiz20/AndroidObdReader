package br.com.dreamteam.androidobdreader.model.usecase.obd;

import java.util.Set;

import javax.inject.Inject;

import br.com.dreamteam.androidobdreader.model.BluetoothDeviceWrapper;
import br.com.dreamteam.androidobdreader.model.repository.obd.ObdRepository;
import br.com.dreamteam.androidobdreader.model.usecase.UseCaseCallback;

/**
 * This use case returns the list of paired devices
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */

public class GetPairedDevices {

    private ObdRepository repository;

    @Inject
    public GetPairedDevices(ObdRepository repository) {
        this.repository = repository;
    }

    public void execute(UseCaseCallback<Set<BluetoothDeviceWrapper>> callback) {
        try {
            callback.onSuccess(repository.getPairedDevices());
        } catch (Exception e) {
            callback.onError(e);
        }
    }
}

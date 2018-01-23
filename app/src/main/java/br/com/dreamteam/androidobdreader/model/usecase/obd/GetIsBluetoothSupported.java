package br.com.dreamteam.androidobdreader.model.usecase.obd;

import javax.inject.Inject;

import br.com.dreamteam.androidobdreader.model.repository.obd.ObdRepository;
import br.com.dreamteam.androidobdreader.model.usecase.UseCaseCallback;

/**
 * This use case returns true if bluetooth is supported by the device
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */

public class GetIsBluetoothSupported {

    private ObdRepository repository;

    @Inject
    public GetIsBluetoothSupported(ObdRepository repository) {
        this.repository = repository;
    }

    public void execute(UseCaseCallback<Boolean> callback) {
        try {
            callback.onSuccess(repository.getIsBluetoothSupported());
        } catch (Exception e) {
            callback.onError(e);
        }
    }
}

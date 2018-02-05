package br.com.dreamteam.androidobdreader.model.usecase.obd;

import javax.inject.Inject;

import br.com.dreamteam.androidobdreader.model.repository.obd.ObdRepository;
import br.com.dreamteam.androidobdreader.model.usecase.UseCaseCallback;

/**
 * This use case returns the name of the connected device
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */

public class GetConnectedDeviceName {

    private ObdRepository repository;

    @Inject
    public GetConnectedDeviceName(ObdRepository repository) {
        this.repository = repository;
    }

    public void execute(UseCaseCallback<String> callback) {
        try {
            callback.onSuccess(repository.getConnectedDeviceName());
        } catch (Exception e) {
            callback.onError(e);
        }
    }
}
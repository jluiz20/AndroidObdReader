package br.com.dreamteam.androidobdreader.model.usecase.obd;

import javax.inject.Inject;

import br.com.dreamteam.androidobdreader.model.repository.obd.ObdRepository;
import br.com.dreamteam.androidobdreader.model.usecase.UseCaseCallback;

/**
 * Start the discovery for new OBD
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */

public class SetStartDiscovery {

    private ObdRepository repository;

    @Inject
    public SetStartDiscovery(ObdRepository repository) {
        this.repository = repository;
    }

    public void execute(UseCaseCallback<Void> callback) {
        try {
            repository.startDiscovery();
            callback.onSuccess(null);
        } catch (Exception e) {
            callback.onError(e);
        }
    }
}

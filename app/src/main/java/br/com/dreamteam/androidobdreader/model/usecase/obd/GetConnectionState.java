package br.com.dreamteam.androidobdreader.model.usecase.obd;

import javax.inject.Inject;

import br.com.dreamteam.androidobdreader.model.datasource.obd.states.ConnectionState;
import br.com.dreamteam.androidobdreader.model.repository.obd.ObdRepository;
import br.com.dreamteam.androidobdreader.model.usecase.UseCaseCallback;

/**
 * This use case returns the connection state
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */

public class GetConnectionState {

    private ObdRepository repository;

    @Inject
    public GetConnectionState(ObdRepository repository) {
        this.repository = repository;
    }

    public void execute(UseCaseCallback<ConnectionState> callback) {
        try {
            callback.onSuccess(repository.getConnectionState());
        } catch (Exception e) {
            callback.onError(e);
        }
    }
}

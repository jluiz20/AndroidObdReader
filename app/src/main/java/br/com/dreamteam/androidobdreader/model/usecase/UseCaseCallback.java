package br.com.dreamteam.androidobdreader.model.usecase;

/**
 * This class is the interface between a presenter and the model,
 * use cases should return success or error through it.
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */

public interface UseCaseCallback<T> {

    void onSuccess(T data);

    void onError(Exception e);

}

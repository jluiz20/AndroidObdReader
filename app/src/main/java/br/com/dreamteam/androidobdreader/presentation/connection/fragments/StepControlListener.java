package br.com.dreamteam.androidobdreader.presentation.connection.fragments;

/**
 * Listens for OnProceedClick
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */

public interface StepControlListener {

    void onProceed();

    void onReturn();

    void onCompleted();
}
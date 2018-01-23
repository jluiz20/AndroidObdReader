package br.com.dreamteam.androidobdreader.presentation.connection.fragments.turnvehicleon;


/**
 * This class is a contract for the connection screen
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */

public interface TurnVehicleOnContract {

    /**
     * View part of the contract.
     */
    interface View {

    }

    /**
     * Presenter part of the contract.
     */
    interface Presenter {


        /**
         * Called when the view enters on resume state, generally this are loaded here.
         */
        void onViewResume(View view);

        /**
         * Called when then view enters on pause state, usually this is the time to release strong
         * references to an Activity or fragment for example.
         */
        void onViewPause(View view);
    }
}

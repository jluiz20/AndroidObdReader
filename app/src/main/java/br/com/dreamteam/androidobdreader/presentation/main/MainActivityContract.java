package br.com.dreamteam.androidobdreader.presentation.main;


/**
 * This class is an example of a contract for a screen and its presenter.
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */

public interface MainActivityContract {

    /**
     * View part of the contract.
     */
    interface View {

        /**
         * Show the app version.
         */
        void showAppVersion(String appVersion);

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

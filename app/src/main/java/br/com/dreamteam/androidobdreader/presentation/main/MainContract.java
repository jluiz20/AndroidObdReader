package br.com.dreamteam.androidobdreader.presentation.main;


import com.github.pires.obd.commands.ObdCommand;

/**
 * This class is an example of a contract for a screen and its presenter.
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */

public interface MainContract {

    /**
     * View part of the contract.
     */
    interface View {

        /**
         * Show the app version.
         */
        void showAppVersion(String appVersion);

        /**
         * if bluetooth is not supported, update the view
         */
        void showBluetoothIsNotSupported();

        /**
         * Shows the name of the connected device
         *
         * @param name
         */
        void showConnectedDeviceName(String name);

        /**
         * Shows the address of the connected device
         *
         * @param address
         */
        void showConnectedDeviceAddress(String address);
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

        /**
         * Sends command by obd
         *
         * @param command the command
         */
        void onUserWantToSendCommand(ObdCommand command);

    }
}

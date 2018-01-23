package br.com.dreamteam.androidobdreader.presentation.connection.fragments.discovery;


import java.util.Set;

import br.com.dreamteam.androidobdreader.model.BluetoothDeviceWrapper;
import br.com.dreamteam.androidobdreader.model.datasource.obd.states.ConnectionState;

/**
 * This class is a contract for the connection screen
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */

public interface DiscoveryContract {

    /**
     * View part of the contract.
     */
    interface View {

        void showDiscoveredDevices(Set<BluetoothDeviceWrapper> data);

        /**
         * Updates the ui according to the new connection state
         *
         * @param connectionState
         */
        void updateConnectionState(ConnectionState connectionState);

        void showPairedDevices(Set<BluetoothDeviceWrapper> data);

        void showConnectionInProgress();

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
         * User wants to start the connection with a new OBD
         */
        void onUserStartConnection();


        /**
         * Use wants to connect to a device
         *
         * @param bluetoothDevice
         */
        void onUserSelectedDeviceToConnect(BluetoothDeviceWrapper bluetoothDevice);

    }
}

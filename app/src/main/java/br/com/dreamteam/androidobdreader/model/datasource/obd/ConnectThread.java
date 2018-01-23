package br.com.dreamteam.androidobdreader.model.datasource.obd;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.UUID;

import br.com.dreamteam.androidobdreader.model.datasource.obd.states.ObdConnectionFailedChanged;
import br.com.dreamteam.androidobdreader.model.datasource.obd.states.ObdConnectionSuccessfulChanged;

/**
 * Thread responsible for connecting to the OBD device
 *
 * @author João Luiz Vieira <joao.vieira@pixida.com.br>.
 */

public class ConnectThread extends Thread {
    private static final String TAG = "ConnectThread";

    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private final BluetoothDevice bluetoothDevice;
    private final EventBus eventBus;


    public ConnectThread(BluetoothDevice bluetoothDevice, EventBus eventBus) {
        this.bluetoothDevice = bluetoothDevice;
        this.eventBus = eventBus;
    }


    /**
     * Instantiates a BluetoothSocket for the remote device and connects it.
     * <p/>
     * See http://stackoverflow.com/questions/18657427/ioexception-read-failed-socket-might-closed-bluetooth-on-android-4-3/18786701#18786701
     *
     * @return The BluetoothSocket
     * @throws IOException
     */
    private BluetoothSocket connect() throws IOException {
        BluetoothSocket sock = null;
        BluetoothSocket sockFallback = null;

        Log.d(TAG, "Starting Bluetooth connection..");
        try {

            sock = bluetoothDevice.createRfcommSocketToServiceRecord(MY_UUID);
            sock.connect();
        } catch (Exception e1) {
            Log.e(TAG, "There was an error while establishing Bluetooth connection. Falling back..", e1);
            Class<?> clazz = sock.getRemoteDevice().getClass();
            Class<?>[] paramTypes = new Class<?>[]{Integer.TYPE};
            try {
                Method m = clazz.getMethod("createRfcommSocket", paramTypes);
                Object[] params = new Object[]{Integer.valueOf(1)};
                sockFallback = (BluetoothSocket) m.invoke(sock.getRemoteDevice(), params);
                sockFallback.connect();
                sock = sockFallback;
            } catch (Exception e2) {
                sock.close();
                if (sockFallback != null) {
                    sockFallback.close();
                }
                Log.e(TAG, "Couldn't fallback while establishing Bluetooth connection.", e2);
                throw new IOException(e2.getMessage());
            }
        }
        return sock;
    }

    @Override
    public void run() {
        super.run();
        try {
            BluetoothSocket socket = connect();
            eventBus.postSticky(new ObdConnectionSuccessfulChanged(Calendar.getInstance(), socket));
        } catch (IOException e) {
            eventBus.postSticky(new ObdConnectionFailedChanged(Calendar.getInstance()));
            e.printStackTrace();
        }
    }
}

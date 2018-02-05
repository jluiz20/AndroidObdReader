package br.com.dreamteam.androidobdreader.model.datasource.obd;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.github.pires.obd.commands.protocol.EchoOffCommand;
import com.github.pires.obd.commands.protocol.LineFeedOffCommand;
import com.github.pires.obd.commands.protocol.SelectProtocolCommand;
import com.github.pires.obd.commands.protocol.TimeoutCommand;
import com.github.pires.obd.commands.temperature.AmbientAirTemperatureCommand;
import com.github.pires.obd.enums.ObdProtocols;
import com.github.pires.obd.exceptions.UnsupportedCommandException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.inject.Inject;

import br.com.dreamteam.androidobdreader.R;
import br.com.dreamteam.androidobdreader.model.BluetoothDeviceWrapper;
import br.com.dreamteam.androidobdreader.model.datasource.obd.ObdCommandJob.ObdCommandJobState;
import br.com.dreamteam.androidobdreader.model.datasource.obd.states.ObdConnectionSuccessfulChanged;
import br.com.dreamteam.androidobdreader.model.usecase.UseCaseCallback;
import br.com.dreamteam.androidobdreader.model.usecase.obd.GetConnectedDeviceAddress;
import br.com.dreamteam.androidobdreader.model.usecase.obd.SetConnectToDevice;
import br.com.dreamteam.androidobdreader.presentation.main.MainActivity;

/**
 * Service responsible for connecting and communicating with the OBD device
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */

public class ObdGatewayService extends Service {
    public static final int NOTIFICATION_ID = 1;
    private static final String TAG = "ObdGatewayService";
    private final IBinder binder = new ObdGatewayServiceBinder();
    Intent connectionIntent;
    String connectedDeviceAddress;
    @Inject
    GetConnectedDeviceAddress getConnectedDeviceAddress;
    @Inject
    SetConnectToDevice setConnectToDevice;
    private final BroadcastReceiver mBroadcastReceiver3 = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            connectionIntent = intent;

            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

            switch (action) {
                case BluetoothDevice.ACTION_ACL_CONNECTED:
                    showNotification("Connected", device.getName(), R.drawable.ic_home_black_24dp, false, true, true);
                    loadConnectedDeviceAddress();
                    break;
                case BluetoothDevice.ACTION_ACL_DISCONNECTED:
                    showNotification("Disconnected", device.getName(), R.drawable.ic_home_black_24dp, false, true, true);
                    break;
            }
        }
    };
    private Long queueCounter = 0L;
    private BlockingQueue<ObdCommandJob> jobsQueue = new LinkedBlockingQueue<>();
    private BluetoothSocket socket;
    // Run the executeQueue in a different thread to lighten the UI thread
    Thread t = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                executeQueue();
            } catch (InterruptedException e) {
                t.interrupt();
            }
        }
    });

    public ObdGatewayService() {

    }

    private void loadConnectedDeviceAddress() {
        getConnectedDeviceAddress.execute(new UseCaseCallback<String>() {
            @Override
            public void onSuccess(String data) {
                Log.d(TAG, "loadConnectedDeviceAddress onSuccess: " + data);
                if (!data.isEmpty()) {
                    validateConnectedDevice(data);
                }
            }

            @Override
            public void onError(Exception e) {
                Log.e(TAG, "loadConnectedDeviceAddress onError: ", e);
            }
        });
    }

    private void connectedToDevice(BluetoothDevice bluetoothDevice) {
        setConnectToDevice.execute(new BluetoothDeviceWrapper(bluetoothDevice), new UseCaseCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                Log.d(TAG, "connectedToDevice onSuccess: " + data);
            }

            @Override
            public void onError(Exception e) {
                Log.e(TAG, "connectedToDevice onError: ", e);
            }

        });
    }

    private void validateConnectedDevice(String deviceAddress) {
        BluetoothDevice device = connectionIntent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
        if (device == null) {
            return;
        }
        if (device.getAddress().equals(deviceAddress)) {
            connectedDeviceAddress = deviceAddress;
            Log.d(TAG, "validateConnectedDevice: Ready to Rock!");
            connectedToDevice(device);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: " + intent.toString());
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        registerReceiver(mBroadcastReceiver3, intentFilter);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver3);
        EventBus.getDefault().unregister(this);
    }

    /**
     * Show a notification while this service is running.
     */
    protected void showNotification(String contentTitle, String contentText, int icon, boolean ongoing, boolean notify, boolean vibrate) {
        final PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, new Intent(getApplicationContext(), MainActivity.class), 0);
        final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext());
        notificationBuilder.setContentTitle(contentTitle)
                .setContentText(contentText).setSmallIcon(icon)
                .setContentIntent(contentIntent)
                .setWhen(System.currentTimeMillis());
        // can cancel?
        if (ongoing) {
            notificationBuilder.setOngoing(true);
        } else {
            notificationBuilder.setAutoCancel(true);
        }
        if (vibrate) {
            notificationBuilder.setDefaults(Notification.DEFAULT_VIBRATE);
        }
        if (notify) {
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(NOTIFICATION_ID, notificationBuilder.getNotification());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onObdConnectionSuccessfulChanged(ObdConnectionSuccessfulChanged event) {
        Log.d(TAG, "onObdConnectionSuccessfulChanged: " + event.getDispatchTime());
        socket = event.getBluetoothSocket();
        configureConnection();
    }

    /**
     * Runs the queue until the service is stopped
     */
    protected void executeQueue() throws InterruptedException {
        Log.d(TAG, "Executing queue..");
        while (!Thread.currentThread().isInterrupted()) {
            ObdCommandJob job = null;
            try {
                job = jobsQueue.take();

                // log job
                Log.d(TAG, "Taking job[" + job.getId() + "] from queue..");

                if (job.getState().equals(ObdCommandJobState.NEW)) {
                    Log.d(TAG, "Job state is NEW. Run it..");
                    job.setState(ObdCommandJobState.RUNNING);
                    if (socket.isConnected()) {
                        job.getCommand().run(socket.getInputStream(), socket.getOutputStream());
                    } else {
                        job.setState(ObdCommandJobState.EXECUTION_ERROR);
                        Log.e(TAG, "Can't run command on a closed socket.");
                    }
                } else
                    // log not new job
                    Log.e(TAG,
                            "Job state was not new, so it shouldn't be in queue. BUG ALERT!");
            } catch (InterruptedException i) {
                Thread.currentThread().interrupt();
            } catch (UnsupportedCommandException u) {
                if (job != null) {
                    job.setState(ObdCommandJobState.NOT_SUPPORTED);
                }
                Log.d(TAG, "Command not supported. -> " + u.getMessage());
            } catch (IOException io) {
                if (job != null) {
                    if (io.getMessage().contains("Broken pipe"))
                        job.setState(ObdCommandJobState.BROKEN_PIPE);
                    else
                        job.setState(ObdCommandJobState.EXECUTION_ERROR);
                }
                Log.e(TAG, "IO error. -> " + io.getMessage());
            } catch (Exception e) {
                if (job != null) {
                    job.setState(ObdCommandJobState.EXECUTION_ERROR);
                }
                Log.e(TAG, "Failed to run command. -> " + e.getMessage());
            }

            if (job != null) {
                final ObdCommandJob job2 = job;
                ((MainActivity) getBaseContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //   ((MainActivity) get).stateUpdate(job2);
                    }
                });
            }
        }
    }

    /**
     * add job to the queue
     *
     * @param job the job to queue.
     */
    public void queueJob(ObdCommandJob job) {
        // This is a good place to enforce the imperial units option
        //job.getCommand().useImperialUnits(prefs.getBoolean(ConfigActivity.IMPERIAL_UNITS_KEY, false));

        // Now we can pass it along
        queueCounter++;
        Log.d(TAG, "Adding job[" + queueCounter + "] to queue..");

        job.setId(queueCounter);
        try {
            jobsQueue.put(job);
            Log.d(TAG, "Job queued successfully.");
        } catch (InterruptedException e) {
            job.setState(ObdCommandJobState.QUEUE_ERROR);
            Log.e(TAG, "Failed to queue job.");
        }
    }

    private void configureConnection() {
        try {
            new EchoOffCommand().run(socket.getInputStream(), socket.getOutputStream());
            new LineFeedOffCommand().run(socket.getInputStream(), socket.getOutputStream());
            new TimeoutCommand(125).run(socket.getInputStream(), socket.getOutputStream());
            new SelectProtocolCommand(ObdProtocols.AUTO).run(socket.getInputStream(), socket.getOutputStream());
            new AmbientAirTemperatureCommand().run(socket.getInputStream(), socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "configureConnection: ", e);
        } catch (InterruptedException e) {
            Log.e(TAG, "configureConnection: ", e);
            e.printStackTrace();
        }
    }

    public class ObdGatewayServiceBinder extends Binder {
        public ObdGatewayService getService() {
            return ObdGatewayService.this;
        }
    }
}

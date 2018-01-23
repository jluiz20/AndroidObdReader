package br.com.dreamteam.androidobdreader;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import br.com.dreamteam.androidobdreader.di.AppComponent;
import br.com.dreamteam.androidobdreader.di.AppModule;
import br.com.dreamteam.androidobdreader.di.DaggerAppComponent;
import br.com.dreamteam.androidobdreader.di.DataSourceModule;
import br.com.dreamteam.androidobdreader.di.PresentationModule;
import br.com.dreamteam.androidobdreader.di.RepositoryModule;
import br.com.dreamteam.androidobdreader.model.datasource.obd.ObdGatewayService;

/**
 * This Android Application is responsible for keep state of the app and general configurations,
 * such as hockey app setup, dependency injection and so on.
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */

public class ObdReaderApplication extends Application {

    private static final String TAG = "ObdReaderApplication";
    private AppComponent appComponent;
    private ServiceConnection serviceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG, "onServiceConnected: " + componentName);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "onServiceDisconnected: " + componentName);
        }
    };

    /**
     * Keep this method separated to enable override on android instrumented tests package.
     */
    AppComponent createComponent() {

        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .dataSourceModule(new DataSourceModule())
                .presentationModule(new PresentationModule())
                .repositoryModule(new RepositoryModule())
                .build();
    }

    public AppComponent component() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = createComponent();
        appComponent.inject(this);

        Intent serviceIntent = new Intent(this, ObdGatewayService.class);
        bindService(serviceIntent, serviceConn, Context.BIND_AUTO_CREATE);
    }
}

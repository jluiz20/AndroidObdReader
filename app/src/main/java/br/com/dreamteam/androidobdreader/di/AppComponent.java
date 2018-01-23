package br.com.dreamteam.androidobdreader.di;


import javax.inject.Singleton;

import br.com.dreamteam.androidobdreader.ObdReaderApplication;
import br.com.dreamteam.androidobdreader.presentation.connection.ConnectionStepsActivity;
import br.com.dreamteam.androidobdreader.presentation.connection.fragments.connecting.ConnectingFragment;
import br.com.dreamteam.androidobdreader.presentation.connection.fragments.connectobd.ConnectObdFragment;
import br.com.dreamteam.androidobdreader.presentation.connection.fragments.discovery.DiscoveryFragment;
import br.com.dreamteam.androidobdreader.presentation.connection.fragments.turnvehicleon.TurnVehicleOnFragment;
import br.com.dreamteam.androidobdreader.presentation.main.MainActivity;
import dagger.Component;

/**
 * App Component that injects key classes.
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */
@Singleton
@Component(modules = {AppModule.class, PresentationModule.class, DataSourceModule.class, RepositoryModule.class})
public interface AppComponent {
    void inject(ObdReaderApplication obdReaderApplication);

    void inject(MainActivity mainActivity);

    void inject(ConnectionStepsActivity connectionStepsActivity);

    void inject(ConnectObdFragment connectObdFragment);

    void inject(TurnVehicleOnFragment turnVehicleOnFragment);

    void inject(DiscoveryFragment discoveryFragment);


    void inject(ConnectingFragment connectingFragment);
}

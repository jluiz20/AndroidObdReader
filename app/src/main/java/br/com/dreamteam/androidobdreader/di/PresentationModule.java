package br.com.dreamteam.androidobdreader.di;


import br.com.dreamteam.androidobdreader.presentation.connection.ConnectionStepsContract;
import br.com.dreamteam.androidobdreader.presentation.connection.ConnectionStepsPresenter;
import br.com.dreamteam.androidobdreader.presentation.connection.fragments.connecting.ConnectingContract;
import br.com.dreamteam.androidobdreader.presentation.connection.fragments.connecting.ConnectingPresenter;
import br.com.dreamteam.androidobdreader.presentation.connection.fragments.connectobd.ConnectObdContract;
import br.com.dreamteam.androidobdreader.presentation.connection.fragments.connectobd.ConnectObdPresenter;
import br.com.dreamteam.androidobdreader.presentation.connection.fragments.discovery.DiscoveryContract;
import br.com.dreamteam.androidobdreader.presentation.connection.fragments.discovery.DiscoveryPresenter;
import br.com.dreamteam.androidobdreader.presentation.connection.fragments.turnvehicleon.TurnVehicleOnContract;
import br.com.dreamteam.androidobdreader.presentation.connection.fragments.turnvehicleon.TurnVehicleOnPresenter;
import br.com.dreamteam.androidobdreader.presentation.main.MainContract;
import br.com.dreamteam.androidobdreader.presentation.main.MainPresenter;
import dagger.Module;
import dagger.Provides;

/**
 * Creates all dependencies related to presentation layer.
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */
@Module
public class PresentationModule {

    @Provides
    @SuppressWarnings("WeakerAccess")
    public MainContract.Presenter providesMainPresenter(MainPresenter presenter) {
        return presenter;
    }

    @Provides
    @SuppressWarnings("WeakerAccess")
    public ConnectionStepsContract.Presenter providesConnectionPresenter(ConnectionStepsPresenter presenter) {
        return presenter;
    }

    @Provides
    @SuppressWarnings("WeakerAccess")
    public ConnectObdContract.Presenter providesConnectObdPresenter(ConnectObdPresenter presenter) {
        return presenter;
    }

    @Provides
    @SuppressWarnings("WeakerAccess")
    public TurnVehicleOnContract.Presenter providesTurnVehicleOnPresenter(TurnVehicleOnPresenter presenter) {
        return presenter;
    }

    @Provides
    @SuppressWarnings("WeakerAccess")
    public DiscoveryContract.Presenter providesDiscoveryPresenter(DiscoveryPresenter presenter) {
        return presenter;
    }

    @Provides
    @SuppressWarnings("WeakerAccess")
    public ConnectingContract.Presenter providesConnectingPresenter(ConnectingPresenter presenter) {
        return presenter;
    }

}

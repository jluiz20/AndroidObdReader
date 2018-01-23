package br.com.dreamteam.androidobdreader.di;

import javax.inject.Singleton;

import br.com.dreamteam.androidobdreader.presentation.connection.ConnectionStepsContract;
import br.com.dreamteam.androidobdreader.presentation.connection.fragments.connecting.ConnectingContract;
import br.com.dreamteam.androidobdreader.presentation.connection.fragments.connectobd.ConnectObdContract;
import br.com.dreamteam.androidobdreader.presentation.connection.fragments.discovery.DiscoveryContract;
import br.com.dreamteam.androidobdreader.presentation.connection.fragments.turnvehicleon.TurnVehicleOnContract;
import br.com.dreamteam.androidobdreader.presentation.main.MainContract;
import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

/**
 * This module in general should only return {@link Singleton} and Mock instances.
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */
@Module
public class MockPresentationModule {

    @Provides
    @Singleton
    MainContract.Presenter providesPresenter() {
        return mock(MainContract.Presenter.class);
    }

    @Provides
    @Singleton
    ConnectionStepsContract.Presenter providesConnectionPresenter() {
        return mock(ConnectionStepsContract.Presenter.class);
    }

    @Provides
    @Singleton
    DiscoveryContract.Presenter providesDiscoveryPresenter() {
        return mock(DiscoveryContract.Presenter.class);
    }

    @Provides
    @Singleton
    ConnectObdContract.Presenter providesConnectObdPresenter() {
        return mock(ConnectObdContract.Presenter.class);
    }

    @Provides
    @Singleton
    TurnVehicleOnContract.Presenter providesTurnVehicleOnPresenter() {
        return mock(TurnVehicleOnContract.Presenter.class);
    }

    @Provides
    @Singleton
    ConnectingContract.Presenter providesConnectingPresenter() {
        return mock(ConnectingContract.Presenter.class);
    }

}

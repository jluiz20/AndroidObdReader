package br.com.dreamteam.androidobdreader.di;


import javax.inject.Singleton;

import br.com.dreamteam.androidobdreader.presentation.connection.fragments.connecting.ConnectingFragmentTest;
import br.com.dreamteam.androidobdreader.presentation.connection.fragments.connectobd.ConnectObdFragmentTest;
import br.com.dreamteam.androidobdreader.presentation.connection.fragments.discovery.DiscoveryFragmentTest;
import br.com.dreamteam.androidobdreader.presentation.connection.fragments.turnvehicleon.TurnVehicleOnFragmentTest;
import br.com.dreamteam.androidobdreader.presentation.main.MainActivityTest;
import dagger.Component;

/**
 * Test component that uses a {@link br.com.dreamteam.androidobdreader.MockObdReaderApplication} to enable mocks on
 * android instrumented tests.
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */
@Singleton
@Component(modules = {MockPresentationModule.class, RepositoryModule.class, AppModule.class})
public interface TestComponent extends AppComponent {

    void inject(MainActivityTest mainActivityTest);

    void inject(ConnectObdFragmentTest connectObdFragmentTest);

    void inject(TurnVehicleOnFragmentTest turnVehicleOnFragmentTest);

    void inject(DiscoveryFragmentTest discoveryFragmentTest);

    void inject(ConnectingFragmentTest connectingFragmentTest);
}

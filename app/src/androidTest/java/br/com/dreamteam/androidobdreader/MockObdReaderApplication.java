package br.com.dreamteam.androidobdreader;


import br.com.dreamteam.androidobdreader.di.AppModule;
import br.com.dreamteam.androidobdreader.di.DaggerTestComponent;
import br.com.dreamteam.androidobdreader.di.MockPresentationModule;
import br.com.dreamteam.androidobdreader.di.RepositoryModule;
import br.com.dreamteam.androidobdreader.di.TestComponent;

/**
 * Android application to enable mock on android instrumented tests.
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */

public class MockObdReaderApplication extends ObdReaderApplication {

    @Override
    public TestComponent createComponent() {
        return DaggerTestComponent.builder()
                .appModule(new AppModule(this))
                .repositoryModule(new RepositoryModule())
                .mockPresentationModule(new MockPresentationModule())
                .build();
    }

}

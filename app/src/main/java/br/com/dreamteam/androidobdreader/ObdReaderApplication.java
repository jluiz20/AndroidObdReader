package br.com.dreamteam.androidobdreader;

import android.app.Application;

import br.com.dreamteam.androidobdreader.di.AppComponent;
import br.com.dreamteam.androidobdreader.di.AppModule;
import br.com.dreamteam.androidobdreader.di.DaggerAppComponent;
import br.com.dreamteam.androidobdreader.di.DataSourceModule;
import br.com.dreamteam.androidobdreader.di.PresentationModule;
import br.com.dreamteam.androidobdreader.di.RepositoryModule;

/**
 * This Android Application is responsible for keep state of the app and general configurations,
 * such as hockey app setup, dependency injection and so on.
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */

public class ObdReaderApplication extends Application {

    private AppComponent appComponent = createComponent();

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
}

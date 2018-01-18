package br.com.dreamteam.androidobdreader.di;


import javax.inject.Singleton;

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
    void inject(MainActivity mainActivity);
}

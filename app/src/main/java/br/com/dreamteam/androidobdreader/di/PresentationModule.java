package br.com.dreamteam.androidobdreader.di;


import br.com.dreamteam.androidobdreader.presentation.main.MainActivityContract;
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
    public MainActivityContract.Presenter providesMainPresenter(MainPresenter presenter) {
        return presenter;
    }

}

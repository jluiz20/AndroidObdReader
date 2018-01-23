package br.com.dreamteam.androidobdreader.di;

import javax.inject.Singleton;

import br.com.dreamteam.androidobdreader.model.datasource.obd.ObdBluetoothDataSource;
import br.com.dreamteam.androidobdreader.model.datasource.obd.ObdDataSource;
import br.com.dreamteam.androidobdreader.model.datasource.version.LocalVersionDataSource;
import br.com.dreamteam.androidobdreader.model.datasource.version.VersionDataSource;
import dagger.Module;
import dagger.Provides;

/**
 * Module responsible for generate instances of classes related to the model data.
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */
@Module
public class DataSourceModule {

    @Provides
    @Singleton
    VersionDataSource providesVersionDataSource() {
        return new LocalVersionDataSource();
    }

    @Provides
    @Singleton
    ObdDataSource providesObdDataSource(ObdBluetoothDataSource obdBluetoothDataSource) {
        return obdBluetoothDataSource;
    }


}

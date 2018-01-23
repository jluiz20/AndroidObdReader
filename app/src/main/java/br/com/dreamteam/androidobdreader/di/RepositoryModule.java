package br.com.dreamteam.androidobdreader.di;

import javax.inject.Singleton;

import br.com.dreamteam.androidobdreader.model.datasource.version.VersionDataSource;
import br.com.dreamteam.androidobdreader.model.repository.obd.ObdRepository;
import br.com.dreamteam.androidobdreader.model.repository.obd.ObdRepositoryImpl;
import br.com.dreamteam.androidobdreader.model.repository.version.VersionRepository;
import br.com.dreamteam.androidobdreader.model.repository.version.VersionRepositoryImpl;
import dagger.Module;
import dagger.Provides;

/**
 * This class provides all the repositories that exists in this application.
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */
@Module
public class RepositoryModule {

    @Provides
    @Singleton
    VersionRepository providesVersionRepository(VersionDataSource versionDataSource) {
        return new VersionRepositoryImpl(versionDataSource);
    }

    @Provides
    @Singleton
    ObdRepository providesObdRepository(ObdRepositoryImpl obdRepository) {
        return obdRepository;
    }

}

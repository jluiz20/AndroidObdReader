package br.com.dreamteam.androidobdreader.model.repository.version;


import android.util.Log;

import javax.inject.Inject;

import br.com.dreamteam.androidobdreader.model.datasource.version.VersionDataSource;

/**
 * Implementation of {@link VersionRepository}
 * * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */

public class VersionRepositoryImpl implements VersionRepository {

    private static final String TAG = "VersionRepositoryImpl";
    private VersionDataSource versionDataSource;

    @Inject
    public VersionRepositoryImpl(VersionDataSource versionDataSource) {
        this.versionDataSource = versionDataSource;
    }

    @Override
    public String getAppVersion() {
        Log.d(TAG, "getAppVersion: ");
        return versionDataSource.getAppVersion();
    }
}

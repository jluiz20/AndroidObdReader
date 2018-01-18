package br.com.dreamteam.androidobdreader.model.datasource.version;


import br.com.dreamteam.androidobdreader.BuildConfig;

/**
 * This class returns all information related to the current version and build locally.
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */

public class LocalVersionDataSource implements VersionDataSource {
    @Override
    public String getAppVersion() {
        return BuildConfig.VERSION_NAME;
    }
}

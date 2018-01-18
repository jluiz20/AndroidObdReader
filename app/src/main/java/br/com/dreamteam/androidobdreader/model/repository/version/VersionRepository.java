package br.com.dreamteam.androidobdreader.model.repository.version;

/**
 * This class is the repository responsible for all the information related to the build.
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */

public interface VersionRepository {

    /**
     * Returns the app version of the application.
     */
    String getAppVersion();
}

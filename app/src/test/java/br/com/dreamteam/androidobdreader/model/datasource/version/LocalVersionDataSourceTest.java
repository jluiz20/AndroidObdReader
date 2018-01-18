package br.com.dreamteam.androidobdreader.model.datasource.version;

import org.junit.Test;

import br.com.dreamteam.androidobdreader.BuildConfig;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link LocalVersionDataSource}
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */
public class LocalVersionDataSourceTest {


    @Test
    public void getAppVersion() throws Exception {

        LocalVersionDataSource localVersionDataSource = new LocalVersionDataSource();
        String appVersion = localVersionDataSource.getAppVersion();

        assertEquals(BuildConfig.VERSION_NAME, appVersion);
    }

}
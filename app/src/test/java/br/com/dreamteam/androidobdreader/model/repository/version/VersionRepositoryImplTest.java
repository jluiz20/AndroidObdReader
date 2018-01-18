package br.com.dreamteam.androidobdreader.model.repository.version;

import android.util.Log;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.com.dreamteam.androidobdreader.model.datasource.version.VersionDataSource;

import static junit.framework.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;


/**
 * Tests for {@link VersionRepositoryImpl}
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Log.class)
public class VersionRepositoryImplTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @InjectMocks
    private VersionRepositoryImpl versionRepository;

    @Mock
    private VersionDataSource versionDataSource;

    @Before
    public void setUp() throws Exception {
        mockStatic(Log.class);
    }

    @Test
    public void test_getAppVersion() throws Exception {
        when(versionDataSource.getAppVersion()).thenReturn("0.0.1");

        String appVersion = versionRepository.getAppVersion();

        assertEquals("0.0.1", appVersion);

    }

}
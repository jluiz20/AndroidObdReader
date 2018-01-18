package br.com.dreamteam.androidobdreader.model.usecase.version;

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

import br.com.dreamteam.androidobdreader.model.repository.version.VersionRepository;
import br.com.dreamteam.androidobdreader.model.usecase.UseCaseCallback;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;


/**
 * Tests for {@link GetAppVersion}
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Log.class)
public class GetAppVersionTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @InjectMocks
    @SuppressWarnings("WeakerAccess")
    GetAppVersion getAppVersion;

    @Mock
    private VersionRepository versionRepository;

    @Mock
    @SuppressWarnings("WeakerAccess")
    private UseCaseCallback<String> caseCallback;

    @Mock
    private RuntimeException runtimeException;

    @Before
    public void setUp() throws Exception {
        mockStatic(Log.class);
    }

    @Test
    public void test_execute() throws Exception {

        when(versionRepository.getAppVersion()).thenReturn("0.0.1");

        getAppVersion.execute(caseCallback);

        verify(caseCallback).onSuccess("0.0.1");
    }

    @Test
    public void test_execute_error() throws Exception {

        doThrow(runtimeException).when(versionRepository).getAppVersion();

        getAppVersion.execute(caseCallback);

        verify(caseCallback).onError(runtimeException);
    }

}
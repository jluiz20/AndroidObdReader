package br.com.dreamteam.androidobdreader.presentation.main;

import android.util.Log;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.com.dreamteam.androidobdreader.model.usecase.UseCaseCallback;
import br.com.dreamteam.androidobdreader.model.usecase.version.GetAppVersion;

import static junit.framework.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.powermock.api.mockito.PowerMockito.mockStatic;


/**
 * Tests for {@link MainPresenter}
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Log.class)
public class MainPresenterTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @InjectMocks
    @SuppressWarnings("WeakerAccess")
    MainPresenter mainPresenter;

    @Mock
    private GetAppVersion getAppVersion;
    @Mock
    private MainActivityContract.View view;
    @Captor
    private ArgumentCaptor<UseCaseCallback<String>> caseCallbackArgumentCaptor;
    @Mock
    private Exception exception;

    @Before
    public void setUp() throws Exception {
        mockStatic(Log.class);
    }

    @Test
    public void test_onViewResume() throws Exception {
        mainPresenter.onViewResume(view);

        assertNotNull(mainPresenter.getView());
        verify(getAppVersion).execute(any(UseCaseCallback.class));
    }

    @Test
    public void test_loadAppVersion() throws Exception {

        mainPresenter.loadAppVersion();

        verify(getAppVersion).execute(caseCallbackArgumentCaptor.capture());

        caseCallbackArgumentCaptor.getValue().onSuccess("1.0.0");

        verify(view).showAppVersion("1.0.0");
    }

    @Test
    public void test_loadAppVersionViewPause() throws Exception {

        mainPresenter.loadAppVersion();
        mainPresenter.onViewPause(view);

        verify(getAppVersion).execute(caseCallbackArgumentCaptor.capture());

        caseCallbackArgumentCaptor.getValue().onSuccess("1.0.0");

        verifyZeroInteractions(view);
    }

    @Test
    public void test_loadAppVersion_error() throws Exception {

        mainPresenter.loadAppVersion();

        verify(getAppVersion).execute(caseCallbackArgumentCaptor.capture());

        caseCallbackArgumentCaptor.getValue().onError(exception);

        verifyZeroInteractions(view);
    }

}
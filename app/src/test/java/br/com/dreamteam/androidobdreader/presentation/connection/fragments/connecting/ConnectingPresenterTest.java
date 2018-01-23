package br.com.dreamteam.androidobdreader.presentation.connection.fragments.connecting;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;
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

import java.util.Calendar;

import br.com.dreamteam.androidobdreader.model.datasource.obd.states.ObdConnectionFailedChanged;
import br.com.dreamteam.androidobdreader.model.datasource.obd.states.ObdConnectionSuccessfulChanged;
import br.com.dreamteam.androidobdreader.model.usecase.UseCaseCallback;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

/**
 * Tests for {@link ConnectingPresenter}
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Log.class)
public class ConnectingPresenterTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @InjectMocks
    @SuppressWarnings("WeakerAccess")
    ConnectingPresenter presenter;

    @Mock
    private ConnectingContract.View view;

    @Mock
    private EventBus eventBus;

    @Mock
    private Exception exception;

    @Mock
    private ObdConnectionSuccessfulChanged obdConnectionSuccessfulChanged;
    @Mock
    private ObdConnectionFailedChanged obdConnectionFailedChanged;

    @Captor
    private ArgumentCaptor<UseCaseCallback<Void>> voidCallbackArgumentCaptor;

    @Before
    public void setUp() throws Exception {
        mockStatic(Log.class);
    }

    @Test
    public void test_onViewResume() throws Exception {
        presenter.onViewResume(view);
        verify(eventBus).register(presenter);
        assertNotNull(presenter.getView());
    }

    @Test
    public void test_onViewPause() throws Exception {
        presenter.onViewPause(view);
        verify(eventBus).unregister(presenter);
        assertNull(presenter.getView());
    }

    @Test
    public void test_onObdConnectionSuccessfulChanged() throws Exception {
        when(obdConnectionSuccessfulChanged.getDispatchTime()).thenReturn(Calendar.getInstance());

        presenter.onObdConnectionSuccessfulChanged(obdConnectionSuccessfulChanged);
        verify(view).showConnectionSuccess();
    }

    @Test
    public void test_onObdConnectionFailedChanged() throws Exception {
        when(obdConnectionFailedChanged.getDispatchTime()).thenReturn(Calendar.getInstance());

        presenter.onObdConnectionFailedChanged(obdConnectionFailedChanged);
        verify(view).showConnectionFailed();
    }

}
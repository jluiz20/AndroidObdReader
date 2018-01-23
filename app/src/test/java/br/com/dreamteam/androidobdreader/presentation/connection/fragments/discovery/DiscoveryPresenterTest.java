package br.com.dreamteam.androidobdreader.presentation.connection.fragments.discovery;

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
import java.util.Set;

import br.com.dreamteam.androidobdreader.model.BluetoothDeviceWrapper;
import br.com.dreamteam.androidobdreader.model.datasource.obd.states.ConnectionState;
import br.com.dreamteam.androidobdreader.model.datasource.obd.states.ObdDiscoveryStateChanged;
import br.com.dreamteam.androidobdreader.model.usecase.UseCaseCallback;
import br.com.dreamteam.androidobdreader.model.usecase.obd.GetConnectionState;
import br.com.dreamteam.androidobdreader.model.usecase.obd.GetDiscoveredDevices;
import br.com.dreamteam.androidobdreader.model.usecase.obd.GetPairedDevices;
import br.com.dreamteam.androidobdreader.model.usecase.obd.SetConnectToDevice;
import br.com.dreamteam.androidobdreader.model.usecase.obd.SetStartDiscovery;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

/**
 * Tests for {@link DiscoveryPresenter}
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Log.class)
public class DiscoveryPresenterTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @InjectMocks
    @SuppressWarnings("WeakerAccess")
    DiscoveryPresenter presenter;

    @Mock
    private DiscoveryContract.View view;

    @Mock
    private EventBus eventBus;

    @Mock
    private Exception exception;

    @Mock
    private GetPairedDevices getPairedDevices;
    @Mock
    private GetDiscoveredDevices getDiscoveredDevices;

    @Mock
    private GetConnectionState getConnectionState;

    @Mock
    private Set<BluetoothDeviceWrapper> bluetoothDevices;

    @Captor
    private ArgumentCaptor<UseCaseCallback<Set<BluetoothDeviceWrapper>>> devicesCallbackArgumentCaptor;

    @Captor
    private ArgumentCaptor<UseCaseCallback<ConnectionState>> connectionStateCallbackArgumentCaptor;
    @Captor
    private ArgumentCaptor<UseCaseCallback<Void>> voidCallbackArgumentCaptor;

    @Mock
    private BluetoothDeviceWrapper bluetoothDevice;

    @Mock
    private ObdDiscoveryStateChanged obdDiscoveryStateChanged;

    @Mock
    private SetConnectToDevice setConnectToDevice;

    @Mock
    private SetStartDiscovery setStartDiscovery;

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
    public void test_loadPairedDevices() throws Exception {
        presenter.loadPairedDevices();

        verify(getPairedDevices).execute(devicesCallbackArgumentCaptor.capture());
        devicesCallbackArgumentCaptor.getValue().onSuccess(bluetoothDevices);

        verify(view).showPairedDevices(bluetoothDevices);
    }

    @Test
    public void test_loadPairedDevices_ViewPause() throws Exception {

        presenter.loadPairedDevices();
        presenter.onViewPause(view);

        verify(getPairedDevices).execute(devicesCallbackArgumentCaptor.capture());
        devicesCallbackArgumentCaptor.getValue().onSuccess(bluetoothDevices);

        verifyZeroInteractions(view);
    }

    @Test
    public void test_loadPairedDevices_error() throws Exception {

        presenter.loadPairedDevices();

        verify(getPairedDevices).execute(devicesCallbackArgumentCaptor.capture());
        devicesCallbackArgumentCaptor.getValue().onError(exception);
        verifyZeroInteractions(view);
    }

    @Test
    public void test_loadDiscoveredDevices() throws Exception {
        presenter.loadDiscoveredDevices();

        verify(getDiscoveredDevices).execute(devicesCallbackArgumentCaptor.capture());
        devicesCallbackArgumentCaptor.getValue().onSuccess(bluetoothDevices);

        verify(view).showDiscoveredDevices(bluetoothDevices);
    }

    @Test
    public void test_loadDiscoveredDevices_ViewPause() throws Exception {

        presenter.loadDiscoveredDevices();
        presenter.onViewPause(view);

        verify(getDiscoveredDevices).execute(devicesCallbackArgumentCaptor.capture());
        devicesCallbackArgumentCaptor.getValue().onSuccess(bluetoothDevices);

        verifyZeroInteractions(view);
    }

    @Test
    public void test_loadDiscoveredDevices_error() throws Exception {

        presenter.loadDiscoveredDevices();

        verify(getDiscoveredDevices).execute(devicesCallbackArgumentCaptor.capture());
        devicesCallbackArgumentCaptor.getValue().onError(exception);
        verifyZeroInteractions(view);
    }

    @Test
    public void test_loadConnectionState() throws Exception {
        ConnectionState connectionState = ConnectionState.DISCOVERY_STARTED;

        presenter.loadConnectionState();

        verify(getConnectionState).execute(connectionStateCallbackArgumentCaptor.capture());
        connectionStateCallbackArgumentCaptor.getValue().onSuccess(connectionState);

        verify(view).updateConnectionState(connectionState);
    }

    @Test
    public void test_loadConnectionState_ViewPause() throws Exception {
        ConnectionState connectionState = ConnectionState.DISCOVERY_STARTED;
        presenter.loadConnectionState();
        presenter.onViewPause(view);

        verify(getConnectionState).execute(connectionStateCallbackArgumentCaptor.capture());
        connectionStateCallbackArgumentCaptor.getValue().onSuccess(connectionState);

        verifyZeroInteractions(view);
    }

    @Test
    public void test_loadConnectionState_error() throws Exception {

        presenter.loadConnectionState();

        verify(getConnectionState).execute(connectionStateCallbackArgumentCaptor.capture());
        connectionStateCallbackArgumentCaptor.getValue().onError(exception);
        verifyZeroInteractions(view);
    }

    @Test
    public void test_onUserStartConnection_Success() throws Exception {
        presenter.onUserStartConnection();

        verify(setStartDiscovery).execute(voidCallbackArgumentCaptor.capture());
        voidCallbackArgumentCaptor.getValue().onSuccess(null);

        verifyZeroInteractions(view);
    }

    @Test
    public void test_onUserStartConnection_Error() throws Exception {
        presenter.onUserStartConnection();

        verify(setStartDiscovery).execute(voidCallbackArgumentCaptor.capture());
        voidCallbackArgumentCaptor.getValue().onError(exception);

        verifyZeroInteractions(view);
    }

    @Test
    public void test_onObdDiscoveryStateChanged() throws Exception {
        when(obdDiscoveryStateChanged.getDispatchTime()).thenReturn(Calendar.getInstance());

        presenter.onObdDiscoveryStateChanged(obdDiscoveryStateChanged);
        verify(getDiscoveredDevices).execute(devicesCallbackArgumentCaptor.capture());
        verify(getConnectionState).execute(connectionStateCallbackArgumentCaptor.capture());
    }

    @Test
    public void test_onUserSelectedDeviceToConnect_Success() throws Exception {
        presenter.onUserSelectedDeviceToConnect(bluetoothDevice);

        verify(setConnectToDevice).execute(eq(bluetoothDevice), voidCallbackArgumentCaptor.capture());
        voidCallbackArgumentCaptor.getValue().onSuccess(null);

        verify(view).showConnectionInProgress();
    }

    @Test
    public void test_onUserSelectedDeviceToConnect_Error() throws Exception {
        presenter.onUserSelectedDeviceToConnect(bluetoothDevice);

        verify(setConnectToDevice).execute(eq(bluetoothDevice), voidCallbackArgumentCaptor.capture());
        voidCallbackArgumentCaptor.getValue().onError(exception);

        verifyZeroInteractions(view);
    }


}
package br.com.dreamteam.androidobdreader.model.datasource.obd;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.IntentFilter;
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

import java.util.Set;

import javax.inject.Named;

import br.com.dreamteam.androidobdreader.model.BluetoothDeviceWrapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

/**
 * Tests for {@link ObdBluetoothDataSource}
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Log.class, BluetoothAdapter.class, ObdBluetoothDataSource.class})
public class ObdBluetoothDataSourceTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    BluetoothAdapter bluetoothAdapter;

    @Mock
    @Named("applicationContext")
    Context context;
    @Mock
    EventBus eventBus;
    @InjectMocks
    ObdBluetoothDataSource dataSource;

    @Captor
    ArgumentCaptor<IntentFilter> intentFilterArgumentCaptor;

    @Mock
    BluetoothDevice bluetoothDevice;

    @Mock
    Set<BluetoothDevice> bluetoothDevices;

    @Mock
    ConnectThread connectThread;

    @Before
    public void setUp() throws Exception {
        mockStatic(Log.class);
        mockStatic(BluetoothAdapter.class);
        when(BluetoothAdapter.getDefaultAdapter()).thenReturn(bluetoothAdapter);
    }

    @Test
    public void test_isBluetoothSupported() throws Exception {
        boolean isBluetoothSupported = dataSource.isBluetoothSupported();
        assertTrue(isBluetoothSupported);
    }

    @Test
    public void test_getPairedDevices() throws Exception {
        when(BluetoothAdapter.getDefaultAdapter()).thenReturn(bluetoothAdapter);
        when(bluetoothAdapter.getBondedDevices()).thenReturn(bluetoothDevices);
        Set<BluetoothDeviceWrapper> devices = dataSource.getPairedDevices();
        assertEquals(bluetoothDevices.size(), devices.size());
    }

    @Test
    public void test_startDiscovery_disabled() throws Exception {
        when(bluetoothAdapter.isEnabled()).thenReturn(false);
        dataSource.startDiscovery();

        verify(bluetoothAdapter).enable();
        verify(bluetoothAdapter).startDiscovery();
        verify(context, times(3)).registerReceiver(any(), intentFilterArgumentCaptor.capture());
    }

    @Test
    public void test_startDiscovery_enabled() throws Exception {
        when(bluetoothAdapter.isEnabled()).thenReturn(true);
        dataSource.startDiscovery();

        verify(bluetoothAdapter).startDiscovery();
        verify(context, times(3)).registerReceiver(any(), intentFilterArgumentCaptor.capture());
    }
}
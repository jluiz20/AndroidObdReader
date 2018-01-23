package br.com.dreamteam.androidobdreader.model.repository.obd;

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

import java.util.Set;

import br.com.dreamteam.androidobdreader.model.BluetoothDeviceWrapper;
import br.com.dreamteam.androidobdreader.model.datasource.obd.ObdBluetoothDataSource;
import br.com.dreamteam.androidobdreader.model.datasource.obd.states.ConnectionState;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Tests for {@link ObdRepositoryImpl}
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Log.class)
public class ObdRepositoryImplTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @InjectMocks
    private ObdRepositoryImpl obdRepository;

    @Mock
    private ObdBluetoothDataSource obdBluetoothDataSource;

    @Mock
    private Set<BluetoothDeviceWrapper> bluetoothDevices;


    @Mock
    private BluetoothDeviceWrapper bluetoothDevice;

    @Before
    public void setUp() throws Exception {
        mockStatic(Log.class);
    }

    @Test
    public void test_isBluetoothSupported() throws Exception {
        when(obdBluetoothDataSource.isBluetoothSupported()).thenReturn(true);

        boolean isBluetoothSupported = obdRepository.getIsBluetoothSupported();

        assertEquals(true, isBluetoothSupported);
    }

    @Test
    public void test_getPairedDevices() throws Exception {
        when(obdBluetoothDataSource.getPairedDevices()).thenReturn(bluetoothDevices);

        Set<BluetoothDeviceWrapper> devices = obdRepository.getPairedDevices();

        assertEquals(bluetoothDevices, devices);
    }

    @Test
    public void test_getDiscoveredDevices() throws Exception {
        when(obdBluetoothDataSource.getDiscoveredDevices()).thenReturn(bluetoothDevices);

        Set<BluetoothDeviceWrapper> devices = obdRepository.getDiscoveredDevices();

        assertEquals(bluetoothDevices, devices);
    }

    @Test
    public void test_startDiscovery() throws Exception {
        obdRepository.startDiscovery();
        verify(obdBluetoothDataSource).startDiscovery();
    }

    @Test
    public void test_getConnectionState() throws Exception {
        ConnectionState connectionState = ConnectionState.UNKNOWN;
        when(obdBluetoothDataSource.getConnectionState()).thenReturn(connectionState);

        ConnectionState state = obdRepository.getConnectionState();

        assertEquals(connectionState, state);
    }

    @Test
    public void test_connectToDevice() throws Exception {
        obdRepository.connectToDevice(bluetoothDevice);
        verify(obdBluetoothDataSource).connectToDevice(bluetoothDevice);
    }

}
package br.com.dreamteam.androidobdreader.model.usecase.obd;

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
import br.com.dreamteam.androidobdreader.model.repository.obd.ObdRepository;
import br.com.dreamteam.androidobdreader.model.usecase.UseCaseCallback;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Tests for {@link GetDiscoveredDevices}
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Log.class)
public class GetDiscoveredDevicesTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @InjectMocks
    @SuppressWarnings("WeakerAccess")
    GetDiscoveredDevices getDiscoveredDevices;

    @Mock
    private ObdRepository obdRepository;

    @Mock
    @SuppressWarnings("WeakerAccess")
    private UseCaseCallback<Set<BluetoothDeviceWrapper>> caseCallback;

    @Mock
    private Set<BluetoothDeviceWrapper> bluetoothDevices;

    @Mock
    private RuntimeException runtimeException;

    @Before
    public void setUp() throws Exception {
        mockStatic(Log.class);
    }

    @Test
    public void test_execute() throws Exception {
        when(obdRepository.getDiscoveredDevices()).thenReturn(bluetoothDevices);

        getDiscoveredDevices.execute(caseCallback);

        verify(caseCallback).onSuccess(bluetoothDevices);
    }

    @Test
    public void test_execute_error() throws Exception {

        doThrow(runtimeException).when(obdRepository).getDiscoveredDevices();

        getDiscoveredDevices.execute(caseCallback);

        verify(caseCallback).onError(runtimeException);
    }
}
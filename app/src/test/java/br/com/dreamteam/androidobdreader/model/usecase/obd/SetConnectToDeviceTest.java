package br.com.dreamteam.androidobdreader.model.usecase.obd;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import br.com.dreamteam.androidobdreader.model.BluetoothDeviceWrapper;
import br.com.dreamteam.androidobdreader.model.repository.obd.ObdRepository;
import br.com.dreamteam.androidobdreader.model.usecase.UseCaseCallback;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link SetConnectToDevice}
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */
public class SetConnectToDeviceTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @InjectMocks
    private SetConnectToDevice setConnectToDevice;

    @Mock
    private ObdRepository obdRepository;

    @Mock
    private UseCaseCallback<Void> callbackMock;

    @Mock
    private RuntimeException runTimeException;

    @Mock
    private BluetoothDeviceWrapper bluetoothDevice;

    @Test
    public void testExecuteSuccess() {
        setConnectToDevice.execute(bluetoothDevice, callbackMock);

        verify(obdRepository).connectToDevice(bluetoothDevice);
        verify(callbackMock).onSuccess(null);
    }

    @Test
    public void testExecuteThrowingException() throws Exception {
        doThrow(runTimeException).when(obdRepository).connectToDevice(bluetoothDevice);

        setConnectToDevice.execute(bluetoothDevice, callbackMock);

        verify(callbackMock).onError(runTimeException);
    }
}
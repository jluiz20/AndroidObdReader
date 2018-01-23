package br.com.dreamteam.androidobdreader.model.usecase.obd;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import br.com.dreamteam.androidobdreader.model.repository.obd.ObdRepository;
import br.com.dreamteam.androidobdreader.model.usecase.UseCaseCallback;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link SetStartDiscovery}
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */
public class SetStartDiscoveryTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @InjectMocks
    private SetStartDiscovery setStartDiscovery;

    @Mock
    private ObdRepository obdRepository;

    @Mock
    private UseCaseCallback<Void> callbackMock;

    @Mock
    private RuntimeException runTimeException;

    @Test
    public void testExecuteSuccess() {
        setStartDiscovery.execute(callbackMock);

        verify(obdRepository).startDiscovery();
        verify(callbackMock).onSuccess(null);
    }

    @Test
    public void testExecuteThrowingException() throws Exception {
        doThrow(runTimeException).when(obdRepository).startDiscovery();

        setStartDiscovery.execute(callbackMock);

        verify(callbackMock).onError(runTimeException);
    }
}
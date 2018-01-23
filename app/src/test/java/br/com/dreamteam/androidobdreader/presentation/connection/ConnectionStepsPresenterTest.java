package br.com.dreamteam.androidobdreader.presentation.connection;

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

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

/**
 * Tests for {@link ConnectionStepsPresenter}
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Log.class)
public class ConnectionStepsPresenterTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @InjectMocks
    @SuppressWarnings("WeakerAccess")
    ConnectionStepsPresenter presenter;

    @Mock
    private ConnectionStepsContract.View view;

    @Mock
    private Exception exception;

    @Before
    public void setUp() throws Exception {
        mockStatic(Log.class);
    }

    @Test
    public void test_onViewResume() throws Exception {
        presenter.onViewResume(view);
        assertNotNull(presenter.getView());
    }

    @Test
    public void test_onViewPause() throws Exception {
        presenter.onViewPause(view);
        assertNull(presenter.getView());
    }
}
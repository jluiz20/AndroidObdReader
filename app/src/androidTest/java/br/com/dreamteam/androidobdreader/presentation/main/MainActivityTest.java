package br.com.dreamteam.androidobdreader.presentation.main;

import android.Manifest;
import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import javax.inject.Inject;

import br.com.dreamteam.androidobdreader.MockObdReaderApplication;
import br.com.dreamteam.androidobdreader.R;
import br.com.dreamteam.androidobdreader.di.TestComponent;
import br.com.dreamteam.androidobdreader.presentation.connection.ConnectionStepsActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link MainActivity}
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public IntentsTestRule<MainActivity> activityRule = new IntentsTestRule<>(
            MainActivity.class,
            true,     // initialTouchMode
            true); // launchActivity. False so we can customize the intent per test method

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule//Grant the needed permissions
    public GrantPermissionRule mRuntimePermissionRule = GrantPermissionRule.grant(Manifest.permission.ACCESS_COARSE_LOCATION);


    @Inject
    MainContract.Presenter presenter;

    private ArgumentCaptor<MainContract.View> viewArgumentCaptor;

    @Before
    public void setUp() {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        MockObdReaderApplication app = (MockObdReaderApplication) instrumentation.getTargetContext().getApplicationContext();
        TestComponent component = (TestComponent) app.component();
        component.inject(this);
    }

    @Test
    public void test_AppVersion() {
        activityRule.getActivity().runOnUiThread(() -> {
            viewArgumentCaptor = ArgumentCaptor.forClass(MainContract.View.class);
            verify(presenter).onViewResume(viewArgumentCaptor.capture());
        });
        activityRule.getActivity().runOnUiThread(() -> viewArgumentCaptor.getValue().showAppVersion("1.5.4"));

        onView(withId(R.id.app_version))
                .check(matches(withText("1.5.4")));

        clearInvocations(presenter);
    }

    @Test
    public void test_connectButton() {
        activityRule.getActivity().runOnUiThread(() -> {
            viewArgumentCaptor = ArgumentCaptor.forClass(MainContract.View.class);
            verify(presenter).onViewResume(viewArgumentCaptor.capture());
        });

        onView(withId(R.id.button_connect)).check(matches(isDisplayed())).perform(click());

        intended(hasComponent(ConnectionStepsActivity.class.getName()));

        clearInvocations(presenter);
    }


    @Test
    public void test_bluetoothSupported() {
        activityRule.getActivity().runOnUiThread(() -> {
            viewArgumentCaptor = ArgumentCaptor.forClass(MainContract.View.class);
            verify(presenter).onViewResume(viewArgumentCaptor.capture());
        });

        onView(withId(R.id.button_connect)).check(matches(isEnabled()));

        clearInvocations(presenter);
    }

    @Test
    public void test_bluetoothNotSupported() {
        activityRule.getActivity().runOnUiThread(() -> {
            viewArgumentCaptor = ArgumentCaptor.forClass(MainContract.View.class);
            verify(presenter).onViewResume(viewArgumentCaptor.capture());
        });
        activityRule.getActivity().runOnUiThread(() -> viewArgumentCaptor.getValue().showBluetoothIsNotSupported());
        onView(withText(R.string.alert_bluetooth_not_supported_title)).check(matches(isDisplayed()));
        onView(withText(android.R.string.ok)).perform(click());

        onView(withId(R.id.button_connect)).check(matches(not(isEnabled())));
        clearInvocations(presenter);
    }


}

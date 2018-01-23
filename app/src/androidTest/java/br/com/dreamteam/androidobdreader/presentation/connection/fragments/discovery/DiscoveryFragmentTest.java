package br.com.dreamteam.androidobdreader.presentation.connection.fragments.discovery;

import android.app.Instrumentation;
import android.graphics.drawable.Drawable;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.content.ContextCompat;
import android.widget.ProgressBar;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import br.com.dreamteam.androidobdreader.MockObdReaderApplication;
import br.com.dreamteam.androidobdreader.R;
import br.com.dreamteam.androidobdreader.di.TestComponent;
import br.com.dreamteam.androidobdreader.model.BluetoothDeviceWrapper;
import br.com.dreamteam.androidobdreader.model.datasource.obd.states.ConnectionState;
import br.com.dreamteam.androidobdreader.presentation.connection.ConnectionStepsActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static br.com.dreamteam.androidobdreader.stepper.CommonStepperMatchers.checkCurrentStepIs;
import static com.stepstone.stepper.test.StepperNavigationActions.clickBack;
import static com.stepstone.stepper.test.StepperNavigationActions.clickNext;
import static com.stepstone.stepper.test.StepperNavigationActions.clickTabAtPosition;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.verify;


/**
 * Tests for {@link DiscoveryFragment}
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */
@RunWith(AndroidJUnit4.class)
public class DiscoveryFragmentTest {

    @Rule
    public IntentsTestRule<ConnectionStepsActivity> activityRule = new IntentsTestRule<>(
            ConnectionStepsActivity.class,
            true,     // initialTouchMode
            true); // launchActivity. False so we can customize the intent per test method

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();


    @Inject
    DiscoveryContract.Presenter presenter;

    private ArgumentCaptor<DiscoveryContract.View> viewArgumentCaptor;

    @Mock
    private BluetoothDeviceWrapper bluetoothDevicePaired;

    @Mock
    private BluetoothDeviceWrapper bluetoothDeviceDiscovered;


    @Before
    public void setUp() {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        MockObdReaderApplication app = (MockObdReaderApplication) instrumentation.getTargetContext().getApplicationContext();
        TestComponent component = (TestComponent) app.component();
        component.inject(this);
    }


    @Test
    public void test_step03_onBack() {
        //given
        onView(withId(R.id.stepperLayout)).perform(clickNext());
        viewArgumentCaptor = ArgumentCaptor.forClass(DiscoveryContract.View.class);
        verify(presenter, atLeastOnce()).onViewResume(viewArgumentCaptor.capture());
        onView(withId(R.id.stepperLayout)).perform(clickTabAtPosition(2));
        //when
        checkCurrentStepIs(2);
        onView(withId(R.id.stepperLayout)).perform(clickBack());
        //then
        checkCurrentStepIs(1);

        clearInvocations(presenter);
    }

    @Test
    public void test_step03_checkViews() {
        //given
        onView(withId(R.id.stepperLayout)).perform(clickNext());
        viewArgumentCaptor = ArgumentCaptor.forClass(DiscoveryContract.View.class);
        verify(presenter, atLeastOnce()).onViewResume(viewArgumentCaptor.capture());


        //when
        onView(withId(R.id.stepperLayout)).perform(clickNext());
        onView(withId(R.id.stepperLayout)).perform(clickNext());

        //then
        checkCurrentStepIs(2);
        onView(withId(R.id.stepperLayout)).check(matches(isDisplayed()));
        onView(allOf(withText(R.string.connection_step_discovery_title), withId(R.id.step_title))).check(matches(isDisplayed()));
        onView(allOf(withText(R.string.connection_step_discovery_explanation), withId(R.id.step_subtitle))).check(matches(isDisplayed()));
        onView(withId(R.id.step_btn_retry)).check(matches(not(isDisplayed())));
        onView(withId(R.id.text_view_paired_devices)).check(matches(isDisplayed()));
        onView(withId(R.id.text_view_discovered)).check(matches(isDisplayed()));

        clearInvocations(presenter);
    }

    @Test
    public void test_step03_checkViews_in_progress() {
        //given
        onView(withId(R.id.stepperLayout)).perform(clickNext());
        viewArgumentCaptor = ArgumentCaptor.forClass(DiscoveryContract.View.class);
        verify(presenter, atLeastOnce()).onViewResume(viewArgumentCaptor.capture());

        //when
        onView(withId(R.id.stepperLayout)).perform(clickNext());
        onView(withId(R.id.stepperLayout)).perform(clickNext());

        //set a drawable for progress bar to avoid stay stuck on it
        Drawable notAnimatedDrawable = ContextCompat.getDrawable(activityRule.getActivity(), R.drawable.ic_check_circle_24dp);
        ((ProgressBar) activityRule.getActivity().findViewById(R.id.step_progress)).setIndeterminateDrawable(notAnimatedDrawable);

        activityRule.getActivity().runOnUiThread(() -> viewArgumentCaptor.getValue().updateConnectionState(ConnectionState.DISCOVERY_STARTED));

        //then
        checkCurrentStepIs(2);
        onView(withId(R.id.stepperLayout)).check(matches(isDisplayed()));
        onView(allOf(withText(R.string.connection_step_discovery_title), withId(R.id.step_title))).check(matches(isDisplayed()));
        onView(allOf(withText(R.string.connection_step_discovery_explanation), withId(R.id.step_subtitle))).check(matches(isDisplayed()));
        onView(withId(R.id.step_btn_retry)).check(matches(not(isDisplayed())));
        onView(withId(R.id.text_view_paired_devices)).check(matches(isDisplayed()));
        onView(withId(R.id.text_view_discovered)).check(matches(isDisplayed()));
        onView(withId(R.id.step_progress)).check(matches(isDisplayed()));

        clearInvocations(presenter);
    }

    @Test
    public void test_step03_checkViews_discovery_finished() {
        //given
        onView(withId(R.id.stepperLayout)).perform(clickNext());
        activityRule.getActivity().runOnUiThread(() -> {
            viewArgumentCaptor = ArgumentCaptor.forClass(DiscoveryContract.View.class);
            verify(presenter, atLeastOnce()).onViewResume(viewArgumentCaptor.capture());
        });

        Set<BluetoothDeviceWrapper> bluetoothDevicesPaired = new HashSet<>();
        Set<BluetoothDeviceWrapper> bluetoothDevicesDiscovered = new HashSet<>();

        bluetoothDevicesPaired.add(bluetoothDevicePaired);
        bluetoothDevicesDiscovered.add(bluetoothDeviceDiscovered);

        //when
        onView(withId(R.id.stepperLayout)).perform(clickNext());
        onView(withId(R.id.stepperLayout)).perform(clickNext());

        //set a drawable for progress bar to avoid stay stuck on it
        Drawable notAnimatedDrawable = ContextCompat.getDrawable(activityRule.getActivity(), R.drawable.ic_check_circle_24dp);
        ((ProgressBar) activityRule.getActivity().findViewById(R.id.step_progress)).setIndeterminateDrawable(notAnimatedDrawable);

        activityRule.getActivity().runOnUiThread(() -> viewArgumentCaptor.getValue().showDiscoveredDevices(bluetoothDevicesDiscovered));
        activityRule.getActivity().runOnUiThread(() -> viewArgumentCaptor.getValue().showPairedDevices(bluetoothDevicesPaired));
        activityRule.getActivity().runOnUiThread(() -> viewArgumentCaptor.getValue().updateConnectionState(ConnectionState.DISCOVERY_FINISHED));

        //then
        checkCurrentStepIs(2);
        onView(withId(R.id.stepperLayout)).check(matches(isDisplayed()));
        onView(allOf(withText(R.string.connection_step_discovery_title), withId(R.id.step_title))).check(matches(isDisplayed()));
        onView(allOf(withText(R.string.connection_step_discovery_explanation), withId(R.id.step_subtitle))).check(matches(isDisplayed()));
        onView(withId(R.id.step_btn_retry)).check(matches(isDisplayed()));
        onView(withId(R.id.text_view_paired_devices)).check(matches(isDisplayed()));
        onView(withId(R.id.text_view_discovered)).check(matches(isDisplayed()));
        onView(withId(R.id.step_progress)).check(matches(not(isDisplayed())));
        onView(withId(R.id.recycler_paired_devices)).check(matches(isDisplayed()));
        onView(withId(R.id.recycler_discovered)).check(matches(isDisplayed()));


        clearInvocations(presenter);
    }

    @Test
    public void test_step03_checkViews_selected_device_paired() {
        //given
        onView(withId(R.id.stepperLayout)).perform(clickNext());
        activityRule.getActivity().runOnUiThread(() -> {
            viewArgumentCaptor = ArgumentCaptor.forClass(DiscoveryContract.View.class);
            verify(presenter, atLeastOnce()).onViewResume(viewArgumentCaptor.capture());
        });

        Set<BluetoothDeviceWrapper> bluetoothDevicesPaired = new HashSet<>();

        bluetoothDevicesPaired.add(bluetoothDevicePaired);

        //when
        onView(withId(R.id.stepperLayout)).perform(clickNext());
        onView(withId(R.id.stepperLayout)).perform(clickNext());

        //set a drawable for progress bar to avoid stay stuck on it
        Drawable notAnimatedDrawable = ContextCompat.getDrawable(activityRule.getActivity(), R.drawable.ic_check_circle_24dp);
        ((ProgressBar) activityRule.getActivity().findViewById(R.id.step_progress)).setIndeterminateDrawable(notAnimatedDrawable);

        activityRule.getActivity().runOnUiThread(() -> viewArgumentCaptor.getValue().showPairedDevices(bluetoothDevicesPaired));
        activityRule.getActivity().runOnUiThread(() -> viewArgumentCaptor.getValue().updateConnectionState(ConnectionState.DISCOVERY_FINISHED));

        //then
        checkCurrentStepIs(2);
        onView(withId(R.id.stepperLayout)).check(matches(isDisplayed()));
        onView(allOf(withText(R.string.connection_step_discovery_title), withId(R.id.step_title))).check(matches(isDisplayed()));
        onView(allOf(withText(R.string.connection_step_discovery_explanation), withId(R.id.step_subtitle))).check(matches(isDisplayed()));
        onView(withId(R.id.step_btn_retry)).check(matches(isDisplayed()));
        onView(withId(R.id.text_view_paired_devices)).check(matches(isDisplayed()));
        onView(withId(R.id.text_view_discovered)).check(matches(isDisplayed()));
        onView(withId(R.id.step_progress)).check(matches(not(isDisplayed())));
        onView(withId(R.id.recycler_paired_devices)).check(matches(isDisplayed()));

        //perform recycler click paired
        onView(withId(R.id.recycler_paired_devices))
                .perform(actionOnItemAtPosition(0, click()));

        verify(presenter).onUserSelectedDeviceToConnect(bluetoothDevicePaired);

        activityRule.getActivity().runOnUiThread(() -> viewArgumentCaptor.getValue().showConnectionInProgress());


        clearInvocations(presenter);
    }

    @Test
    public void test_step03_checkViews_selected_device_discovered() {
        //given
        onView(withId(R.id.stepperLayout)).perform(clickNext());
        activityRule.getActivity().runOnUiThread(() -> {
            viewArgumentCaptor = ArgumentCaptor.forClass(DiscoveryContract.View.class);
            verify(presenter, atLeastOnce()).onViewResume(viewArgumentCaptor.capture());
        });

        Set<BluetoothDeviceWrapper> bluetoothDevicesDiscovered = new HashSet<>();

        bluetoothDevicesDiscovered.add(bluetoothDeviceDiscovered);

        //when
        onView(withId(R.id.stepperLayout)).perform(clickNext());
        onView(withId(R.id.stepperLayout)).perform(clickNext());

        //set a drawable for progress bar to avoid stay stuck on it
        Drawable notAnimatedDrawable = ContextCompat.getDrawable(activityRule.getActivity(), R.drawable.ic_check_circle_24dp);
        ((ProgressBar) activityRule.getActivity().findViewById(R.id.step_progress)).setIndeterminateDrawable(notAnimatedDrawable);

        activityRule.getActivity().runOnUiThread(() -> viewArgumentCaptor.getValue().showDiscoveredDevices(bluetoothDevicesDiscovered));
        activityRule.getActivity().runOnUiThread(() -> viewArgumentCaptor.getValue().updateConnectionState(ConnectionState.DISCOVERY_FINISHED));

        //then
        checkCurrentStepIs(2);
        onView(withId(R.id.stepperLayout)).check(matches(isDisplayed()));
        onView(allOf(withText(R.string.connection_step_discovery_title), withId(R.id.step_title))).check(matches(isDisplayed()));
        onView(allOf(withText(R.string.connection_step_discovery_explanation), withId(R.id.step_subtitle))).check(matches(isDisplayed()));
        onView(withId(R.id.step_btn_retry)).check(matches(isDisplayed()));
        onView(withId(R.id.text_view_paired_devices)).check(matches(isDisplayed()));
        onView(withId(R.id.text_view_discovered)).check(matches(isDisplayed()));
        onView(withId(R.id.step_progress)).check(matches(not(isDisplayed())));
        onView(withId(R.id.recycler_discovered)).check(matches(isDisplayed()));

        //perform recycler click paired
        onView(withId(R.id.recycler_discovered))
                .perform(actionOnItemAtPosition(0, click()));

        verify(presenter).onUserSelectedDeviceToConnect(bluetoothDeviceDiscovered);

        activityRule.getActivity().runOnUiThread(() -> viewArgumentCaptor.getValue().showConnectionInProgress());


        clearInvocations(presenter);
    }
}
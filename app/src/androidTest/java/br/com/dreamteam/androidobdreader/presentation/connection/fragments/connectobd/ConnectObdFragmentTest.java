package br.com.dreamteam.androidobdreader.presentation.connection.fragments.connectobd;

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
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static br.com.dreamteam.androidobdreader.stepper.CommonStepperMatchers.checkCurrentStepIs;
import static com.stepstone.stepper.test.StepperNavigationActions.clickBack;
import static com.stepstone.stepper.test.StepperNavigationActions.clickNext;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link ConnectObdFragment}
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */
@RunWith(AndroidJUnit4.class)
public class ConnectObdFragmentTest {

    @Rule
    public IntentsTestRule<ConnectionStepsActivity> activityRule = new IntentsTestRule<>(
            ConnectionStepsActivity.class,
            true,     // initialTouchMode
            true); // launchActivity. False so we can customize the intent per test method

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule//Grant the needed permissions
    public GrantPermissionRule mRuntimePermissionRule = GrantPermissionRule.grant(Manifest.permission.ACCESS_COARSE_LOCATION);


    @Inject
    ConnectObdContract.Presenter presenter;

    private ArgumentCaptor<ConnectObdContract.View> viewArgumentCaptor;


    @Before
    public void setUp() {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        MockObdReaderApplication app = (MockObdReaderApplication) instrumentation.getTargetContext().getApplicationContext();
        TestComponent component = (TestComponent) app.component();
        component.inject(this);
    }

    @Test
    public void test_step01_onBack() {
        //given
            viewArgumentCaptor = ArgumentCaptor.forClass(ConnectObdContract.View.class);
        verify(presenter, atLeastOnce()).onViewResume(viewArgumentCaptor.capture());
        //when

        checkCurrentStepIs(0);

        onView(withId(R.id.stepperLayout)).perform(clickBack());
        //then
        assertTrue(activityRule.getActivity().isFinishing());

        clearInvocations(presenter);
    }

    @Test
    public void test_step01_checkViews() {
        //given
            viewArgumentCaptor = ArgumentCaptor.forClass(ConnectObdContract.View.class);
        verify(presenter, atLeastOnce()).onViewResume(viewArgumentCaptor.capture());

        //when


        //then
        checkCurrentStepIs(0);
        onView(withId(R.id.stepperLayout)).check(matches(isDisplayed()));
        onView(allOf(withText(R.string.connection_step_connect_obd_title), withId(R.id.step_title))).check(matches(isDisplayed()));
        onView(allOf(withText(R.string.connection_step_connect_obd_explanation), withId(R.id.step_subtitle))).check(matches(isDisplayed()));
        onView(allOf(withText(R.string.connection_step_connect_obd_click_next), withId(R.id.step_msg_next))).check(matches(isDisplayed()));

        clearInvocations(presenter);
    }

    @Test
    public void test_step01_onNext() {
        //given
            viewArgumentCaptor = ArgumentCaptor.forClass(ConnectObdContract.View.class);
        verify(presenter, atLeastOnce()).onViewResume(viewArgumentCaptor.capture());
        //when
        onView(withId(R.id.stepperLayout)).perform(clickNext());

        //then
        checkCurrentStepIs(1);
        clearInvocations(presenter);
    }
}
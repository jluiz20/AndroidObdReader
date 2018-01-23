package br.com.dreamteam.androidobdreader.presentation.connection.fragments.connecting;

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
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import javax.inject.Inject;

import br.com.dreamteam.androidobdreader.MockObdReaderApplication;
import br.com.dreamteam.androidobdreader.R;
import br.com.dreamteam.androidobdreader.di.TestComponent;
import br.com.dreamteam.androidobdreader.presentation.connection.ConnectionStepsActivity;
import br.com.dreamteam.androidobdreader.presentation.connection.fragments.discovery.DiscoveryContract;
import br.com.dreamteam.androidobdreader.presentation.connection.fragments.discovery.DiscoveryFragment;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static br.com.dreamteam.androidobdreader.MatcherUtils.withDrawable;
import static br.com.dreamteam.androidobdreader.stepper.CommonStepperMatchers.checkCurrentStepIs;
import static com.stepstone.stepper.test.StepperNavigationActions.clickBack;
import static com.stepstone.stepper.test.StepperNavigationActions.clickComplete;
import static com.stepstone.stepper.test.StepperNavigationActions.clickNext;
import static com.stepstone.stepper.test.StepperNavigationActions.clickTabAtPosition;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link DiscoveryFragment}
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */
@RunWith(AndroidJUnit4.class)
public class ConnectingFragmentTest {

    @Rule
    public IntentsTestRule<ConnectionStepsActivity> activityRule = new IntentsTestRule<>(
            ConnectionStepsActivity.class,
            true,     // initialTouchMode
            true); // launchActivity. False so we can customize the intent per test method

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Inject
    DiscoveryContract.Presenter discoveryPresenter;

    @Inject
    ConnectingContract.Presenter presenter;

    private ArgumentCaptor<ConnectingContract.View> viewArgumentCaptor;


    @Before
    public void setUp() {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        MockObdReaderApplication app = (MockObdReaderApplication) instrumentation.getTargetContext().getApplicationContext();
        TestComponent component = (TestComponent) app.component();
        component.inject(this);
    }


    @Test
    public void test_step04_onBack() {
        //given

        //set a drawable for progress bar to avoid stay stuck on it
        Drawable notAnimatedDrawable = ContextCompat.getDrawable(activityRule.getActivity(), R.drawable.ic_check_circle_24dp);
        ((ProgressBar) activityRule.getActivity().findViewById(R.id.step_connecting_progress)).setIndeterminateDrawable(notAnimatedDrawable);

        onView(withId(R.id.stepperLayout)).perform(clickNext());
        onView(withId(R.id.stepperLayout)).perform(clickNext());

        activityRule.getActivity().runOnUiThread(() -> {
            ArgumentCaptor<DiscoveryContract.View> discoveryViewArgumentCaptor = ArgumentCaptor.forClass(DiscoveryContract.View.class);
            verify(discoveryPresenter, atLeastOnce()).onViewResume(discoveryViewArgumentCaptor.capture());
            activityRule.getActivity().runOnUiThread(() -> discoveryViewArgumentCaptor.getValue().showConnectionInProgress());
        });

        viewArgumentCaptor = ArgumentCaptor.forClass(ConnectingContract.View.class);
        verify(presenter, atLeastOnce()).onViewResume(viewArgumentCaptor.capture());

        onView(withId(R.id.stepperLayout)).perform(clickTabAtPosition(3));
        //when
        checkCurrentStepIs(3);
        onView(withId(R.id.stepperLayout)).perform(clickBack());
        //then
        checkCurrentStepIs(2);

        clearInvocations(presenter);
    }


    @Test
    public void test_step04_check_connecting() {
        //given

        //set a drawable for progress bar to avoid stay stuck on it
        Drawable notAnimatedDrawable = ContextCompat.getDrawable(activityRule.getActivity(), R.drawable.ic_check_circle_24dp);
        ((ProgressBar) activityRule.getActivity().findViewById(R.id.step_connecting_progress)).setIndeterminateDrawable(notAnimatedDrawable);

        onView(withId(R.id.stepperLayout)).perform(clickNext());
        onView(withId(R.id.stepperLayout)).perform(clickNext());

        activityRule.getActivity().runOnUiThread(() -> {
            ArgumentCaptor<DiscoveryContract.View> discoveryViewArgumentCaptor = ArgumentCaptor.forClass(DiscoveryContract.View.class);
            verify(discoveryPresenter, atLeastOnce()).onViewResume(discoveryViewArgumentCaptor.capture());
            activityRule.getActivity().runOnUiThread(() -> discoveryViewArgumentCaptor.getValue().showConnectionInProgress());
        });

        viewArgumentCaptor = ArgumentCaptor.forClass(ConnectingContract.View.class);
        verify(presenter, atLeastOnce()).onViewResume(viewArgumentCaptor.capture());

        onView(withId(R.id.stepperLayout)).perform(clickTabAtPosition(3));

        //when


        //then
        onView(withId(R.id.stepperLayout)).check(matches(isDisplayed()));
        onView(allOf(withText(R.string.connection_step_connecting_title), withId(R.id.step_title))).check(matches(isDisplayed()));
        onView(allOf(withText(R.string.connection_step_connecting_explanation), withId(R.id.step_subtitle))).check(matches(isDisplayed()));

        onView(withId(R.id.step_connecting_progress)).check(matches(isDisplayed()));
        onView(withId(R.id.step_result_icon)).check(matches(not(isDisplayed())));


        clearInvocations(presenter);
    }

    @Test
    public void test_step04_check_success() {
        //given

        //set a drawable for progress bar to avoid stay stuck on it
        Drawable notAnimatedDrawable = ContextCompat.getDrawable(activityRule.getActivity(), R.drawable.ic_check_circle_24dp);
        ((ProgressBar) activityRule.getActivity().findViewById(R.id.step_connecting_progress)).setIndeterminateDrawable(notAnimatedDrawable);

        onView(withId(R.id.stepperLayout)).perform(clickNext());
        onView(withId(R.id.stepperLayout)).perform(clickNext());

        activityRule.getActivity().runOnUiThread(() -> {
            ArgumentCaptor<DiscoveryContract.View> discoveryViewArgumentCaptor = ArgumentCaptor.forClass(DiscoveryContract.View.class);
            verify(discoveryPresenter, atLeastOnce()).onViewResume(discoveryViewArgumentCaptor.capture());
            activityRule.getActivity().runOnUiThread(() -> discoveryViewArgumentCaptor.getValue().showConnectionInProgress());
        });

        viewArgumentCaptor = ArgumentCaptor.forClass(ConnectingContract.View.class);
        verify(presenter, atLeastOnce()).onViewResume(viewArgumentCaptor.capture());

        onView(withId(R.id.stepperLayout)).perform(clickTabAtPosition(3));

        //when
        activityRule.getActivity().runOnUiThread(() -> viewArgumentCaptor.getValue().showConnectionSuccess());

        //then
        onView(withId(R.id.stepperLayout)).check(matches(isDisplayed()));
        onView(allOf(withText(R.string.connection_step_connecting_title_success), withId(R.id.step_title))).check(matches(isDisplayed()));
        onView(allOf(withText(R.string.connection_step_connecting_explanation_success), withId(R.id.step_subtitle))).check(matches(isDisplayed()));

        onView(withId(R.id.step_connecting_progress)).check(matches(not(isDisplayed())));
        onView(withId(R.id.step_result_icon)).check(matches(isDisplayed()));
        onView(withId(R.id.step_result_icon)).check(matches(withDrawable(R.drawable.ic_check_circle_24dp)));

        onView(withId(R.id.stepperLayout)).perform(clickComplete());

        assertTrue(activityRule.getActivity().isFinishing());

        clearInvocations(presenter);
    }

    @Test
    public void test_step04_check_failed() {
        //given

        //set a drawable for progress bar to avoid stay stuck on it
        Drawable notAnimatedDrawable = ContextCompat.getDrawable(activityRule.getActivity(), R.drawable.ic_check_circle_24dp);
        ((ProgressBar) activityRule.getActivity().findViewById(R.id.step_connecting_progress)).setIndeterminateDrawable(notAnimatedDrawable);

        onView(withId(R.id.stepperLayout)).perform(clickNext());
        onView(withId(R.id.stepperLayout)).perform(clickNext());

        activityRule.getActivity().runOnUiThread(() -> {
            ArgumentCaptor<DiscoveryContract.View> discoveryViewArgumentCaptor = ArgumentCaptor.forClass(DiscoveryContract.View.class);
            verify(discoveryPresenter, atLeastOnce()).onViewResume(discoveryViewArgumentCaptor.capture());
            activityRule.getActivity().runOnUiThread(() -> discoveryViewArgumentCaptor.getValue().showConnectionInProgress());
        });

        viewArgumentCaptor = ArgumentCaptor.forClass(ConnectingContract.View.class);
        verify(presenter, atLeastOnce()).onViewResume(viewArgumentCaptor.capture());

        onView(withId(R.id.stepperLayout)).perform(clickTabAtPosition(3));

        //when
        activityRule.getActivity().runOnUiThread(() -> viewArgumentCaptor.getValue().showConnectionFailed());

        //then
        onView(withId(R.id.stepperLayout)).check(matches(isDisplayed()));
        onView(allOf(withText(R.string.connection_step_connecting_title_failed), withId(R.id.step_title))).check(matches(isDisplayed()));
        onView(allOf(withText(R.string.connection_step_connecting_explanation_failed), withId(R.id.step_subtitle))).check(matches(isDisplayed()));

        onView(withId(R.id.step_connecting_progress)).check(matches(not(isDisplayed())));
        onView(withId(R.id.step_result_icon)).check(matches(isDisplayed()));
        onView(withId(R.id.step_result_icon)).check(matches(withDrawable(R.drawable.ic_error_24dp)));

        onView(withId(R.id.stepperLayout)).perform(clickComplete());

        checkCurrentStepIs(3);

        clearInvocations(presenter);
    }
}
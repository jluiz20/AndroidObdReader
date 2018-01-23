package br.com.dreamteam.androidobdreader.stepper;

import android.support.annotation.ColorRes;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.view.View;

import org.hamcrest.Matcher;

import br.com.dreamteam.androidobdreader.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasTextColor;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static br.com.dreamteam.androidobdreader.stepper.ViewPagerPositionMatcher.hasPagePosition;
import static com.stepstone.stepper.test.StepperLayoutTabSubtitleMatcher.tabAtPositionHasSubtitle;

/**
 * Contains commonly used matchers.
 *
 * @author Piotr Zawadzki
 */
public final class CommonStepperMatchers {

    private CommonStepperMatchers() {
    }


    public static void checkTabSubtitle(@IntRange(from = 0) int position, @NonNull Matcher<View> subtitleMatcher) {
        onView(withId(R.id.stepperLayout)).check(matches(tabAtPositionHasSubtitle(position, subtitleMatcher)));
    }

    public static void checkCurrentStepIs(@IntRange(from = 0) int expectedCurrentStep) {
        onView(withId(R.id.ms_stepPager)).check(matches(hasPagePosition(expectedCurrentStep)));
    }

    public static void checkCompleteButtonShown() {
        onView(withId(R.id.ms_stepCompleteButton)).check(matches(isDisplayed()));
    }

    public static void checkBackButtonColor(@ColorRes int expectedColor) {
        onView(withId(com.stepstone.stepper.R.id.ms_stepPrevButton)).check(matches(hasTextColor(expectedColor)));
    }

    public static void checkNextButtonColor(@ColorRes int expectedColor) {
        onView(withId(com.stepstone.stepper.R.id.ms_stepNextButton)).check(matches(hasTextColor(expectedColor)));
    }

    public static void checkCompleteButtonColor(@ColorRes int expectedColor) {
        onView(withId(com.stepstone.stepper.R.id.ms_stepCompleteButton)).check(matches(hasTextColor(expectedColor)));
    }

}

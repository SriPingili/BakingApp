package com.example.android.bakingapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

/**
 * UI test for StepsDetailActivity
 *
 * Used the Udacity classroom videos on UI testing as reference
 */
@RunWith(AndroidJUnit4.class)
public class StepsDetailActivityUITest {

    @Rule
    public IntentsTestRule<MainActivity> mActivityRule = new IntentsTestRule<>(MainActivity.class);

    @Before
    public void stubAllExternalIntents() {
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }


    /**
     * tests if the previous button is hidden and next button is
     * displayed for the first detailed description of the steps
     * in a fragment
     */
    @Test
    public void testFirstElementInDetailedStepDescriptionFragment() {
        onView(ViewMatchers.withId(R.id.recipe_names_recycler_view_id))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(ViewMatchers.withId(R.id.steps_recycler_view_id))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.step_detailed_description_text_id)).check(matches(isDisplayed()));
        onView(withId(R.id.next_button_id)).check(matches(isDisplayed()));
        onView(withId(R.id.prev_button_id)).check(matches(not(isDisplayed())));
    }

    /**
     * tests if both the previous and next buttons are displayed for the
     * middle elements
     */
    @Test
    public void testMiddleElementInDetailedStepDescriptionFragment() {
        onView(ViewMatchers.withId(R.id.recipe_names_recycler_view_id))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(ViewMatchers.withId(R.id.steps_recycler_view_id))
                .perform(RecyclerViewActions.actionOnItemAtPosition(4, click()));
        onView(withId(R.id.step_detailed_description_text_id)).check(matches(isDisplayed()));
        onView(withId(R.id.next_button_id)).check(matches(isDisplayed()));
        onView(withId(R.id.prev_button_id)).check(matches(isDisplayed()));
    }

    /**
     * tests if next button is hidden and only the previous button is
     * displayed for the last step
     */
    @Test
    public void testLastElementInDetailedStepDescriptionFragment() {
        onView(ViewMatchers.withId(R.id.recipe_names_recycler_view_id))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(ViewMatchers.withId(R.id.steps_recycler_view_id))
                .perform(RecyclerViewActions.actionOnItemAtPosition(6, click()));
        onView(withId(R.id.step_detailed_description_text_id)).check(matches(isDisplayed()));
        onView(withId(R.id.next_button_id)).check(matches(not(isDisplayed())));
        onView(withId(R.id.prev_button_id)).check(matches(isDisplayed()));
    }
}

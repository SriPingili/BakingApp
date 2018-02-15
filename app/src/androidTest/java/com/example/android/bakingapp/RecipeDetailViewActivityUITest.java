package com.example.android.bakingapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
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
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.AllOf.allOf;

/**
 * UI test class for the RecipeDetailActivity class
 *
 * Used the Udacity classroom videos on UI testing as reference
 */
@RunWith(AndroidJUnit4.class)
public class RecipeDetailViewActivityUITest {

    @Rule
    public IntentsTestRule<MainActivity> mActivityRule = new IntentsTestRule<>(MainActivity.class);

    @Before
    public void stubAllExternalIntents() {
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }


    /**
     * tests clicking on a recipe name in MainActivity opens up a screen
     * which includes a textview with 'Click here for Recipe Ingredients'
     * text.
     */
    @Test
    public void testRecipeDetailsActivityShowsTextViewWithRecipeIngredientsLink() {

        onView(ViewMatchers.withId(R.id.recipe_names_recycler_view_id))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.ingredients_list_text_view_id)).check(matches(withText(R.string.click_here_for_recipe_ingredients)));
    }

    /**
     * tests clicking on a text view opens an Intent to IngredientsDetailActivity
     * class
     */
    @Test
    public void testClickingOnTheTextViewOpensIntent() {

        onView(ViewMatchers.withId(R.id.recipe_names_recycler_view_id))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(ViewMatchers.withId(R.id.ingredients_list_text_view_id))
                .perform(click());
        intended(allOf(hasComponent(IngredientsDetailActivity.class.getName()), hasExtra(Intent.EXTRA_TEXT, MainActivity.recipeData.get(1).getIngredientsList())));
    }


    /**
     * tests clicking on a recyclerview item opens an Intent to StepsDetailActivity
     */
    @Test
    public void testRecipeDetailsActivityClickOnItsRecyclerViewFiresAnIntent() {

        onView(ViewMatchers.withId(R.id.recipe_names_recycler_view_id))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(ViewMatchers.withId(R.id.steps_recycler_view_id))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        intended(allOf(hasComponent(StepsDetailActivity.class.getName()), hasExtra(Intent.EXTRA_TEXT, MainActivity.recipeData.get(1))));
    }
}
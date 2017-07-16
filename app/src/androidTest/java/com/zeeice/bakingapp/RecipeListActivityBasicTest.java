package com.zeeice.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import com.zeeice.bakingapp.ui.activity.RecipeDetailActivity;
import com.zeeice.bakingapp.ui.activity.RecipeListActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Oriaje on 29/06/2017.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeListActivityBasicTest {

    @Rule
    public IntentsTestRule<RecipeListActivity> mActivity =
            new IntentsTestRule<>(RecipeListActivity.class);

    @Test
    public void click_GetRecipeDetail()
    {
       Espresso.onView(ViewMatchers.withId(R.id.recipe_item_recycleview))
               .perform(RecyclerViewActions.actionOnItem(
                       ViewMatchers.hasDescendant(ViewMatchers.withText("Nutella Pie")), ViewActions.click()));

        Intents.intended(IntentMatchers.hasComponent(RecipeDetailActivity.class.getName()));
    }

    //check to see if recipes were loaded
    @Test
    public void isRecipesLoaded()
    {
       Espresso.onView(ViewMatchers.withId(R.id.recipe_item_recycleview))
               .check(new RecyclerViewNonZeroItemCountAssertion());
    }


}

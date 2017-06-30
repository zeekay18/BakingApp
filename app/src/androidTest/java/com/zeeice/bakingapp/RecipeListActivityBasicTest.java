package com.zeeice.bakingapp;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.zeeice.bakingapp.ui.activity.RecipeDetailActivity;
import com.zeeice.bakingapp.ui.activity.RecipeListActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;

import android.support.test.espresso.contrib.RecyclerViewActions;

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
       onView(withId(R.id.recipe_item_recycleview))
               .perform(RecyclerViewActions.actionOnItem(
                       hasDescendant(withText("Nutella Pie")),click()));

        intended(hasComponent(RecipeDetailActivity.class.getName()));
    }
}

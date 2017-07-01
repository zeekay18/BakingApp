package com.zeeice.bakingapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.zeeice.bakingapp.Data.Model.StepObject;
import com.zeeice.bakingapp.R;
import com.zeeice.bakingapp.ui.Adapter.StepItemRecyclerViewAdapter;
import com.zeeice.bakingapp.ui.fragment.PlayVideoFragment;
import com.zeeice.bakingapp.ui.fragment.RecipeDetailFragment;

/**
 * Created by Oriaje on 05/06/2017.
 */

public class RecipeDetailActivity extends AppCompatActivity
        implements RecipeDetailFragment.PlayVideoHandler {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if(savedInstanceState == null)
        {
            Bundle arguments = new Bundle();
            arguments.putParcelable(RecipeDetailFragment.RECIPE_ITEM,
                    getIntent().getParcelableExtra(RecipeDetailFragment.RECIPE_ITEM));

            RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
            recipeDetailFragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.recipe_item_detail_container, recipeDetailFragment)
                    .commit();

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home)
        {
            NavUtils.navigateUpTo(this,new Intent(this,RecipeListActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void stepItemClick(String url, String step) {
        Bundle arguments = new Bundle();
        arguments.putString(PlayVideoFragment.VIDEO_URL, url);
        arguments.putString(PlayVideoFragment.VIDEO_STEPS,step);

        PlayVideoFragment fragment = new PlayVideoFragment();
        fragment.setArguments(arguments);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipe_item_detail_container, fragment)
                .addToBackStack(fragment.getClass().getName())
                .commit();
    }
}

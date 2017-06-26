package com.zeeice.bakingapp.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zeeice.bakingapp.Data.Model.Recipe;
import com.zeeice.bakingapp.Data.Model.StepObject;
import com.zeeice.bakingapp.R;
import com.zeeice.bakingapp.Utilities.NetworkUtil;
import com.zeeice.bakingapp.ui.Adapter.RecipeRecyclerViewAdapter;
import com.zeeice.bakingapp.ui.Adapter.StepItemRecyclerViewAdapter;
import com.zeeice.bakingapp.ui.fragment.PlayVideoFragment;
import com.zeeice.bakingapp.ui.fragment.RecipeDetailFragment;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListActivity extends AppCompatActivity
implements LoaderManager.LoaderCallbacks<List<Recipe>>, RecipeRecyclerViewAdapter.RecipeItemClickHandler,
        RecipeDetailFragment.PlayVideoHandler{

    private boolean mTwoPane;

    @BindView(R.id.recipe_item_recycleview)
    RecyclerView recyclerView;
    RecipeRecyclerViewAdapter mRecipeAdapter;

    private static final int FORECAST_LOADER_ID = 0;

    ProgressBar progressDialog;

    List<Recipe> recipeData = null;

    private static final String RECIPE_LIST = "recipe_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        if(findViewById(R.id.recipe_item_detail_container) != null)
            mTwoPane = true;

        setUpRecyclerView();

        progressDialog = (ProgressBar)findViewById(R.id.loading_indicator);

        int loaderId = FORECAST_LOADER_ID;

        if(savedInstanceState != null)
        {
            recipeData = savedInstanceState.getParcelableArrayList(RECIPE_LIST);
            mRecipeAdapter.setRecipes(recipeData);
        }
        else
        getSupportLoaderManager().initLoader(loaderId,null,this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {

        outState.putParcelableArrayList(RECIPE_LIST,new ArrayList<>(recipeData));
    }

    @Override
    public void onClick(Recipe recipe) {

        if(mTwoPane)
        {
            Bundle arguments = new Bundle();
            arguments.putParcelable(RecipeDetailFragment.RECIPE_ITEM,recipe);
            RecipeDetailFragment fragment = new RecipeDetailFragment();

            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipe_item_detail_container,fragment)
                    .addToBackStack(fragment.getClass().getName())
                    .commit();
        }else {
            Intent intent = new Intent(this,RecipeDetailActivity.class);
            intent.putExtra(RecipeDetailFragment.RECIPE_ITEM,recipe);
            startActivity(intent);
        }
    }
    private void setUpRecyclerView()
    {
        recyclerView = (RecyclerView)findViewById(R.id.recipe_item_recycleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        mRecipeAdapter = new RecipeRecyclerViewAdapter(this,this);
        recyclerView.setAdapter(mRecipeAdapter);
    }

    @Override
    public Loader<List<Recipe>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<List<Recipe>>(this) {

            @Override
            protected void onStartLoading() {

                progressDialog.setVisibility(View.VISIBLE);

                if(recipeData != null)
                    deliverResult(recipeData);
                else
                {
                    forceLoad();
                }
            }

            @Override
            public List<Recipe> loadInBackground() {

                try {
                    String jsonNetworkRequest = NetworkUtil.makeNetworkRequest("http://go.udacity.com/android-baking-app-json");
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<Recipe>>(){}.getType();
                    List<Recipe> recipes = gson.fromJson(jsonNetworkRequest,type);

                    return recipes;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            public void deliverResult(List<Recipe> data) {
                recipeData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<Recipe>> loader, List<Recipe> data) {
        mRecipeAdapter.setRecipes(data);
        progressDialog.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<List<Recipe>> loader) {

        invalidateData();
    }
    private void invalidateData() {
        mRecipeAdapter.setRecipes(null);
    }


    @Override
    public void stepItemClick(String url, String step) {
       if(!mTwoPane)
           return;

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

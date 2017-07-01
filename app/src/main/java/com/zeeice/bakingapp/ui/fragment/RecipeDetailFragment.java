package com.zeeice.bakingapp.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zeeice.bakingapp.Data.Model.IngredientItem;
import com.zeeice.bakingapp.Data.Model.Recipe;
import com.zeeice.bakingapp.Data.Model.StepObject;
import com.zeeice.bakingapp.R;
import com.zeeice.bakingapp.ui.Adapter.StepItemRecyclerViewAdapter;

import java.io.InvalidClassException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Oriaje on 05/06/2017.
 */

public class RecipeDetailFragment extends Fragment
    implements StepItemRecyclerViewAdapter.StepItemClickHandler
{

    public static final String RECIPE_ITEM = "recipe_item";

    @BindView(R.id.instructionView)
    TextView instructionView;

    @BindView(R.id.steps_recyclerview)
    RecyclerView stepsRecyclerView;

    Unbinder unbinder;

    StepItemRecyclerViewAdapter adapter;

    Recipe recipe;
    public RecipeDetailFragment(){}

    public interface PlayVideoHandler{
        void stepItemClick(String url,String step);
    }
    PlayVideoHandler playVideoHandler;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            playVideoHandler = (PlayVideoHandler) context;
        }catch (ClassCastException ex)
        {
            throw new ClassCastException("Activity must implement PlayVideoHandler");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments().containsKey(RECIPE_ITEM))
        {
            recipe = getArguments().getParcelable(RECIPE_ITEM);

            getActivity().setTitle(recipe.getName());
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragemnt_recipe_detail,container,false);

      //  instructionView = (TextView)rootView.findViewById(R.id.instructionView);
       // stepsRecyclerView = (RecyclerView)rootView.findViewById(R.id.steps_recyclerview);

        unbinder = ButterKnife.bind(this,rootView);
        setUpRecyclerView();

        setInstructionView();
        adapter.swapData(recipe.getSteps());

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }

    private void setInstructionView()
    {
        StringBuilder builder = new StringBuilder();

        for(IngredientItem i : recipe.getIngredients())
        {
            builder.append(i.getIngredient() +" ("+i.getQuantity()+") "+i.getMeasure());
            builder.append("\n");
        }

        instructionView.setText(builder.toString());
    }
    private void setUpRecyclerView() {

        stepsRecyclerView.setHasFixedSize(true);
        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(
                getContext(),LinearLayoutManager.HORIZONTAL,false));

        adapter = new StepItemRecyclerViewAdapter(getContext(),this);
        stepsRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(int position) {
        StepObject clickedStep = recipe.getSteps().get(position);

        if(playVideoHandler != null)
          playVideoHandler.stepItemClick(clickedStep.getVideoURL(),clickedStep.getDescription());
    }
}

package com.zeeice.bakingapp.ui.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zeeice.bakingapp.Data.Model.Recipe;
import com.zeeice.bakingapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Oriaje on 05/06/2017.
 */

public class RecipeRecyclerViewAdapter extends RecyclerView.Adapter<RecipeRecyclerViewAdapter.RecipeViewHolder>{

    List<Recipe> mRecipes;
    Context mContext;

    public RecipeRecyclerViewAdapter(Context mContext, RecipeItemClickHandler itemClickHandler)
    {
        this.mContext = mContext;
        this.itemClickHandler = itemClickHandler;
    }

    public interface RecipeItemClickHandler
    {
        void onClick(Recipe recipe);
    }
    private RecipeItemClickHandler itemClickHandler;

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.recipe_item_layout,parent,false);

        return new RecipeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {

        Recipe recipe = mRecipes.get(position);

        holder.recipeName.setText(recipe.getName());
        holder.servings.setText(recipe.getServings());

        Picasso.with(mContext)
             //   .load(recipe.getImageUrl()) empty string returned from the url link
                .load(R.drawable.recipe_item)
                .placeholder(R.drawable.recipe_item)
                .error(R.drawable.recipe_item)
                .into(holder.recipeImage);
    }

    @Override
    public int getItemCount() {

        if(mRecipes == null)
            return 0;

        return mRecipes.size();
    }

    public void setRecipes(List<Recipe> mRecipes)
    {
        this.mRecipes = mRecipes;

        notifyDataSetChanged();
    }
    class RecipeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recipeImage)
        public ImageView recipeImage;

        @BindView(R.id.recipeName)
        public TextView recipeName;

        @BindView(R.id.recipeServings)
        public TextView servings;

        public RecipeViewHolder(View itemView) {
            super(itemView);

              ButterKnife.bind(this,itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickHandler.onClick(mRecipes.get(getAdapterPosition()));
                }
            });
        }
    }
}

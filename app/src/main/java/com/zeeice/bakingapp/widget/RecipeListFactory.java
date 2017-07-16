package com.zeeice.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zeeice.bakingapp.Data.Model.IngredientItem;
import com.zeeice.bakingapp.R;

import java.lang.reflect.Type;
import java.util.List;


/**
 * Created by Oriaje on 29/06/2017.
 */

public class RecipeListFactory implements RemoteViewsService.RemoteViewsFactory{


    private Cursor mCursor;
    private Context mContext;


    public RecipeListFactory(Context context, Intent intent){

        mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

        if(mCursor != null)
            mCursor.close();


    }

    @Override
    public void onDestroy() {

        if(mCursor != null)
            mCursor.close();
    }

    @Override
    public int getCount() {
        if(mCursor == null)
        return 0;

        return mCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        if(mCursor == null || !mCursor.moveToPosition(position))
            return null;

        RemoteViews row = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);

        IngredientItem ingredientItem = getIngredinent(position);
        row.setTextViewText(R.id.quantity_value,String.valueOf(ingredientItem.getQuantity()));
        row.setTextViewText(R.id.measure_value,ingredientItem.getMeasure());

        return row;
    }

    private IngredientItem getIngredinent(int position)
    {
        Gson gson = new Gson();
        Type type = new TypeToken<List<IngredientItem>>(){}.getType();
        List<IngredientItem> ingredients = gson.fromJson(mCursor.getString(2),type);

        return ingredients.get(position);
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}

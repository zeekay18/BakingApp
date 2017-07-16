package com.zeeice.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.zeeice.bakingapp.Data.Model.IngredientItem;
import com.zeeice.bakingapp.Data.Model.Recipe;
import com.zeeice.bakingapp.R;
import com.zeeice.bakingapp.ui.activity.RecipeListActivity;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidgetProvider extends AppWidgetProvider {


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                Recipe recipe,int appWidgetId) {


            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);

            rv.setTextViewText(R.id.recipe_name, recipe.getName());

            Intent showRecipeIntent = new Intent(context, RecipeListActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,0,showRecipeIntent,0);

            rv.setOnClickPendingIntent(R.id.recipe_name,pendingIntent );

            rv.removeAllViews(R.id.appwidget_ingredients);

            for( IngredientItem ingredientItem : recipe.getIngredients()) {

                RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                        R.layout.widget_list_item);
                remoteViews.setTextViewText(R.id.quantity_value,String.valueOf(ingredientItem.getQuantity()));
                remoteViews.setTextViewText(R.id.measure_value,ingredientItem.getMeasure());

                rv.addView(R.id.appwidget_ingredients,remoteViews);
            }

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, rv);
        }


    public static void updateRecipeData(Context context, AppWidgetManager appWidgetManager,
                                        Recipe recipe, int[] appWidgetIds)
    {
        for(int widgetId : appWidgetIds)
        {
        updateAppWidget(context, appWidgetManager, recipe, widgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}


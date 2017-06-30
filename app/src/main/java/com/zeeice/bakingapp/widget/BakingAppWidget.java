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
public class BakingAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                Recipe recipe,int[] appWidgetIds) {
      // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);

        StringBuilder ingredientbuilder = new StringBuilder();

        if(recipe != null) {
            for (IngredientItem i : recipe.getIngredients()) {
                ingredientbuilder.append(i.getIngredient() + " (" + i.getQuantity() + ") " + i.getMeasure())
                .append("\n");
                }
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {

            views.setTextViewText(R.id.appwidget_recipe_text, recipe.getName());
            views.setTextViewText(R.id.appwidget_ingredients,ingredientbuilder.toString());
            Intent intent = new Intent(context, RecipeListActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);

            views.setOnClickPendingIntent(R.id.appwidget_recipe_text,pendingIntent );

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }

        }
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

            updateAppWidget(context, appWidgetManager, null,appWidgetIds);

    }

    public static void updateRecipeData(Context context, AppWidgetManager appWidgetManager,
                                        Recipe recipe,int[] appWidgetIds)
    {
        updateAppWidget(context, appWidgetManager, recipe,appWidgetIds);
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


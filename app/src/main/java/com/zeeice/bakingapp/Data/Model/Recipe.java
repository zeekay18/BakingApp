package com.zeeice.bakingapp.Data.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oriaje on 05/06/2017.
 */

public class Recipe implements Parcelable {

    private int id;
    private String name;
    private List<IngredientItem> ingredients;
    private List<StepObject> steps;
    private String servings;
    private String imageUrl;

    public Recipe(Parcel in) {

        this.id = in.readInt();
        this.name = in.readString();
        this.imageUrl = in.readString();
        this.servings = in.readString();
        this.ingredients = new ArrayList<>();
        in.readList(ingredients,IngredientItem.class.getClassLoader());
        this.steps = new ArrayList<>();
        in.readList(steps,StepObject.class.getClassLoader());

    }


    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(imageUrl);
        dest.writeString(servings);
        dest.writeList(ingredients);
        dest.writeList(steps);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IngredientItem> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientItem> ingredients) {
        this.ingredients = ingredients;
    }

    public List<StepObject> getSteps() {
        return steps;
    }

    public void setSteps(List<StepObject> steps) {
        this.steps = steps;
    }

    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


}

package com.zeeice.bakingapp.Data.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Oriaje on 25/06/2017.
 */

public class IngredientItem implements Parcelable {

    private double quantity;
    private String measure;
    private String ingredient;

    protected IngredientItem(Parcel in) {
        setQuantity(in.readDouble());
        setMeasure(in.readString());
        setIngredient(in.readString());
    }

    public static final Creator<IngredientItem> CREATOR = new Creator<IngredientItem>() {
        @Override
        public IngredientItem createFromParcel(Parcel in) {
            return new IngredientItem(in);
        }

        @Override
        public IngredientItem[] newArray(int size) {
            return new IngredientItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(getQuantity());
        dest.writeString(getMeasure());
        dest.writeString(getIngredient());
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}

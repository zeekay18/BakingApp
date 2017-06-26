package com.zeeice.bakingapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zeeice.bakingapp.R;

/**
 * Created by Oriaje on 16/06/2017.
 */

public class IngredientsFragment extends Fragment {

    public IngredientsFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_ingredients,container,false);
    }
}

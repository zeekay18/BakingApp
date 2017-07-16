package com.zeeice.bakingapp;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Matcher;

import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * Created by Oriaje on 15/07/2017.
 */

public class RecyclerViewNonZeroItemCountAssertion implements ViewAssertion {

    private final Matcher<Integer> matcher;

    public RecyclerViewNonZeroItemCountAssertion()
    {
        this.matcher = is(not(0)) ;
    }


    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {

        if(noViewFoundException != null){
            throw  noViewFoundException;
        }

        RecyclerView recyclerView = (RecyclerView)view;
        RecyclerView.Adapter adapter = recyclerView.getAdapter();

        assertThat(adapter.getItemCount(),matcher);

    }
}

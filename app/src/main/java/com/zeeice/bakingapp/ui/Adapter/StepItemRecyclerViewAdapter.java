package com.zeeice.bakingapp.ui.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zeeice.bakingapp.Data.Model.StepObject;
import com.zeeice.bakingapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Oriaje on 24/06/2017.
 */

public class StepItemRecyclerViewAdapter extends RecyclerView.Adapter<StepItemRecyclerViewAdapter.StepItemViewHolder> {


    private Context mContext;
    List<StepObject> steps;

    public StepItemRecyclerViewAdapter(Context context, StepItemClickHandler listner)
    {
        mContext = context;
        this.itemClickHandler = listner;
    }
    public interface StepItemClickHandler
    {
        void onClick(int position);
    }
    private StepItemClickHandler itemClickHandler;


    @Override
    public StepItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.step_item_layout,parent,false);

        return new StepItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepItemViewHolder holder, int position) {

        StepObject step = steps.get(position);

        holder.short_desc_TextView.setText(step.getShortDescription());
        holder.desc_TextView.setText(step.getDescription());

        Picasso.with(mContext)
//                .load(step.getThumbnailURL()) empty string return from the URL
                .load(R.drawable.step_item)
                .placeholder(R.drawable.step_item)
                .error(R.drawable.step_item)
                .into(holder.thumb_imageView);
    }

    @Override
    public int getItemCount() {

        if(steps == null)
            return 0;

        return steps.size();
    }

    public void swapData(List<StepObject> steps)
    {
        this.steps = steps;
        notifyDataSetChanged();
    }

    class StepItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.step_thumbnail)public ImageView thumb_imageView;
        @BindView(R.id.short_desc)public TextView short_desc_TextView;
        @BindView(R.id.desc)public TextView desc_TextView;

        public StepItemViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickHandler.onClick(getAdapterPosition());
                }
            });
        }

    }
}

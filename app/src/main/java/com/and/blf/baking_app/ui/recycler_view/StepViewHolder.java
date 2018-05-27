package com.and.blf.baking_app.ui.recycler_view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.and.blf.baking_app.R;
import com.and.blf.baking_app.ui.StepClickListener;

class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView mStepDescription;
    ImageView mStepThumbnail;

    public StepViewHolder(View itemView) {
        super(itemView);
        mStepDescription = itemView.findViewById(R.id.step_description);
        mStepThumbnail   = itemView.findViewById(R.id.step_thumbnail_img);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ((StepClickListener)v.getContext()).onStepClicked(getAdapterPosition());
    }
}

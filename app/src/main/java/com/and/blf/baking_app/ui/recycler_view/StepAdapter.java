package com.and.blf.baking_app.ui.recycler_view;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.and.blf.baking_app.R;
import com.and.blf.baking_app.model.Step;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepViewHolder>{
    private List<Step> mStepList;

    public StepAdapter(ArrayList<Step> steps) {
        mStepList = steps;
    }

    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_item,parent,false);
        layoutView.setTag(mStepList);
        return new StepViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(StepViewHolder holder, int position) {
        holder.mStepDescription.setText(mStepList.get(position).getDescription());
        String imageURL = mStepList.get(position).getThumbnailURL();
        if(!(imageURL == null || imageURL.isEmpty())) {
            Picasso.with(holder.mStepThumbnail.getContext())
                    .load(Uri.parse(imageURL))
                    .placeholder(R.drawable.step_default_icon)
                    .error(R.drawable.step_default_icon)
                    .into(holder.mStepThumbnail);
        }
    }

    @Override
    public int getItemCount() {
        return mStepList.size();
    }
}

package com.phacsin.jobseeker.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.phacsin.jobseeker.R;
import com.phacsin.jobseeker.dataClasses.JobListItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class JobListAdapter extends RecyclerView.Adapter<JobListAdapter.ViewHolder> {
    private List<JobListItem> jobs;
    private Context context;
    private GridLayoutManager layoutManager;

    public JobListAdapter(Context context, List<JobListItem> jobs, GridLayoutManager layoutManager) {
        this.jobs = jobs;
        this.context = context;
        this.layoutManager = layoutManager;

    }

    @Override
    public JobListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if(layoutManager.getSpanCount()==2) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.job_list_card_grid, viewGroup, false);
            return new ViewHolder(view);
        }
        else
        {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.job_list_card_list, viewGroup, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(JobListAdapter.ViewHolder viewHolder, int i) {
        viewHolder.title.setText(jobs.get(i).getTitle());
        viewHolder.subtitle.setText(jobs.get(i).getSubtitle());
        Picasso.with(context).load(jobs.get(i).getUrl()).into(viewHolder.image);
    }

    @Override
    public int getItemCount() {
        return jobs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title,subtitle;
        private ImageView image;

        public ViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.title);
            subtitle = view.findViewById(R.id.subtitle);
            image = view.findViewById(R.id.imageView);
        }
    }

}
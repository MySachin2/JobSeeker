package com.phacsin.jobseeker.activities;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.support.annotation.NonNull;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.phacsin.jobseeker.R;
import com.phacsin.jobseeker.adapter.JobListAdapter;
import com.phacsin.jobseeker.dataClasses.JobListItem;

import java.util.ArrayList;
import java.util.List;

public class JobListingActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference mRef;
    List<JobListItem> jobs = new ArrayList<>();
    JobListAdapter jobListAdapter;
    ProgressBar progressBar;
    Toolbar toolbar;
    GridLayoutManager layoutManager;
    ImageView list_grid_icon,search_icon,location_icon,menu_icon;
    View top_bar;
    AppCompatEditText search_bar,location_bar;
    ImageView my_location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRef = FirebaseDatabase.getInstance().getReference();
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressbar);
        search_icon = findViewById(R.id.search_icon);
        location_icon = findViewById(R.id.location_icon);
        my_location = findViewById(R.id.get_my_location);
        menu_icon = findViewById(R.id.menu_icon);
        top_bar = findViewById(R.id.top_menu_bar);
        toolbar = findViewById(R.id.toolbar);
        search_bar = findViewById(R.id.search_bar);
        location_bar = findViewById(R.id.location_bar);

        toolbar.setTitle("JobSeeker");
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        jobListAdapter = new JobListAdapter(JobListingActivity.this,jobs,layoutManager);
        recyclerView.setAdapter(jobListAdapter);
        list_grid_icon = findViewById(R.id.list_grid_icon);
        mRef.child("Jobs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("DS",dataSnapshot.toString());
                for(DataSnapshot postDataSnapshot: dataSnapshot.getChildren())
                {
                    String image = postDataSnapshot.child("Image").getValue(String.class);
                    String title = postDataSnapshot.child("Title").getValue(String.class);
                    String subtitle = postDataSnapshot.child("Subtitle").getValue(String.class);
                    JobListItem jobListItem = new JobListItem(image,title,subtitle);
                    jobs.add(jobListItem);
                    Log.d("PDS",postDataSnapshot.toString());
                }
                progressBar.setVisibility(View.GONE);

                if(jobs.size()>0)
                {
                    jobListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        list_grid_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(layoutManager.getSpanCount()==2) {
                    layoutManager.setSpanCount(1);
                    list_grid_icon.setImageDrawable(AnimatedVectorDrawableCompat.create(JobListingActivity.this, R.drawable.list_to_grid));
                }
                else {
                    layoutManager.setSpanCount(2);
                    list_grid_icon.setImageDrawable(AnimatedVectorDrawableCompat.create(JobListingActivity.this, R.drawable.grid_to_list));
                }
                ((Animatable) list_grid_icon.getDrawable()).start();
                jobListAdapter.notifyItemRangeChanged(0,jobListAdapter.getItemCount());
            }
        });
        search_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(search_bar.getVisibility()==View.VISIBLE) {
                    search_bar.setVisibility(View.GONE);
                    top_bar.setVisibility(View.GONE);
                    hideKeyboard(search_bar);
                }
                else
                {
                    showKeyboard(search_bar);
                    if(top_bar.getVisibility()==View.VISIBLE) {
                        search_bar.setVisibility(View.VISIBLE);
                        location_bar.setVisibility(View.GONE);
                        my_location.setVisibility(View.GONE);
                    }
                    else
                    {
                        search_bar.setVisibility(View.VISIBLE);
                        top_bar.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        location_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(location_bar.getVisibility()==View.VISIBLE) {
                    location_bar.setVisibility(View.GONE);
                    top_bar.setVisibility(View.GONE);
                    hideKeyboard(location_bar);
                }
                else
                {
                    showKeyboard(location_bar);
                    if(top_bar.getVisibility()==View.VISIBLE) {
                        search_bar.setVisibility(View.GONE);
                        location_bar.setVisibility(View.VISIBLE);
                        my_location.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        location_bar.setVisibility(View.VISIBLE);
                        my_location.setVisibility(View.VISIBLE);
                        top_bar.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

    }

    public void hideKeyboard(View view) {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
    }

    public void showKeyboard(View view)
    {
        try {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInputFromWindow(
                    view.getApplicationWindowToken(),
                    InputMethodManager.SHOW_FORCED, 0);
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
    }
}

package com.doghat.gitboy.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.doghat.gitboy.R;
import com.doghat.gitboy.adapters.RepoPagerAdapter;
import com.doghat.gitboy.models.Repo;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RepoDetailActivity extends AppCompatActivity {
    @Bind(R.id.viewPager) ViewPager mViewPager;
    private RepoPagerAdapter adapterViewPager;
    ArrayList<Repo> mRepos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_detail);
        ButterKnife.bind(this);

        mRepos = Parcels.unwrap(getIntent().getParcelableExtra("repos"));
        int startingPosition = getIntent().getIntExtra("position",0);


        adapterViewPager = new RepoPagerAdapter(getSupportFragmentManager(),mRepos);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);

    }
}

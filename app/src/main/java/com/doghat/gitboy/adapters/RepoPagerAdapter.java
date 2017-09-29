package com.doghat.gitboy.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.doghat.gitboy.models.Repo;
import com.doghat.gitboy.ui.RepoDetailFragment;

import java.util.ArrayList;


public class RepoPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Repo> mRepos;
    private String mSource;
    public RepoPagerAdapter(FragmentManager fm, ArrayList<Repo> repos, String source) {
        super(fm);
        mRepos = repos;
        mSource = source;
    }

    @Override
    public Fragment getItem(int position) {
        return RepoDetailFragment.newInstance(mRepos, position, mSource);
    }

    @Override
    public int getCount() {
        return mRepos.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mRepos.get(position).getName();
    }
}

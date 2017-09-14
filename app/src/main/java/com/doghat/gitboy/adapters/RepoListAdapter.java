package com.doghat.gitboy.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

/**
 * Created by saul on 9/14/17.
 */

public class RepoListAdapter extends ArrayAdapter {
    private Context mContext;
    private String[] mRepos;
    private String[] mUsernames;

    public RepoListAdapter(Context mContext, int resource, String[] mRepos, String[] mUsernames){
        super(mContext, resource);
        this.mContext = mContext;
        this.mRepos = mRepos;
        this.mUsernames = mUsernames;
    }

    @Override
    public Object getItem(int position) {
        String repo = mRepos[position];
        String username = mUsernames[position];
        return String.format("%s \nOwner: %s", repo, username);
    }

    @Override
    public int getCount() {
        return mRepos.length;
    }
}

package com.doghat.gitboy.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.doghat.gitboy.R;
import com.doghat.gitboy.models.Repo;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RepoListAdapter extends RecyclerView.Adapter<RepoListAdapter.RepoViewHolder>{
    private ArrayList<Repo> mRepos = new ArrayList<>();
    private Context mContext;

    public RepoListAdapter(Context context, ArrayList<Repo> repos){
        mContext = context;
        mRepos = repos;
    }

    @Override
    public RepoListAdapter.RepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_list_item, parent, false);
        RepoViewHolder viewHolder = new RepoViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RepoViewHolder holder, int position) {
        holder.bindRepo(mRepos.get(position));
    }

    @Override
    public int getItemCount() {
        return mRepos.size();
    }

    public class RepoViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.repoImageView) ImageView mRepoImageView;
        @Bind(R.id.repoNameTextView) TextView mRepoNameTextView;
        @Bind(R.id.repoOwnerTextView) TextView mRepoOwnerTextView;
        @Bind(R.id.repoLanguageTextView) TextView mRepoLanguageTextView;
        //@Bind(R.id.repoRecyclerView) RecyclerView mRepoRecyclerView;

        private Context mContext;

        public RepoViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();

        }

        public void bindRepo(final Repo repo) {
            mRepoNameTextView.setText(repo.getmName());
            mRepoLanguageTextView.setText(repo.getmLanguage());
            mRepoOwnerTextView.setText("Owner: " + repo.getmOwner());

            mRepoImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent webIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(repo.getmHtmlUrl()));
                    mContext.startActivity(webIntent);
                }
            });
        }
    }
}

package com.doghat.gitboy.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.doghat.gitboy.R;
import com.doghat.gitboy.models.Repo;
import com.squareup.picasso.Picasso;

import org.parceler.Parcel;
import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RepoDetailFragment extends Fragment {
    @Bind(R.id.repoImageView) ImageView mRepoImageView;
    @Bind(R.id.repoNameTextView) TextView mRepoNameTextView;
    @Bind(R.id.repoDetailTextView) TextView mRepoDetailTextView;
    @Bind(R.id.repoOwnerTextView) TextView mRepoOwnerTextView;
    @Bind(R.id.repoLanguageTextView) TextView mRepoLanguageTextView;

    private Repo mRepo;

    public static RepoDetailFragment newInstance(Repo repo) {
        RepoDetailFragment repoDetailFragment = new RepoDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("repo", Parcels.wrap(repo));
        repoDetailFragment.setArguments(args);
        return repoDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepo = Parcels.unwrap(getArguments().getParcelable("repo"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repo_detail, container, false);

        mRepoDetailTextView.setText(mRepo.getmDescription());
        mRepoImageView.
    }

}

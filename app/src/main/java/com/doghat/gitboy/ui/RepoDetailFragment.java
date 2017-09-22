package com.doghat.gitboy.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.doghat.gitboy.Constants;
import com.doghat.gitboy.R;
import com.doghat.gitboy.models.Repo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RepoDetailFragment extends Fragment implements View.OnClickListener {
    @Bind(R.id.repoImageView) ImageView mRepoImageView;
    @Bind(R.id.repoNameTextView) TextView mRepoNameTextView;
    @Bind(R.id.repoDetailTextView) TextView mRepoDetailTextView;
    @Bind(R.id.repoOwnerTextView) TextView mRepoOwnerTextView;
    @Bind(R.id.repoLanguageTextView) TextView mRepoLanguageTextView;
    @Bind(R.id.saveRepoButton) Button mSaveRepoButton;

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
        ButterKnife.bind(this, view);

        Picasso.with(view.getContext()).load(R.drawable.giticon).into(mRepoImageView);
        mRepoDetailTextView.setText(mRepo.getDescription());
        mRepoLanguageTextView.setText(mRepo.getLanguage());
        mRepoNameTextView.setText(mRepo.getName());
        mRepoOwnerTextView.setText(mRepo.getOwner());

        mSaveRepoButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == mSaveRepoButton) {
            DatabaseReference repoRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_REPOS);
            repoRef.push().setValue(mRepo);
            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
        }
    }

}

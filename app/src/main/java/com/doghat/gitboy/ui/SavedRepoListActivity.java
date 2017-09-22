package com.doghat.gitboy.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.doghat.gitboy.Constants;
import com.doghat.gitboy.R;
import com.doghat.gitboy.adapters.FirebaseRepoViewHolder;
import com.doghat.gitboy.models.Repo;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SavedRepoListActivity extends AppCompatActivity {
    private DatabaseReference mRepoReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;

    @Bind(R.id.repoSearchResultsRecycler) RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_repo_search_results);
        ButterKnife.bind(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        mRepoReference = FirebaseDatabase
                .getInstance()
                .getReference(Constants.FIREBASE_CHILD_REPOS)
                .child(uid);

        setUpFirebaseAdapter();

    }

    private void setUpFirebaseAdapter() {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Repo, FirebaseRepoViewHolder>
                (Repo.class, R.layout.repo_list_item, FirebaseRepoViewHolder.class,
                        mRepoReference) {
            @Override
            protected void populateViewHolder(FirebaseRepoViewHolder viewHolder, Repo model, int position) {
                viewHolder.bindRepo(model);
            }
        };
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }
}

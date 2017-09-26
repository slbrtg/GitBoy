package com.doghat.gitboy.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.doghat.gitboy.Constants;
import com.doghat.gitboy.R;
import com.doghat.gitboy.models.Repo;
import com.doghat.gitboy.ui.RepoDetailActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;


public class FirebaseRepoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    View mView;
    Context mContext;

    public FirebaseRepoViewHolder (View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindRepo(Repo repo) {
        ImageView repoImageView = (ImageView) mView.findViewById(R.id.repoImageView);
        TextView repoNameTextView = (TextView) mView.findViewById(R.id.repoNameTextView);
        TextView repoDetailTextView = (TextView) mView.findViewById(R.id.repoDetailTextView);
        TextView repoOwnerTextView = (TextView) mView.findViewById(R.id.repoOwnerTextView);
        TextView repoLanguageTextView = (TextView) mView.findViewById(R.id.repoLanguageTextView);
        Picasso.with(mContext).load(R.drawable.giticon).into(repoImageView);

        repoNameTextView.setText(repo.getName());
        repoOwnerTextView.setText(repo.getOwner());
        repoLanguageTextView.setText(repo.getLanguage());
    }

    @Override
    public void onClick(View v) {
        final ArrayList<Repo> repos = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_REPOS);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    repos.add(snapshot.getValue(Repo.class));
                }

                int itemPosition = getLayoutPosition();

                Intent intent = new Intent(mContext, RepoDetailActivity.class);
                intent.putExtra("position", itemPosition);
                intent.putExtra("repos", Parcels.wrap(repos));

                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
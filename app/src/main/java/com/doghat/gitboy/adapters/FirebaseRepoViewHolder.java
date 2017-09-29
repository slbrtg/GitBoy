package com.doghat.gitboy.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.doghat.gitboy.Constants;
import com.doghat.gitboy.R;
import com.doghat.gitboy.models.Repo;
import com.doghat.gitboy.ui.RepoDetailActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.io.IOException;
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
        if (repo.getImageUrl() != null) {
            try {
                Bitmap imageBitmap = decodeFromFirebaseBase64(repo.getImageUrl());
                repoImageView.setImageBitmap(imageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Picasso.with(mContext)
                    .load(R.drawable.giticon)
                    .into(repoImageView);
        }

        repoNameTextView.setText(repo.getName());
        repoOwnerTextView.setText(repo.getOwner());
        repoLanguageTextView.setText(repo.getLanguage());
    }

    public static Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }


    @Override
    public void onClick(View v) {
        final ArrayList<Repo> repos = new ArrayList<>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        DatabaseReference ref = FirebaseDatabase
                .getInstance()
                .getReference(Constants.FIREBASE_CHILD_REPOS)
                .child(uid);

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
                intent.putExtra("source", "saved");

                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
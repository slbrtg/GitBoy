package com.doghat.gitboy.ui;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.doghat.gitboy.Constants;
import com.doghat.gitboy.R;
import com.doghat.gitboy.models.Repo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RepoDetailFragment extends Fragment implements View.OnClickListener {
    @Bind(R.id.repoImageView) ImageView mRepoImageView;
    @Bind(R.id.repoNameTextView) TextView mRepoNameTextView;
    @Bind(R.id.repoDetailTextView) TextView mRepoDetailTextView;
    @Bind(R.id.repoOwnerTextView) TextView mRepoOwnerTextView;
    @Bind(R.id.repoLanguageTextView) TextView mRepoLanguageTextView;
    @Bind(R.id.saveRepoButton) Button mSaveRepoButton;

    private String mSource;
    private static final int REQUEST_IMAGE_CAPTURE = 111;
    private Repo mRepo;
    private ArrayList<Repo> mRepos = new ArrayList<>();
    private int mPosition;

    public static RepoDetailFragment newInstance(ArrayList<Repo> repos, int position, String source) {
        RepoDetailFragment repoDetailFragment = new RepoDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("repo", Parcels.wrap(repos));
        args.putInt("position", position);
        args.putString("source", source);
        repoDetailFragment.setArguments(args);
        return repoDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSource = getArguments().getString("source");
        mRepos = Parcels.unwrap(getArguments().getParcelable("repo"));
        mPosition = getArguments().getInt("position");
        mRepo = mRepos.get(mPosition);

        setHasOptionsMenu(true);

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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if(mSource.equals("saved")){
            inflater.inflate(R.menu.menu_photo, menu);
        } else {
            inflater.inflate(R.menu.menu_main, menu);
        }
    }

    public void onLaunchCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public static Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            //mImageLabel.setImageBitmap(imageBitmap);
            encodeBitmapAndSaveToFirebase(imageBitmap);
        }
    }

    public void encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference(Constants.FIREBASE_CHILD_REPOS)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(mRepo.getPushId())
                .child("imageUrl");
        ref.setValue(imageEncoded);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_photo:
                onLaunchCamera();
                default:
                    break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if (v == mSaveRepoButton) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            DatabaseReference repoRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_REPOS)
                    .child(uid);
            DatabaseReference pushRef = repoRef.push();
            String pushId = pushRef.getKey();
            mRepo.setPushId(pushId);
            pushRef.setValue(mRepo);

            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
        }
    }

}

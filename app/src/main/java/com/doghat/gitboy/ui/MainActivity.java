
package com.doghat.gitboy.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.doghat.gitboy.Constants;
import com.doghat.gitboy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = MainActivity.class.getSimpleName();

    private DatabaseReference mSearchedRepoReference;
    private ValueEventListener mSearchedRepoReferenceListener;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String mRecentRepo;

    @Bind(R.id.searchRepoButton) Button mSearchRepoButton;
    @Bind(R.id.searchRepoEditText) EditText mSearchRepoEditText;
    @Bind(R.id.appNameTextView) TextView mAppNameTextView;
    @Bind(R.id.searchRepoTextView) TextView mSearchRepoTextView;
    @Bind(R.id.viewSavedRepoButton) Button mViewSavedRepoButton;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        mSearchedRepoReference = FirebaseDatabase
//                .getInstance()
//                .getReference()
//                .child(Constants.FIREBASE_CHILD_SEARCHED_REPO);
//        mSearchedRepoReferenceListener = mSearchedRepoReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot repoSnapshot : dataSnapshot.getChildren()) {
//                    String repo = repoSnapshot.getValue().toString();
//                    Log.d("Repos updated", "Repo: " + repo);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        mRecentRepo = mSharedPreferences.getString(Constants.PREFERENCES_REPO_KEY, null);

        if (mRecentRepo != null) {
            mSearchRepoEditText.setText(mRecentRepo);
        }

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    getSupportActionBar().setTitle("Welcome, " + user.getDisplayName() + "!");
                } else {

                }
            }
        };

        Typeface futuraBI = Typeface.createFromAsset(getAssets(), "fonts/FuturaBoldItalic.ttf");
        Typeface futuraMedium = Typeface.createFromAsset(getAssets(), "fonts/Futura-Medium.ttf");
        Typeface gb = Typeface.createFromAsset(getAssets(), "fonts/gb.ttf");
        mAppNameTextView.setTypeface(futuraBI);
        mSearchRepoTextView.setTypeface(futuraMedium);

        mSearchRepoButton.setOnClickListener(this);
        mViewSavedRepoButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if(v == mSearchRepoButton){
            String searchRepoQuery = mSearchRepoEditText.getText().toString();
            if (searchRepoQuery.equals("")){
                Toast.makeText(MainActivity.this,"Please provide a search",Toast.LENGTH_LONG).show();
            } else {
                addToSharedPreferences(searchRepoQuery);
                //saveRepoToFirebase(searchRepoQuery);
                Intent intent = new Intent(MainActivity.this, RepoSearchResultsActivity.class);
                intent.putExtra("searchRepoQuery", searchRepoQuery);
                startActivity(intent);
            }
        }
        if (v == mViewSavedRepoButton) {
            Intent intent = new Intent(MainActivity.this, SavedRepoListActivity.class);
            startActivity(intent);
        }
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mSearchedRepoReference.removeEventListener(mSearchedRepoReferenceListener);
//    }

//    public void saveRepoToFirebase(String searchRepoQuery) {
//        mSearchedRepoReference.setValue(searchRepoQuery);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent (MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void addToSharedPreferences(String repo) {
        mEditor.putString(Constants.PREFERENCES_REPO_KEY, repo).apply();
    }
}


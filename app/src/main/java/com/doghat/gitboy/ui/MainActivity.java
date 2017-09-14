package com.doghat.gitboy.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.doghat.gitboy.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = MainActivity.class.getSimpleName();
    @Bind(R.id.searchRepoButton) Button mSearchRepoButton;
    @Bind(R.id.searchRepoEditText) EditText mSearchRepoEditText;
    @Bind(R.id.appNameTextView) TextView mAppNameTextView;
    @Bind(R.id.searchRepoTextView) TextView mSearchRepoTextView;
    @Bind(R.id.signInButton) Button mSignInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Typeface futuraBI = Typeface.createFromAsset(getAssets(), "fonts/FuturaBoldItalic.ttf");
        Typeface futuraMedium = Typeface.createFromAsset(getAssets(), "fonts/Futura-Medium.ttf");
        Typeface gb = Typeface.createFromAsset(getAssets(), "fonts/gb.ttf");
        mAppNameTextView.setTypeface(futuraBI);
        mSearchRepoTextView.setTypeface(futuraMedium);

        mSearchRepoButton.setOnClickListener(this);
        mSignInButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if(v == mSearchRepoButton){
            String searchRepoQuery = mSearchRepoEditText.getText().toString();
            Intent intent = new Intent(MainActivity.this, RepoSearchResultsActivity.class);
            intent.putExtra("searchRepoQuery", searchRepoQuery);
            startActivity(intent);
        } else if(v == mSignInButton){
            Intent intent = new Intent(MainActivity.this, SignInActivity.class);
            startActivity(intent);
        }
    }
}


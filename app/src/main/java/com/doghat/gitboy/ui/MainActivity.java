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

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    @Bind(R.id.searchRepoButton) Button mSearchRepoButton;
    @Bind(R.id.searchRepoEditText) EditText mSearchRepoEditText;
    @Bind(R.id.appNameTextView) TextView mAppNameTextView;
    @Bind(R.id.searchRepoTextView) TextView mSearchRepoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Typeface futura = Typeface.createFromAsset(getAssets(), "fonts/Futura-Medium.ttf");
        Typeface gb = Typeface.createFromAsset(getAssets(), "fonts/gb.ttf");
        mAppNameTextView.setTypeface(futura);
        mSearchRepoTextView.setTypeface(gb);

        mSearchRepoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String repo = mSearchRepoEditText.getText().toString();
                Intent intent = new Intent(MainActivity.this, RepoSearchResultsActivity.class);
                intent.putExtra("repo", repo);
                startActivity(intent);
            }
        });
    }
}

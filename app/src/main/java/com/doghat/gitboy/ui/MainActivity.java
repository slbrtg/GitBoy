package com.doghat.gitboy.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.doghat.gitboy.R;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    @Bind(R.id.searchRepoButton) Button mSearchRepoButton;
    @Bind(R.id.searchRepoEditText) EditText mSearchRepoEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mSearchRepoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String repo = mSearchRepoEditText.getText().toString();
                Log.d(TAG, repo);
                Intent intent = new Intent(MainActivity.this, RepoSearchResultsActivity.class);
                intent.putExtra("repo", repo);
                startActivity(intent);
            }
        });
    }
}

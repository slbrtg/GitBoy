package com.doghat.gitboy.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.doghat.gitboy.R;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.searchRepoButton)
    Button mSearchRepoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

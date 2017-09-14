package com.doghat.gitboy.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.doghat.gitboy.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RepoSearchResultsActivity extends AppCompatActivity {
    @Bind(R.id.repoListView) ListView mRepoListView;
    @Bind(R.id.searchRepoResultsTextView) TextView mSearchRepoResultsTextView;
    private String[] repos = new String[]{"Fizz Buzz", "To Do List", "PingPong",
    "MovieSearch-Android", "MyRestaurant-Android", "Emu", "TicTacToe-Javascript", "Solitaire", "Portfolio",
    "GitBoy", "todo-angular2"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_search_results);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String searchRepoQuery = intent.getStringExtra("searchRepoQuery");
        mSearchRepoResultsTextView.setText("You searched for: " + searchRepoQuery);

    }
}

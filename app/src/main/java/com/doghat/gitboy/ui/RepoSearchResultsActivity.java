package com.doghat.gitboy.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.doghat.gitboy.R;
import com.doghat.gitboy.adapters.RepoListAdapter;
import com.doghat.gitboy.services.GithubService;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RepoSearchResultsActivity extends AppCompatActivity {
    public static final String TAG = RepoSearchResultsActivity.class.getSimpleName();

    @Bind(R.id.repoListView) ListView mRepoListView;
    @Bind(R.id.searchRepoResultsTextView) TextView mSearchRepoResultsTextView;
    private String[] repos = new String[]{"Fizz Buzz", "To Do List", "PingPong",
            "MovieSearch-Android", "MyRestaurant-Android", "Emu", "TicTacToe-Javascript", "Solitaire", "Portfolio",
            "GitBoy", "todo-angular2"};
    private String[] usernames = new String[]{"slbrtg", "slbrtg", "slbrtg",
            "slbrtg", "slbrtg", "slbrtg", "slbrtg", "slbrtg", "slbrtg", "slbrtg", "slbrtg", "slbrtg"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_search_results);
        ButterKnife.bind(this);

        RepoListAdapter adapter = new RepoListAdapter(this, android.R.layout.simple_list_item_1, repos, usernames);
        mRepoListView.setAdapter(adapter);

        Intent intent = getIntent();
        String searchRepoQuery = intent.getStringExtra("searchRepoQuery");
        mSearchRepoResultsTextView.setText("You searched for: " + searchRepoQuery);

        getRepos(searchRepoQuery);
    }

    private void getRepos(String repo){
        final GithubService githubService = new GithubService();
        githubService.findRepos(repo, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String jsonData = response.body().string();
                    Log.v(TAG, jsonData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

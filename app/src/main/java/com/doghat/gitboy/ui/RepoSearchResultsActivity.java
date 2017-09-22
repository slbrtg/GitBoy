package com.doghat.gitboy.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.doghat.gitboy.R;
import com.doghat.gitboy.adapters.RepoListAdapter;
import com.doghat.gitboy.models.Repo;
import com.doghat.gitboy.services.GithubService;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RepoSearchResultsActivity extends AppCompatActivity {
    public static final String TAG = RepoSearchResultsActivity.class.getSimpleName();

    @Bind(R.id.repoSearchResultsRecycler) RecyclerView mRepoSearchResultsRecycler;
    private RepoListAdapter mAdapter;

    public ArrayList<Repo> mRepos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_search_results);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String searchRepoQuery = intent.getStringExtra("searchRepoQuery");

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
                mRepos = githubService.processResults(response);

                RepoSearchResultsActivity.this.runOnUiThread(new Runnable(){

                    @Override
                    public void run() {
                        mAdapter = new RepoListAdapter(getApplicationContext(), mRepos);
                        mRepoSearchResultsRecycler.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(RepoSearchResultsActivity.this);
                        mRepoSearchResultsRecycler.setLayoutManager(layoutManager);
                        mRepoSearchResultsRecycler.setHasFixedSize(true);


                        for(Repo repo : mRepos){
                            Log.d(TAG, "______________________________________");
                            Log.d(TAG, "Name: " + repo.getName());
                            Log.d(TAG, "Description: " + repo.getDescription());
                            Log.d(TAG, "HtmlUrl: " + repo.getHtmlUrl());
                            Log.d(TAG, "IsPrivate: " + repo.getIsPrivate());
                            Log.d(TAG, "Language: " + repo.getLanguage());
                            Log.d(TAG, "Owner: " + repo.getOwner());
                            Log.d(TAG, "OwnerType: " + repo.getOwnerType());
                            Log.d(TAG, "OwnerAvatarUrl: " + repo.getOwnerAvatarUrl());
                            Log.d(TAG, "OwnerHtmlUrl: " + repo.getOwnerProfileHtmlUrl());
                            Log.d(TAG,"OwnerReposHtmlUrl: " + repo.getOwnerReposHtmlUrl());
                        }
                    }
                });
            }
        });
    }
}

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

    @Bind(R.id.repoListView) ListView mRepoListView;
    @Bind(R.id.searchRepoResultsTextView) TextView mSearchRepoResultsTextView;

    public ArrayList<Repo> mRepos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_search_results);
        ButterKnife.bind(this);

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
                mRepos = githubService.processResults(response);

                RepoSearchResultsActivity.this.runOnUiThread(new Runnable(){

                    @Override
                    public void run() {
                       String[] repoNames = new String[mRepos.size()];
                        for (int i = 0; i < repoNames.length; i++){
                            repoNames[i] = mRepos.get(i).getmName();
                        }

                        ArrayAdapter adapter = new ArrayAdapter
                                (RepoSearchResultsActivity.this,
                                        android.R.layout.simple_list_item_1,
                                        repoNames);
                        mRepoListView.setAdapter(adapter);

                        for(Repo repo : mRepos){
                            Log.d(TAG, "______________________________________");
                            Log.d(TAG, "Name: " + repo.getmName());
                            Log.d(TAG, "Description: " + repo.getmDescription());
                            Log.d(TAG, "HtmlUrl: " + repo.getmHtmlUrl());
                            Log.d(TAG, "IsPrivate: " + repo.getmIsPrivate());
                            Log.d(TAG, "Language: " + repo.getmLanguage());
                            Log.d(TAG, "Owner: " + repo.getmOwner());
                            Log.d(TAG, "OwnerType: " + repo.getmOwnerType());
                            Log.d(TAG, "OwnerAvatarUrl: " + repo.getmOwnerAvatarUrl());
                            Log.d(TAG, "OwnerHtmlUrl: " + repo.getmOwnerProfileHtmlUrl());
                            Log.d(TAG,"OwnerReposHtmlUrl: " + repo.getmOwnerReposHtmlUrl());
                        }
                    }
                });
            }
        });
    }
}

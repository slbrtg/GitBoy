package com.doghat.gitboy.services;

import android.util.Log;

import com.doghat.gitboy.Constants;
import com.doghat.gitboy.ui.RepoSearchResultsActivity;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

public class GithubService {
    public static final String TAG = RepoSearchResultsActivity.class.getSimpleName();
    public static void findRepos(String repo, Callback callback){
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.GITHUB_BASE_URL).newBuilder();
        urlBuilder.addPathSegments(Constants.GITHUB_REPO_SEARCH_PATH);
        urlBuilder.addQueryParameter(Constants.GITHUB_REPO_SEARCH_QUERY_PARAMETER, repo);
        urlBuilder.addQueryParameter(Constants.GITHUB_ACCESS_TOKEN_PARAMETER, Constants.GITHUB_TOKEN);

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()

                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }
}

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
        OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(Constants.GITHUB_CLIENT_ID,
                Constants.GITHUB_CLIENT_SECRET);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new SigningInterceptor(consumer))
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.GITHUB_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.GITHUB_REPO_QUERY_PARAMETER, repo);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();
        Log.d(TAG, request.toString());

        Call call = client.newCall(request);
        call.enqueue(callback);
    }
}

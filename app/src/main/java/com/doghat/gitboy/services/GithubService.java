package com.doghat.gitboy.services;

import android.util.Log;

import com.doghat.gitboy.Constants;
import com.doghat.gitboy.models.Repo;
import com.doghat.gitboy.ui.RepoSearchResultsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


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
        Log.d(TAG, url);

        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<Repo> processResults(Response response) {
        ArrayList<Repo> repos = new ArrayList<>();

        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                JSONObject gitJSON = new JSONObject(jsonData);
                JSONArray reposJSON = gitJSON.getJSONArray("items");

                for (int i = 0; i < reposJSON.length(); i++) {
                    JSONObject repoJSON = reposJSON.getJSONObject(i);
                    JSONObject ownerJSON = reposJSON.getJSONObject(i).getJSONObject("owner");
                    String name = repoJSON.getString("name");
                    String description = repoJSON.getString("description");
                    String htmlUrl = repoJSON.getString("html_url");
                    Boolean isPrivate = repoJSON.getBoolean("private");
                    String language = repoJSON.getString("language");
                    String owner = ownerJSON.getString("login");
                    String ownerType = ownerJSON.getString("type");
                    String ownerAvatarUrl = ownerJSON.getString("avatar_url");
                    String ownerHtmlUrl = ownerJSON.getString("html_url");

                    Repo repo = new Repo(name,description,htmlUrl,isPrivate,
                            language,owner,ownerType,
                            ownerAvatarUrl,ownerHtmlUrl);
                    repos.add(repo);
                    Log.d(TAG,repo.getName());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return repos;
    }
}


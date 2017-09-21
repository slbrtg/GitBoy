package com.doghat.gitboy.models;

import org.parceler.Parcel;

@Parcel
public class Repo {
    String mName;
    String mDescription;
    String mHtmlUrl;
    Boolean mIsPrivate;
    String mLanguage;
    String mOwner;
    String mOwnerType;
    String mOwnerAvatarUrl;
    String mOwnerProfileHtmlUrl;
    String mOwnerReposHtmlUrl;

    //Parceler Constructor
    public Repo() {}

    public Repo(String name, String description, String htmlUrl, Boolean isPrivate,
                String language, String owner, String ownerType, String ownerAvatarUrl,
                String ownerProfileHtmlUrl) {
        this.mName = name;
        this.mDescription = description;
        this.mHtmlUrl = htmlUrl;
        this.mIsPrivate = isPrivate;
        this.mLanguage = language;
        this.mOwner = owner;
        this.mOwnerType = ownerType;
        this.mOwnerAvatarUrl = ownerAvatarUrl;
        this.mOwnerProfileHtmlUrl = ownerProfileHtmlUrl;
        this.mOwnerReposHtmlUrl = ownerProfileHtmlUrl + "/repos";
    }

    public String getmName() {
        return mName;
    }

    public String getmDescription() {
        return mDescription;
    }

    public String getmHtmlUrl() {
        return mHtmlUrl;
    }

    public Boolean getmIsPrivate() {
        return mIsPrivate;
    }

    public String getmLanguage() {
        return mLanguage;
    }

    public String getmOwner() {
        return mOwner;
    }

    public String getmOwnerType() {
        return mOwnerType;
    }

    public String getmOwnerAvatarUrl() {
        return mOwnerAvatarUrl;
    }

    public String getmOwnerProfileHtmlUrl() {
        return mOwnerProfileHtmlUrl;
    }

    public String getmOwnerReposHtmlUrl() {
        return mOwnerReposHtmlUrl;
    }
}

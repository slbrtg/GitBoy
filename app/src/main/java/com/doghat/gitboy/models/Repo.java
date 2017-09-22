package com.doghat.gitboy.models;

import org.parceler.Parcel;

@Parcel
public class Repo {

    String name;
    String description;
    String htmlUrl;
    Boolean isPrivate;
    String language;
    String owner;
    String ownerType;
    String ownerAvatarUrl;
    String ownerProfileHtmlUrl;
    String ownerReposHtmlUrl;
    String pushId;


    public Repo() {}

    public Repo(String name, String description, String htmlUrl, Boolean isPrivate,
                String language, String owner, String ownerType, String ownerAvatarUrl,
                String ownerProfileHtmlUrl) {
        this.name = name;
        this.description = description;
        this.htmlUrl = htmlUrl;
        this.isPrivate = isPrivate;
        this.language = language;
        this.owner = owner;
        this.ownerType = ownerType;
        this.ownerAvatarUrl = ownerAvatarUrl;
        this.ownerProfileHtmlUrl = ownerProfileHtmlUrl;
        this.ownerReposHtmlUrl = ownerProfileHtmlUrl + "/repos";
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public Boolean getIsPrivate() {
        return isPrivate;
    }

    public String getLanguage() {
        return language;
    }

    public String getOwner() {
        return owner;
    }

    public String getOwnerType() {
        return ownerType;
    }

    public String getOwnerAvatarUrl() {
        return ownerAvatarUrl;
    }

    public String getOwnerProfileHtmlUrl() {
        return ownerProfileHtmlUrl;
    }

    public String getOwnerReposHtmlUrl() {
        return ownerReposHtmlUrl;
    }

    public String getPushId(String pushId) {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}

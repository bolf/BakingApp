package com.and.blf.baking_app.model;

class Step {
    private long mId;
    private String mSshortDescription;
    private String mDescription;
    private String mVideoURL;
    private String mThumbnailURL;

    public Step(long id, String sshortDescription, String description, String videoURL, String thumbnailURL) {
        mId = id;
        mSshortDescription = sshortDescription;
        mDescription = description;
        mVideoURL = videoURL;
        mThumbnailURL = thumbnailURL;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getSshortDescription() {
        return mSshortDescription;
    }

    public void setSshortDescription(String sshortDescription) {
        mSshortDescription = sshortDescription;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getVideoURL() {
        return mVideoURL;
    }

    public void setVideoURL(String videoURL) {
        mVideoURL = videoURL;
    }

    public String getThumbnailURL() {
        return mThumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        mThumbnailURL = thumbnailURL;
    }
}

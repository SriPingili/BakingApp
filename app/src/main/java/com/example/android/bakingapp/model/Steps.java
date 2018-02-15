package com.example.android.bakingapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;

/**
 * Model class for the Steps data
 */
@Data
public class Steps implements Serializable {

    @SerializedName("shortDescription")
    private final String shortDescription;

    @SerializedName("description")
    private final String description;

    @SerializedName("videoURL")
    private final String videoURL;

    @SerializedName("thumbnailURL")
    private final String thumbnailURL;

    public Steps(String shortDescription, String description, String videoURL, String thumbnailURL) {
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }
}

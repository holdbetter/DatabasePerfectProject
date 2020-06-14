
package com.holdbetter.dbperfectproject.gson;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VolumeInfo {

    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("authors")
    @Expose
    public List<String> authors = null;
    @SerializedName("imageLinks")
    @Expose
    public ImageLinks imageLinks;
    @SerializedName("language")
    @Expose
    public String language;

}

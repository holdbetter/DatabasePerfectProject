
package com.holdbetter.dbperfectproject.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GoogleBookInstance
{
    @SerializedName("kind")
    @Expose
    public String kind;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("selfLink")
    @Expose
    public String selfLink;
    @SerializedName("volumeInfo")
    @Expose
    public VolumeInfo volumeInfo;
}

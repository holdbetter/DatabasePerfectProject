
package com.holdbetter.dbperfectproject.gson;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GoogleBookData
{
    @SerializedName("kind")
    @Expose
    public String kind;
    @SerializedName("totalItems")
    @Expose
    public int totalItems;
    @SerializedName("items")
    @Expose
    public List<GoogleBookInstance> googleBookInstances = null;

}

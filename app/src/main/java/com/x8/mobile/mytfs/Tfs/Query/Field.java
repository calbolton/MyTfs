package com.x8.mobile.mytfs.Tfs.Query;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Field {

    @SerializedName("referenceName")
    @Expose
    private String referenceName;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("url")
    @Expose
    private String url;

    /**
     *
     * @return
     * The referenceName
     */
    public String getReferenceName() {
        return referenceName;
    }

    /**
     *
     * @param referenceName
     * The referenceName
     */
    public void setReferenceName(String referenceName) {
        this.referenceName = referenceName;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     * The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

}
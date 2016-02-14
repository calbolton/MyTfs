package com.x8.mobile.mytfs.Tfs.WorkItems;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.x8.mobile.mytfs.Tfs.Query.Source;
import com.x8.mobile.mytfs.Tfs.Query.Target;

public class WorkItemRelation {

    @SerializedName("target")
    @Expose
    private Target target;
    @SerializedName("rel")
    @Expose
    private String rel;
    @SerializedName("source")
    @Expose
    private Source source;

    /**
     *
     * @return
     * The target
     */
    public Target getTarget() {
        return target;
    }

    /**
     *
     * @param target
     * The target
     */
    public void setTarget(Target target) {
        this.target = target;
    }

    /**
     *
     * @return
     * The rel
     */
    public String getRel() {
        return rel;
    }

    /**
     *
     * @param rel
     * The rel
     */
    public void setRel(String rel) {
        this.rel = rel;
    }

    /**
     *
     * @return
     * The source
     */
    public Source getSource() {
        return source;
    }

    /**
     *
     * @param source
     * The source
     */
    public void setSource(Source source) {
        this.source = source;
    }

}
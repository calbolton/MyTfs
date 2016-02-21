package com.x8.mobile.mytfs.Tfs.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.x8.mobile.mytfs.Tfs.Models.WorkItem;

public class WorkItemRelation {

    @SerializedName("target")
    @Expose
    private WorkItem target;
    @SerializedName("rel")
    @Expose
    private String rel;
    @SerializedName("source")
    @Expose
    private WorkItem source;

    /**
     *
     * @return
     * The target
     */
    public WorkItem getTarget() {
        return target;
    }

    /**
     *
     * @param target
     * The target
     */
    public void setTarget(WorkItem target) {
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
    public WorkItem getSource() {
        return source;
    }

    /**
     *
     * @param source
     * The source
     */
    public void setSource(WorkItem source) {
        this.source = source;
    }

}
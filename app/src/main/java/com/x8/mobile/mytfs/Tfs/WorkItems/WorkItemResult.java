package com.x8.mobile.mytfs.Tfs.WorkItems;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WorkItemResult {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("value")
    @Expose
    private List<WorkItem> value = new ArrayList<>();

    /**
     *
     * @return
     * The count
     */
    public Integer getCount() {
        return count;
    }

    /**
     *
     * @param count
     * The count
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     *
     * @return
     * The value
     */
    public List<WorkItem> getValue() {
        return value;
    }

    /**
     *
     * @param value
     * The value
     */
    public void setValue(List<WorkItem> value) {
        this.value = value;
    }

    @Override
    public String toString() {
        String retString = "";

        for (WorkItem workItem: value) {
            retString += workItem.getTitle() + "(" + workItem.getWorkItemType() + ") : ";
        }
        return  retString;
    }
}
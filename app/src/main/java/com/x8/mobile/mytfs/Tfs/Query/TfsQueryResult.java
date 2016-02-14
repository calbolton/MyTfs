package com.x8.mobile.mytfs.Tfs.Query;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.x8.mobile.mytfs.Tfs.WorkItems.WorkItem;

public class TfsQueryResult {

    @SerializedName("queryType")
    @Expose
    private String queryType;
    @SerializedName("queryResultType")
    @Expose
    private String queryResultType;
    @SerializedName("asOf")
    @Expose
    private String asOf;
    @SerializedName("columns")
    @Expose
    private List<Column> columns = new ArrayList<>();
    @SerializedName("workItems")
    @Expose
    private List<WorkItem> workItems = new ArrayList<>();

    /**
     *
     * @return
     * The queryType
     */
    public String getQueryType() {
        return queryType;
    }

    /**
     *
     * @param queryType
     * The queryType
     */
    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    /**
     *
     * @return
     * The queryResultType
     */
    public String getQueryResultType() {
        return queryResultType;
    }

    /**
     *
     * @param queryResultType
     * The queryResultType
     */
    public void setQueryResultType(String queryResultType) {
        this.queryResultType = queryResultType;
    }

    /**
     *
     * @return
     * The asOf
     */
    public String getAsOf() {
        return asOf;
    }

    /**
     *
     * @param asOf
     * The asOf
     */
    public void setAsOf(String asOf) {
        this.asOf = asOf;
    }

    /**
     *
     * @return
     * The columns
     */
    public List<Column> getColumns() {
        return columns;
    }

    /**
     *
     * @param columns
     * The columns
     */
    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    /**
     *
     * @return
     * The workItems
     */
    public List<WorkItem> getWorkItems() {
        return workItems;
    }

    /**
     *
     * @param workItems
     * The workItems
     */
    public void setWorkItems(List<WorkItem> workItems) {
        this.workItems = workItems;
    }

    @Override
    public String toString() {
        return "Work Items: " + workItems.size();
    }
    
    public String getWorkItemString(){
        String retString = ",";
        for (WorkItem workItem : workItems) {
               retString += "," + workItem.getId();
        }

        if (retString.length() == 0){
            return "";
        }

        return retString.substring(2);
    }
}
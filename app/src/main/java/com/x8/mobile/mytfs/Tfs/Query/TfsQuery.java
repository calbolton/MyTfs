package com.x8.mobile.mytfs.Tfs.Query;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TfsQuery {

    @SerializedName("query")
    @Expose
    private String query;

    /**
     *
     * @return
     * The query
     */
    public String getQuery() {
        return query;
    }

    /**
     *
     * @param query
     * The query
     */
    public void setQuery(String query) {
        this.query = query;
    }

    public static String getWorkItemLinksInIteration(String iteration){
        return "SELECT [System.Id]. [System.Title] FROM WorkItemLinks WHERE ( Source.[System.IterationPath] = '" + iteration + "' AND Source.[System.TeamProject] = @project AND Source.[System.WorkItemType] IN ('Product Backlog Item', 'Bug') AND (Source.[System.State] <> 'Removed')" + ") AND [System.Links.LinkType] = 'System.LinkTypes.Hierarchy-Forward' AND Target.[System.WorkItemType] <> ''";
    }

}
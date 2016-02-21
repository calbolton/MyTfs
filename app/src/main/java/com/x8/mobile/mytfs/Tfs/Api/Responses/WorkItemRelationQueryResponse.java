package com.x8.mobile.mytfs.Tfs.Api.Responses;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.x8.mobile.mytfs.Tfs.Models.FieldReference;
import com.x8.mobile.mytfs.Tfs.Models.WorkItem;
import com.x8.mobile.mytfs.Tfs.Models.WorkItemRelation;

public class WorkItemRelationQueryResponse {

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
    private List<FieldReference> columns = new ArrayList<>();
    @SerializedName("sortColumns")
    @Expose
    private List<SortColumn> sortColumns = new ArrayList<>();
    @SerializedName("workItemRelations")
    @Expose
    private List<WorkItemRelation> workItemRelations = new ArrayList<>();

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
    public List<FieldReference> getColumns() {
        return columns;
    }

    /**
     *
     * @param columns
     * The columns
     */
    public void setColumns(List<FieldReference> columns) {
        this.columns = columns;
    }

    /**
     *
     * @return
     * The sortColumns
     */
    public List<SortColumn> getSortColumns() {
        return sortColumns;
    }

    /**
     *
     * @param sortColumns
     * The sortColumns
     */
    public void setSortColumns(List<SortColumn> sortColumns) {
        this.sortColumns = sortColumns;
    }

    /**
     *
     * @return
     * The workItemRelations
     */
    public List<WorkItemRelation> getWorkItemRelations() {
        return workItemRelations;
    }

    /**
     *
     * @param workItemRelations
     * The workItemRelations
     */
    public void setWorkItemRelations(List<WorkItemRelation> workItemRelations) {
        this.workItemRelations = workItemRelations;
    }

    @Override
    public String toString() {
        return "Relations: " + workItemRelations.size();
    }

    public String getWorkItemString(){
        String retString = "";

        for (WorkItemRelation relation: workItemRelations) {
            retString += "," + relation.getTarget().getId();
        }

        if (retString.length() == 0){
            return "";
        }

        return retString.substring(1);
    }

    public ArrayList<WorkItem> getProductBacklogItems(List<WorkItem> workItems) throws Exception {
        ArrayList<WorkItem> productBacklogItems = new ArrayList<>();
        for (WorkItemRelation relation: workItemRelations) {
            if (relation.getSource() == null){
                // PBI
                productBacklogItems.add(getWorkItem(workItems, relation.getTarget().getId()));
            }
        }

        for (WorkItemRelation relation: workItemRelations) {
            if (relation.getSource() != null){
                WorkItem parentWorkItem = getWorkItem(productBacklogItems, relation.getSource().getId());

                if (parentWorkItem == null)
                    throw new Exception("No parent work item found for " + relation.getSource().getId() + " with parent id " + relation.getTarget().getId());

                WorkItem taskItem = getWorkItem(workItems, relation.getTarget().getId());
                parentWorkItem.addWorkItem(taskItem);
            }
        }

        return productBacklogItems;
    }

    private WorkItem getWorkItem(List<WorkItem> workItems, int id){
        for (WorkItem workItem : workItems) {
            if (workItem.getId() == id){
                return workItem;
            }
        }

        return null;
    }
}
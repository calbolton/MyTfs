package com.x8.mobile.mytfs.Tfs.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WorkItem {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("rev")
    @Expose
    private Integer rev;
    @SerializedName("fields")
    @Expose
    private Fields fields;
    @SerializedName("url")
    @Expose
    private String url;

    private ArrayList<WorkItem> workItems;

    public ArrayList<WorkItem> getWorkItems(){
        return workItems;
    }

    public void setWorkItems(ArrayList<WorkItem> workItems){
        this.workItems = workItems;
    }

    public void addWorkItem(WorkItem workItem) throws Exception {
        if (workItem == null){
            throw new Exception("null work item passed in");
        }

        if (this.workItems == null){
            this.workItems = new ArrayList<>();
        }
        this.workItems.add(workItem);
    }

    public boolean moveToNextState(){
        if (getFields().getSystemWorkItemType().equals(Fields.WIT_TASK)){
            if (getFields().getSystemState().equals(Fields.STATUS_TASK_TODO)){
                getFields().setSystemState(Fields.STATUS_TASK_INPROGRESS);
                return true;
            }else if (getFields().getSystemState().equals(Fields.STATUS_TASK_INPROGRESS)){
                getFields().setSystemState(Fields.STATUS_TASK_DONE);
                return true;
            }
        }

        return false;
    }

    public boolean moveToPreviousState(){
        if (getFields().getSystemWorkItemType().equals(Fields.WIT_TASK)){
            if (getFields().getSystemState().equals(Fields.STATUS_TASK_INPROGRESS)){
                getFields().setSystemState(Fields.STATUS_TASK_TODO);
                return true;
            }else if (getFields().getSystemState().equals(Fields.STATUS_TASK_DONE)){
                getFields().setSystemState(Fields.STATUS_TASK_INPROGRESS);
                return true;
            }
        }

        return false;
    }

    public boolean isToDo(){
        return getFields().getSystemState().equals(Fields.STATUS_TASK_TODO);
    }

    public boolean isInProgress(){
        return getFields().getSystemState().equals(Fields.STATUS_TASK_INPROGRESS);
    }

    public boolean isDone(){
        return getFields().getSystemState().equals(Fields.STATUS_TASK_DONE);
    }

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The rev
     */
    public Integer getRev() {
        return rev;
    }

    /**
     *
     * @param rev
     * The rev
     */
    public void setRev(Integer rev) {
        this.rev = rev;
    }

    /**
     *
     * @return
     * The fields
     */
    public Fields getFields() {
        return fields;
    }

    /**
     *
     * @param fields
     * The fields
     */
    public void setFields(Fields fields) {
        this.fields = fields;
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

    public HashMap<String, String> getChangedFields(){
        return fields.getChangedFields();
    }

    public String getTitle(){
        return fields.getSystemTitle();
    }

    public String getWorkItemType(){
        return fields.getSystemWorkItemType();
    }

    @Override
    public String toString() {
        String retString = "";

        retString += "\r\n" + getWorkItemType() + ": " + getTitle();

        if (workItems != null){
            for (WorkItem wi: workItems) {
                retString += wi.toString();
            }
        }

        return retString;
    }
}
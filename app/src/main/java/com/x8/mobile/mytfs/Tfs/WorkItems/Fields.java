package com.x8.mobile.mytfs.Tfs.WorkItems;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fields {

    @SerializedName("System.AreaPath")
    @Expose
    private String SystemAreaPath;
    @SerializedName("System.TeamProject")
    @Expose
    private String SystemTeamProject;
    @SerializedName("System.IterationPath")
    @Expose
    private String SystemIterationPath;
    @SerializedName("System.WorkItemType")
    @Expose
    private String SystemWorkItemType;
    @SerializedName("System.State")
    @Expose
    private String SystemState;
    @SerializedName("System.Reason")
    @Expose
    private String SystemReason;
    @SerializedName("System.CreatedDate")
    @Expose
    private String SystemCreatedDate;
    @SerializedName("System.CreatedBy")
    @Expose
    private String SystemCreatedBy;
    @SerializedName("System.ChangedDate")
    @Expose
    private String SystemChangedDate;
    @SerializedName("System.ChangedBy")
    @Expose
    private String SystemChangedBy;
    @SerializedName("System.Title")
    @Expose
    private String SystemTitle;
    @SerializedName("Microsoft.VSTS.Scheduling.RemainingWork")
    @Expose
    private Integer MicrosoftVSTSSchedulingRemainingWork;
    @SerializedName("Microsoft.VSTS.Common.BacklogPriority")
    @Expose
    private Integer MicrosoftVSTSCommonBacklogPriority;
    @SerializedName("Microsoft.VSTS.Common.Activity")
    @Expose
    private String MicrosoftVSTSCommonActivity;

    /**
     *
     * @return
     * The SystemAreaPath
     */
    public String getSystemAreaPath() {
        return SystemAreaPath;
    }

    /**
     *
     * @param SystemAreaPath
     * The System.AreaPath
     */
    public void setSystemAreaPath(String SystemAreaPath) {
        this.SystemAreaPath = SystemAreaPath;
    }

    /**
     *
     * @return
     * The SystemTeamProject
     */
    public String getSystemTeamProject() {
        return SystemTeamProject;
    }

    /**
     *
     * @param SystemTeamProject
     * The System.TeamProject
     */
    public void setSystemTeamProject(String SystemTeamProject) {
        this.SystemTeamProject = SystemTeamProject;
    }

    /**
     *
     * @return
     * The SystemIterationPath
     */
    public String getSystemIterationPath() {
        return SystemIterationPath;
    }

    /**
     *
     * @param SystemIterationPath
     * The System.IterationPath
     */
    public void setSystemIterationPath(String SystemIterationPath) {
        this.SystemIterationPath = SystemIterationPath;
    }

    /**
     *
     * @return
     * The SystemWorkItemType
     */
    public String getSystemWorkItemType() {
        return SystemWorkItemType;
    }

    /**
     *
     * @param SystemWorkItemType
     * The System.WorkItemType
     */
    public void setSystemWorkItemType(String SystemWorkItemType) {
        this.SystemWorkItemType = SystemWorkItemType;
    }

    /**
     *
     * @return
     * The SystemState
     */
    public String getSystemState() {
        return SystemState;
    }

    /**
     *
     * @param SystemState
     * The System.State
     */
    public void setSystemState(String SystemState) {
        this.SystemState = SystemState;
    }

    /**
     *
     * @return
     * The SystemReason
     */
    public String getSystemReason() {
        return SystemReason;
    }

    /**
     *
     * @param SystemReason
     * The System.Reason
     */
    public void setSystemReason(String SystemReason) {
        this.SystemReason = SystemReason;
    }

    /**
     *
     * @return
     * The SystemCreatedDate
     */
    public String getSystemCreatedDate() {
        return SystemCreatedDate;
    }

    /**
     *
     * @param SystemCreatedDate
     * The System.CreatedDate
     */
    public void setSystemCreatedDate(String SystemCreatedDate) {
        this.SystemCreatedDate = SystemCreatedDate;
    }

    /**
     *
     * @return
     * The SystemCreatedBy
     */
    public String getSystemCreatedBy() {
        return SystemCreatedBy;
    }

    /**
     *
     * @param SystemCreatedBy
     * The System.CreatedBy
     */
    public void setSystemCreatedBy(String SystemCreatedBy) {
        this.SystemCreatedBy = SystemCreatedBy;
    }

    /**
     *
     * @return
     * The SystemChangedDate
     */
    public String getSystemChangedDate() {
        return SystemChangedDate;
    }

    /**
     *
     * @param SystemChangedDate
     * The System.ChangedDate
     */
    public void setSystemChangedDate(String SystemChangedDate) {
        this.SystemChangedDate = SystemChangedDate;
    }

    /**
     *
     * @return
     * The SystemChangedBy
     */
    public String getSystemChangedBy() {
        return SystemChangedBy;
    }

    /**
     *
     * @param SystemChangedBy
     * The System.ChangedBy
     */
    public void setSystemChangedBy(String SystemChangedBy) {
        this.SystemChangedBy = SystemChangedBy;
    }

    /**
     *
     * @return
     * The SystemTitle
     */
    public String getSystemTitle() {
        return SystemTitle;
    }

    /**
     *
     * @param SystemTitle
     * The System.Title
     */
    public void setSystemTitle(String SystemTitle) {
        this.SystemTitle = SystemTitle;
    }

    /**
     *
     * @return
     * The MicrosoftVSTSSchedulingRemainingWork
     */
    public Integer getMicrosoftVSTSSchedulingRemainingWork() {
        return MicrosoftVSTSSchedulingRemainingWork;
    }

    /**
     *
     * @param MicrosoftVSTSSchedulingRemainingWork
     * The Microsoft.VSTS.Scheduling.RemainingWork
     */
    public void setMicrosoftVSTSSchedulingRemainingWork(Integer MicrosoftVSTSSchedulingRemainingWork) {
        this.MicrosoftVSTSSchedulingRemainingWork = MicrosoftVSTSSchedulingRemainingWork;
    }

    /**
     *
     * @return
     * The MicrosoftVSTSCommonBacklogPriority
     */
    public Integer getMicrosoftVSTSCommonBacklogPriority() {
        return MicrosoftVSTSCommonBacklogPriority;
    }

    /**
     *
     * @param MicrosoftVSTSCommonBacklogPriority
     * The Microsoft.VSTS.Common.BacklogPriority
     */
    public void setMicrosoftVSTSCommonBacklogPriority(Integer MicrosoftVSTSCommonBacklogPriority) {
        this.MicrosoftVSTSCommonBacklogPriority = MicrosoftVSTSCommonBacklogPriority;
    }

    /**
     *
     * @return
     * The MicrosoftVSTSCommonActivity
     */
    public String getMicrosoftVSTSCommonActivity() {
        return MicrosoftVSTSCommonActivity;
    }

    /**
     *
     * @param MicrosoftVSTSCommonActivity
     * The Microsoft.VSTS.Common.Activity
     */
    public void setMicrosoftVSTSCommonActivity(String MicrosoftVSTSCommonActivity) {
        this.MicrosoftVSTSCommonActivity = MicrosoftVSTSCommonActivity;
    }

}
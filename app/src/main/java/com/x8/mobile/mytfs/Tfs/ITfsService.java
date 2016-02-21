package com.x8.mobile.mytfs.Tfs;

import com.x8.mobile.mytfs.Tfs.Models.Iteration;
import com.x8.mobile.mytfs.Tfs.Query.QueryRelationResult;
import com.x8.mobile.mytfs.Tfs.Models.WorkItem;
import com.x8.mobile.mytfs.Tfs.Query.WorkItemQuery;
import com.x8.mobile.mytfs.Tfs.Query.WorkItemQueryResult;
import com.x8.mobile.mytfs.Tfs.WorkItems.WorkItemResult;

import java.util.ArrayList;

import retrofit.Callback;

public interface ITfsService {
    void getCurrentIterationAsync(Callback<Iteration> callback);


    void getWorkItemsAsync(String ids, Callback<WorkItemResult> callback);
    void getItemsInIterationAsync(WorkItemQuery query, Callback<WorkItemQueryResult> callback);
    void getItemsInIterationWithRelationsAsync(WorkItemQuery query, Callback<QueryRelationResult> callback);
    void getCurrentSprintProductBacklogItemHeirarchy(Callback<ArrayList<WorkItem>> callback);
    void updateWorkItem(WorkItem workItem, Callback<WorkItem> callback);
}
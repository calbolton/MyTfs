package com.x8.mobile.mytfs.Tfs;

import com.x8.mobile.mytfs.Tfs.Models.Iteration;
import com.x8.mobile.mytfs.Tfs.Models.WorkItem;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;

public interface ITfsService {
    void getCurrentIterationAsync(Callback<Iteration> callback);


    void getWorkItemsAsync(String ids, Callback<List<WorkItem>> callback);
    void getWorkItemsInIterationAsync(String iteration, Callback<ArrayList<WorkItem>> callback);
    void getWorkItemsInIterationWithRelationsAsync(String iteration, Callback<ArrayList<WorkItem>> callback);
    void getWorkItemsInCurrentIterationWithRelationsAsync(Callback<ArrayList<WorkItem>> callback);
    void updateWorkItem(WorkItem workItem, Callback<WorkItem> callback);
}
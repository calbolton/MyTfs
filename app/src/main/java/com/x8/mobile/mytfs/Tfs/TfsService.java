package com.x8.mobile.mytfs.Tfs;

import com.x8.mobile.mytfs.Tfs.Api.Responses.GetCurrentSprintResponse;
import com.x8.mobile.mytfs.Tfs.Models.Iteration;
import com.x8.mobile.mytfs.Tfs.Query.QueryRelationResult;
import com.x8.mobile.mytfs.Tfs.Query.WorkItemQuery;
import com.x8.mobile.mytfs.Tfs.Query.WorkItemQueryResult;
import com.x8.mobile.mytfs.Tfs.Requests.FieldUpdateRequest;
import com.x8.mobile.mytfs.Tfs.Models.WorkItem;
import com.x8.mobile.mytfs.Tfs.WorkItems.WorkItemResult;

import java.util.ArrayList;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by calbo_000 on 2/21/2016.
 */
public class TfsService implements ITfsService {

    private String _tfsUrl;
    private Retrofit _retrofit;

    public TfsService(String tfsUrl){
        _tfsUrl = tfsUrl;

        _retrofit = new Retrofit.Builder()
                .baseUrl(_tfsUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void updateWorkItem(WorkItem workItem){

    }

    @Override
    public void getCurrentIterationAsync(final Callback<Iteration> callback) {
        final ITfsApi tfsService = _retrofit.create(ITfsApi.class);

        Call<GetCurrentSprintResponse> currentSprintCall = tfsService.getCurrentIteration();

        currentSprintCall.enqueue(new Callback<GetCurrentSprintResponse>() {
            @Override
            public void onResponse(Response<GetCurrentSprintResponse> response, Retrofit retrofit) {
                if (!response.isSuccess()){
                    callback.onFailure(new Exception("Error getting current iteration"));
                    return;
                }

                GetCurrentSprintResponse result = response.body();

                callback.onResponse(Response.success(result.getCurrentSprint()), _retrofit);
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    @Override
    public void getWorkItemsAsync(String ids, Callback<WorkItemResult> callback) {
        final ITfsApi tfsService = _retrofit.create(ITfsApi.class);

        Call<WorkItemResult> call = tfsService.getWorkItems(ids);

        call.enqueue(callback);
    }

    @Override
    public void getItemsInIterationAsync(WorkItemQuery query, Callback<WorkItemQueryResult> callback) {

    }

    @Override
    public void getItemsInIterationWithRelationsAsync(WorkItemQuery query, Callback<QueryRelationResult> callback) {

        final ITfsApi tfsService = _retrofit.create(ITfsApi.class);

        Call<QueryRelationResult> workItemLinkscall = tfsService.getItemsInIterationWithRelations(query);

        workItemLinkscall.enqueue(callback);
    }

    @Override
    public void getCurrentSprintProductBacklogItemHeirarchy(final Callback<ArrayList<WorkItem>> callback) {

        getCurrentIterationAsync(new Callback<Iteration>() {
            @Override
            public void onResponse(Response<Iteration> response, Retrofit retrofit) {
                if (!response.isSuccess()) {
                    callback.onFailure(new Exception("Error getting current sprint"));
                    return;
                }

                Iteration currentSprint = response.body();

                WorkItemQuery query = new WorkItemQuery();
                query.setQuery(WorkItemQuery.getWorkItemLinksInIteration(currentSprint.getPath()));

                getItemsInIterationWithRelationsAsync(query, new Callback<QueryRelationResult>() {
                    @Override
                    public void onResponse(Response<QueryRelationResult> response, Retrofit retrofit) {
                        if (!response.isSuccess()) {
                            callback.onFailure(new Exception("Error getting work item links"));
                            return;
                        }

                        final QueryRelationResult queryResult = response.body();

                        String workItemString = queryResult.getWorkItemString();

                        getWorkItemsAsync(workItemString, new Callback<WorkItemResult>() {
                            @Override
                            public void onResponse(Response<WorkItemResult> response, Retrofit retrofit) {
                                if (!response.isSuccess()) {
                                    callback.onFailure(new Exception("Error getting work items in iteration"));
                                    return;
                                }

                                try {
                                    WorkItemResult result = response.body();

                                    ArrayList<WorkItem> productbacklogItems = queryResult.getProductBacklogItems(result.getValue());
                                    callback.onResponse(Response.success(productbacklogItems), _retrofit);
                                } catch (Exception ex) {
                                    callback.onFailure(ex);
                                }

                            }

                            @Override
                            public void onFailure(Throwable t) {
                                callback.onFailure(t);
                            }
                        });

                    }

                    @Override
                    public void onFailure(Throwable t) {
                        callback.onFailure(t);
                    }
                });
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    @Override
    public void updateWorkItem(WorkItem workItem, Callback<WorkItem> callback) {
        int workItemId = workItem.getId();

        ArrayList<FieldUpdateRequest> fieldUpdateRequests = new ArrayList<>();

        for(Map.Entry<String, String> entry: workItem.getChangedFields().entrySet()) {
            FieldUpdateRequest request = new FieldUpdateRequest(FieldUpdateRequest.OP_ADD, entry.getKey(), entry.getValue());
            fieldUpdateRequests.add(request);
        }

        ITfsApi tfsApi = _retrofit.create(ITfsApi.class);

        Call<WorkItem> call = tfsApi.updateWorkItem(workItemId, fieldUpdateRequests);

        call.enqueue(callback);
    }


}

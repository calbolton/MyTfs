package com.x8.mobile.mytfs.Tfs;

import com.x8.mobile.mytfs.Tfs.Api.ITfsApi;
import com.x8.mobile.mytfs.Tfs.Api.Requests.WorkItemQueryRequest;
import com.x8.mobile.mytfs.Tfs.Api.Responses.GetCurrentSprintResponse;
import com.x8.mobile.mytfs.Tfs.Api.Responses.GetWorkItemsResponse;
import com.x8.mobile.mytfs.Tfs.Api.Responses.WorkItemRelationQueryResponse;
import com.x8.mobile.mytfs.Tfs.Models.Iteration;
import com.x8.mobile.mytfs.Tfs.Api.Requests.FieldUpdateRequest;
import com.x8.mobile.mytfs.Tfs.Models.WorkItem;
import java.util.ArrayList;
import java.util.List;
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
    public void getWorkItemsAsync(String ids, final Callback<List<WorkItem>> callback) {
        final ITfsApi tfsService = _retrofit.create(ITfsApi.class);

        Call<GetWorkItemsResponse> call = tfsService.getWorkItems(ids);

        call.enqueue(new Callback<GetWorkItemsResponse>() {
            @Override
            public void onResponse(Response<GetWorkItemsResponse> response, Retrofit retrofit) {
                if (!response.isSuccess()){
                    callback.onFailure(new Exception("Error getting work items"));
                    return;
                }

                callback.onResponse(Response.success(response.body().getValue()), _retrofit);
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    @Override
    public void getWorkItemsInIterationAsync(String iteration, Callback<ArrayList<WorkItem>> callback) {

    }

    @Override
    public void getWorkItemsInIterationWithRelationsAsync(String iteration, final Callback<ArrayList<WorkItem>> callback) {

        final ITfsApi tfsService = _retrofit.create(ITfsApi.class);

        WorkItemQueryRequest query = new WorkItemQueryRequest();
        query.setQuery(WorkItemQueryRequest.getWorkItemLinksInIteration(iteration));

        Call<WorkItemRelationQueryResponse> workItemLinkscall = tfsService.queryWorkItemRelations(query);



        workItemLinkscall.enqueue(new Callback<WorkItemRelationQueryResponse>() {
            @Override
            public void onResponse(Response<WorkItemRelationQueryResponse> response, Retrofit retrofit) {
                if (!response.isSuccess()) {
                    callback.onFailure(new Exception("Error getting work item links"));
                    return;
                }

                final WorkItemRelationQueryResponse queryResult = response.body();

                String workItemString = queryResult.getWorkItemString();

                getWorkItemsAsync(workItemString, new Callback<List<WorkItem>>() {
                    @Override
                    public void onResponse(Response<List<WorkItem>> response, Retrofit retrofit) {
                        if (!response.isSuccess()) {
                            callback.onFailure(new Exception("Error getting work items in iteration"));
                            return;
                        }

                        try {
                            List<WorkItem> result = response.body();

                            ArrayList<WorkItem> productbacklogItems = queryResult.getProductBacklogItems(result);
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
    public void getWorkItemsInCurrentIterationWithRelationsAsync(final Callback<ArrayList<WorkItem>> callback) {

        getCurrentIterationAsync(new Callback<Iteration>() {
            @Override
            public void onResponse(Response<Iteration> response, Retrofit retrofit) {
                if (!response.isSuccess()) {
                    callback.onFailure(new Exception("Error getting current sprint"));
                    return;
                }

                Iteration currentSprint = response.body();

                getWorkItemsInIterationWithRelationsAsync(currentSprint.getPath(), callback);
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

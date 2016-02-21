package com.x8.mobile.mytfs.Tfs;

import com.x8.mobile.mytfs.Tfs.Api.Responses.GetCurrentSprintResponse;
import com.x8.mobile.mytfs.Tfs.Query.QueryRelationResult;
import com.x8.mobile.mytfs.Tfs.Query.WorkItemQuery;
import com.x8.mobile.mytfs.Tfs.Query.WorkItemQueryResult;
import com.x8.mobile.mytfs.Tfs.Requests.FieldUpdateRequest;
import com.x8.mobile.mytfs.Tfs.Models.WorkItem;
import com.x8.mobile.mytfs.Tfs.WorkItems.WorkItemResult;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.PATCH;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

public interface ITfsApi {
    @Headers({
            "Accept: application/json",
            "Authorization: Basic Q2FsQm9sdG9uOkNAMTF1bUIwbHQwbg=="
    })
    @GET("DefaultCollection/Life/_apis/work/teamsettings/iterations?$timeframe=current&api-version=v2.0-preview")
    Call<GetCurrentSprintResponse> getCurrentIteration();

    @Headers({
            "Accept: application/json",
            "Authorization: Basic Q2FsQm9sdG9uOkNAMTF1bUIwbHQwbg=="
    })
    @POST("DefaultCollection/Life/_apis/wit/wiql?api-version=1.0")
    Call<WorkItemQueryResult> queryWorkItems(@Body WorkItemQuery query);



    @Headers({
            "Accept: application/json",
            "Authorization: Basic Q2FsQm9sdG9uOkNAMTF1bUIwbHQwbg=="
    })
    @GET("defaultcollection/_apis/wit/workitems?api-version=1.0")
    Call<WorkItemResult> getWorkItems(@Query("ids") String ids);

    @Headers({
            "Accept: application/json",
            "Authorization: Basic Q2FsQm9sdG9uOkNAMTF1bUIwbHQwbg=="
    })
    @POST("DefaultCollection/Life/_apis/wit/wiql?api-version=1.0")
    Call<WorkItemQueryResult> getItemsInIteration(@Body WorkItemQuery query);

    @Headers({
            "Accept: application/json",
            "Authorization: Basic Q2FsQm9sdG9uOkNAMTF1bUIwbHQwbg=="
    })
    @POST("DefaultCollection/Life/_apis/wit/wiql?api-version=1.0")
    Call<QueryRelationResult> getItemsInIterationWithRelations(@Body WorkItemQuery query);

    @Headers({
            "Accept: application/json",
            "Authorization: Basic Q2FsQm9sdG9uOkNAMTF1bUIwbHQwbg==",
            "Content-Type: application/json-patch+json"
    })
    @PATCH("DefaultCollection/_apis/wit/workitems/{workItemId}?api-version=1.0")
    Call<WorkItem> updateWorkItem(@Path("workItemId") int workItemId, @Body ArrayList<FieldUpdateRequest> fieldsToUpdate);
}

package com.x8.mobile.mytfs.Tfs.Api;

import com.x8.mobile.mytfs.Tfs.Api.Requests.WorkItemQueryRequest;
import com.x8.mobile.mytfs.Tfs.Api.Responses.GetCurrentSprintResponse;
import com.x8.mobile.mytfs.Tfs.Api.Responses.GetWorkItemsResponse;
import com.x8.mobile.mytfs.Tfs.Api.Responses.WorkItemQueryResponse;
import com.x8.mobile.mytfs.Tfs.Api.Responses.WorkItemRelationQueryResponse;
import com.x8.mobile.mytfs.Tfs.Api.Requests.FieldUpdateRequest;
import com.x8.mobile.mytfs.Tfs.Models.WorkItem;

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
    Call<WorkItemQueryResponse> queryWorkItems(@Body WorkItemQueryRequest query);

    @Headers({
            "Accept: application/json",
            "Authorization: Basic Q2FsQm9sdG9uOkNAMTF1bUIwbHQwbg=="
    })
    @POST("DefaultCollection/Life/_apis/wit/wiql?api-version=1.0")
    Call<WorkItemRelationQueryResponse> queryWorkItemRelations(@Body WorkItemQueryRequest query);

    @Headers({
            "Accept: application/json",
            "Authorization: Basic Q2FsQm9sdG9uOkNAMTF1bUIwbHQwbg=="
    })
    @GET("defaultcollection/_apis/wit/workitems?api-version=1.0")
    Call<GetWorkItemsResponse> getWorkItems(@Query("ids") String ids);

    @Headers({
            "Accept: application/json",
            "Authorization: Basic Q2FsQm9sdG9uOkNAMTF1bUIwbHQwbg==",
            "Content-Type: application/json-patch+json"
    })
    @PATCH("DefaultCollection/_apis/wit/workitems/{workItemId}?api-version=1.0")
    Call<WorkItem> updateWorkItem(@Path("workItemId") int workItemId, @Body ArrayList<FieldUpdateRequest> fieldsToUpdate);

}

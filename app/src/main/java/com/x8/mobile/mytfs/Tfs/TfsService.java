package com.x8.mobile.mytfs.Tfs;

import com.x8.mobile.mytfs.Tfs.CurrentSprint.CurrentSprint;
import com.x8.mobile.mytfs.Tfs.Query.TfsQuery;
import com.x8.mobile.mytfs.Tfs.Query.TfsQueryResult;
import com.x8.mobile.mytfs.Tfs.Query.QueryRelationResult;
import com.x8.mobile.mytfs.Tfs.WorkItems.WorkItemResult;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Query;

public interface TfsService {
    @Headers({
            "Accept: application/json",
            "Authorization: Basic Q2FsQm9sdG9uOkNAMTF1bUIwbHQwbg=="
    })
    @GET("DefaultCollection/Life/_apis/work/teamsettings/iterations?$timeframe=current&api-version=v2.0-preview")
    Call<CurrentSprint> getCurrentIteration();

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
    Call<TfsQueryResult> getItemsInIteration(@Body TfsQuery query);

    @Headers({
            "Accept: application/json",
            "Authorization: Basic Q2FsQm9sdG9uOkNAMTF1bUIwbHQwbg=="
    })
    @POST("DefaultCollection/Life/_apis/wit/wiql?api-version=1.0")
    Call<QueryRelationResult> getItemsInIterationWithRelations(@Body TfsQuery query);
}
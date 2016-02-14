package com.x8.mobile.mytfs;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp.StethoInterceptor;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.okhttp.OkHttpClient;
import com.x8.mobile.mytfs.Tfs.CurrentSprint.CurrentSprint;
import com.x8.mobile.mytfs.Tfs.Query.TfsQuery;
import com.x8.mobile.mytfs.Tfs.Query.QueryRelationResult;
import com.x8.mobile.mytfs.Tfs.TfsService;
import com.x8.mobile.mytfs.Tfs.WorkItems.WorkItem;
import com.x8.mobile.mytfs.Tfs.WorkItems.WorkItemResult;
import java.util.ArrayList;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    private GoogleApiClient client;
    private static final String TFS_ONLINE_BASE_URL = "https://calbolton.visualstudio.com/";
    private ArrayList<WorkItem> productbacklogItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        Stetho.initializeWithDefaults(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return id == R.id.action_settings ||super.onOptionsItemSelected(item);
    }

    public void verifyEmail(View view){
        try {
            loadProductbacklogItems();
        }catch (Exception ex){
            EditText etResult = (EditText) findViewById(R.id.etResult);
            etResult.setText(ex.getMessage());
        }
    }

    private void addMessage(String message){
        EditText etResult = (EditText) findViewById(R.id.etResult);
        String text = etResult.getText().toString();
        text += "\r\n" + message;
        etResult.setText(text);
    }

    private void loadProductbacklogItems(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TFS_ONLINE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final TfsService tfsService = retrofit.create(TfsService.class);

        Call<CurrentSprint> currentSprintCall = tfsService.getCurrentIteration();

        currentSprintCall.enqueue(new Callback<CurrentSprint>() {
            @Override
            public void onResponse(Response<CurrentSprint> response, Retrofit retrofit) {
               if (!response.isSuccess()){
                    addMessage("Error getting current sprint");
                    return;
                }

                CurrentSprint currentSprint = response.body();

                TfsQuery query = new TfsQuery();
                query.setQuery(TfsQuery.getWorkItemLinksInIteration(currentSprint.getCurrentSprint().getPath()));

                Call<QueryRelationResult> workItemLinkscall = tfsService.getItemsInIterationWithRelations(query);

                workItemLinkscall.enqueue(new Callback<QueryRelationResult>() {
                    @Override
                    public void onResponse(Response<QueryRelationResult> response, Retrofit retrofit) {

                        if (!response.isSuccess()) {
                            addMessage("Error getting work item links");
                            return;
                        }

                        final QueryRelationResult queryResult = response.body();

                        String workItemString = queryResult.getWorkItemString();

                        Call<WorkItemResult> call = tfsService.getWorkItems(workItemString);

                        call.enqueue(new Callback<WorkItemResult>() {
                            @Override
                            public void onResponse(Response<WorkItemResult> response, Retrofit retrofit) {
                                 if (!response.isSuccess()) {
                                     addMessage("Error geeting work items in iteration");
                                    return;
                                }

                                try {
                                    WorkItemResult result = response.body();

                                    productbacklogItems = queryResult.getProductBacklogItems(result.getValue());

                                    String text = "";
                                    if (productbacklogItems != null) {
                                        for (WorkItem wi : productbacklogItems) {
                                            text += wi.toString();
                                        }
                                    }

                                    addMessage(text);
                                } catch (Exception ex) {
                                    addMessage("Error processing backlog items");
                                }

                            }

                            @Override
                            public void onFailure(Throwable t) {
                                EditText etResult = (EditText) findViewById(R.id.etResult);
                                String text = "Failure: " + t.getMessage();
                                etResult.setText(text);
                            }
                        });

                    }

                    @Override
                    public void onFailure(Throwable t) {
                        EditText etResult = (EditText) findViewById(R.id.etResult);
                        String text = "Failure: " + t.getMessage();
                        etResult.setText(text);
                    }
                });
            }

            @Override
            public void onFailure(Throwable t) {
                EditText etResult = (EditText) findViewById(R.id.etResult);
                String text = "Failure: " + t.getMessage();
                etResult.setText(text);
            }
        });
    }
}

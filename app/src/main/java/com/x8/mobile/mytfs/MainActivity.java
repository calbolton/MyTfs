package com.x8.mobile.mytfs;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.facebook.stetho.Stetho;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.x8.mobile.mytfs.Tfs.ITfsService;
import com.x8.mobile.mytfs.Tfs.TfsService;
import com.x8.mobile.mytfs.Tfs.Models.WorkItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    private GoogleApiClient client;
    private static final String TFS_ONLINE_BASE_URL = "https://calbolton.visualstudio.com/";
    private ArrayList<WorkItem> productbacklogItems;
    private ITfsService _tfsService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        Stetho.initializeWithDefaults(this);

        _tfsService = new TfsService(TFS_ONLINE_BASE_URL);
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
        addMessage("Loading product backlog items");
        _tfsService.getCurrentSprintProductBacklogItemHeirarchy(new Callback<ArrayList<WorkItem>>() {
            @Override
            public void onResponse(Response<ArrayList<WorkItem>> response, Retrofit retrofit) {
                addMessage("Response");
                if (!response.isSuccess()){
                    addMessage("Error getting current sprint");
                    return;
                }

                ArrayList<WorkItem> productbacklogItems = response.body();

                WorkItem first = productbacklogItems.get(0);

                addMessage("WorkItem: " + first.getId());
                addMessage("State: " + first.getFields().getSystemState());

                first.getFields().setSystemState("New");

                _tfsService.updateWorkItem(first, new Callback<WorkItem>() {
                    @Override
                    public void onResponse(Response<WorkItem> response, Retrofit retrofit) {
                        if (!response.isSuccess()){
                            //Try to get response body
                            BufferedReader reader = null;
                            StringBuilder sb = new StringBuilder();
                            try {

                                reader = new BufferedReader(new InputStreamReader(response.errorBody().byteStream()));

                                String line;

                                try {
                                    while ((line = reader.readLine()) != null) {
                                        sb.append(line);
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                            String result = sb.toString();


                            addMessage("Error updating work item:" + result);
                            return;
                        }

                        WorkItem wi = response.body();

                        addMessage("WorkItem: " + wi.getId());
                        addMessage("State: " + wi.getFields().getSystemState());
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        EditText etResult = (EditText) findViewById(R.id.etResult);
                        String text = "Failure: " + t.getMessage();
                        addMessage(text);
                    }
                });

//                        String text = "";
//                if (productbacklogItems != null) {
//                    for (WorkItem wi : productbacklogItems) {
//                        text += wi.toString();
//                    }
//                }

//                addMessage(text);
            }

            @Override
            public void onFailure(Throwable t) {
                EditText etResult = (EditText) findViewById(R.id.etResult);
                String text = "Failure: " + t.getMessage();
                addMessage(text);
            }
        });
    }
}

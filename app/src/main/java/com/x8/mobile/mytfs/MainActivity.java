package com.x8.mobile.mytfs;
import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.facebook.stetho.Stetho;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.x8.mobile.mytfs.Tfs.ITfsService;
import com.x8.mobile.mytfs.Tfs.Models.Fields;
import com.x8.mobile.mytfs.Tfs.TfsService;
import com.x8.mobile.mytfs.Tfs.Models.WorkItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends Activity {

    private GoogleApiClient client;
    private static final String TFS_ONLINE_BASE_URL = "https://calbolton.visualstudio.com/";
    private ArrayList<WorkItem> _productbacklogItems;
    private ITfsService _tfsService;
    ArrayList<WorkItem> _todoItems=new ArrayList<WorkItem>();
    ArrayList<WorkItem> _inProgressItems=new ArrayList<WorkItem>();
    ArrayList<WorkItem> _doneItems=new ArrayList<WorkItem>();
    RowAdapter _todoAdapter;
    RowAdapter _inProgressAdapter;
    RowAdapter _doneAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        Stetho.initializeWithDefaults(this);

        _tfsService = new TfsService(TFS_ONLINE_BASE_URL);

        setupAdapters();
    }

    private void setupAdapters(){

        EditText etResult = (EditText) findViewById(R.id.etResult);
        ViewGroup vg = (ViewGroup)findViewById(R.id.lin_board);
        WorkItemChangedEventListener listener = new WorkItemChangedEventListener(vg, etResult, _tfsService);

        // InProgress
        ListView ls_inProgress = (ListView) findViewById( R.id.ls_inProgress);
        _inProgressAdapter = new RowAdapter(_inProgressItems, this);
        _inProgressAdapter.addListener(listener);
        ls_inProgress.setAdapter(_inProgressAdapter);

        // To do
        ListView ls_todo = (ListView) findViewById( R.id.ls_todo);
        _todoAdapter = new RowAdapter(_todoItems, this);
        _todoAdapter.addListener(listener);
        ls_todo.setAdapter(_todoAdapter);

        // Done
        ListView ls_done = (ListView) findViewById( R.id.ls_done);
        _doneAdapter = new RowAdapter(_doneItems, this);
        _doneAdapter.addListener(listener);
        ls_done.setAdapter(_doneAdapter);
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
            _todoItems.clear();
            _inProgressItems.clear();
            _doneItems.clear();
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

    private void setupBoard(ArrayList<WorkItem> workItems){
        for (WorkItem workItem: workItems) {
            addMessage(workItem.getFields().getSystemWorkItemType() + ":" + Fields.WIT_PRODUCTBACKLOGITEM);
            if (workItem.getFields().getSystemWorkItemType().equals(Fields.WIT_PRODUCTBACKLOGITEM)){
                addMessage(workItem.getWorkItems().size() + "");
                setupBoardWorkItems(workItem.getWorkItems());
            }
        }
    }

    private void setupBoardWorkItems(ArrayList<WorkItem> workItems){
        for (WorkItem workItem: workItems) {
            addMessage(workItem.getFields().getSystemWorkItemType());
            if (workItem.getFields().getSystemWorkItemType().equals(Fields.WIT_TASK)){
                if (workItem.getFields().getSystemState().equals(Fields.STATUS_TASK_TODO)){
                    _todoItems.add(workItem);
                    _todoAdapter.notifyDataSetChanged();
                }else if (workItem.getFields().getSystemState().equals(Fields.STATUS_TASK_INPROGRESS)){
                    _inProgressItems.add(workItem);
                    _inProgressAdapter.notifyDataSetChanged();
                }else if (workItem.getFields().getSystemState().equals(Fields.STATUS_TASK_DONE)){
                    _doneItems.add(workItem);
                    _doneAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    private void loadProductbacklogItems(){
        addMessage("Loading product backlog items");
        _tfsService.getWorkItemsInCurrentIterationWithRelationsAsync(new Callback<ArrayList<WorkItem>>() {
            @Override
            public void onResponse(Response<ArrayList<WorkItem>> response, Retrofit retrofit) {
                addMessage("Response");
                if (!response.isSuccess()) {
                    addMessage("Error getting current sprint");
                    return;
                }

                ArrayList<WorkItem> productbacklogItems = response.body();

                setupBoard(productbacklogItems);

//                WorkItem first = productbacklogItems.get(0);
//
//                addMessage("WorkItem: " + first.getId());
//                addMessage("State: " + first.getFields().getSystemState());
//
//                first.getFields().setSystemState("New");
//
//                _tfsService.updateWorkItem(first, new Callback<WorkItem>() {
//                    @Override
//                    public void onResponse(Response<WorkItem> response, Retrofit retrofit) {
//                        if (!response.isSuccess()){
//                            //Try to get response body
//                            BufferedReader reader = null;
//                            StringBuilder sb = new StringBuilder();
//                            try {
//
//                                reader = new BufferedReader(new InputStreamReader(response.errorBody().byteStream()));
//
//                                String line;
//
//                                try {
//                                    while ((line = reader.readLine()) != null) {
//                                        sb.append(line);
//                                    }
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//
//
//                            String result = sb.toString();
//
//
//                            addMessage("Error updating work item:" + result);
//                            return;
//                        }
//
//                        WorkItem wi = response.body();
//
//                        addMessage("WorkItem: " + wi.getId());
//                        addMessage("State: " + wi.getFields().getSystemState());
//                    }
//
//                    @Override
//                    public void onFailure(Throwable t) {
//                        EditText etResult = (EditText) findViewById(R.id.etResult);
//                        String text = "Failure: " + t.getMessage();
//                        addMessage(text);
//                    }
//                });

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
//                EditText etResult = (EditText) findViewById(R.id.etResult);
//                String text = "Failure: " + t.getMessage();
//                addMessage(text);
            }
        });
    }
}

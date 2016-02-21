package com.x8.mobile.mytfs;

import android.view.ViewGroup;
import android.widget.EditText;

import com.x8.mobile.mytfs.Tfs.ITfsService;
import com.x8.mobile.mytfs.Tfs.Models.WorkItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.EventObject;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class WorkItemChangedEventListener implements  IWorkItemChangedEventListener {

    private ViewGroup _viewGroup;
    private ITfsService _tfsService;
    private EditText _text;

    public WorkItemChangedEventListener(ViewGroup viewGroup, EditText text, ITfsService tfsService){
        _viewGroup = viewGroup;
        _tfsService = tfsService;
        _text = text;
    }

    @Override
    public void workItemChanged(EventObject e) {
        WorkItemChangedEvent event = (WorkItemChangedEvent)e;

        WorkItem wi = event.getWorkItem();

        _text.setText(wi.getTitle());

        _tfsService.updateWorkItem(wi, new Callback<WorkItem>() {
            @Override
            public void onResponse(Response<WorkItem> response, Retrofit retrofit) {
                if (!response.isSuccess()) {
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

                addMessage("UPDATED SUCCESSFULLY");
                addMessage("WorkItem: " + wi.getId());
                addMessage("State: " + wi.getFields().getSystemState());
            }

            @Override
            public void onFailure(Throwable t) {
                String text = "Failure: " + t.getMessage();
                addMessage(text);
            }
        });


        _viewGroup.invalidate();
    }

    private void addMessage(String message){
        String text = _text.getText().toString();
        text += "\r\n" + message;
        _text.setText(text);
    }
}


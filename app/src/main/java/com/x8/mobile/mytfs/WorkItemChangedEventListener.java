package com.x8.mobile.mytfs;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.x8.mobile.mytfs.Tfs.ITfsService;
import com.x8.mobile.mytfs.Tfs.Models.Fields;
import com.x8.mobile.mytfs.Tfs.Models.WorkItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.concurrent.Callable;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class WorkItemChangedEventListener implements  IWorkItemChangedEventListener {

    private ITfsService _tfsService;
    private EditText _text;
    private ListView _todoView;
    private ListView _inProgressView;
    private ListView _doneView;
    private ArrayList<WorkItem> _todoList;
    private ArrayList<WorkItem> _inProgressList;
    private ArrayList<WorkItem> _doneList;
    private ViewGroup _viewGroup;

    public WorkItemChangedEventListener(ListView todoView, ListView inProgressView, ListView doneView, ArrayList<WorkItem> todoList, ArrayList<WorkItem> inProgressList, ArrayList<WorkItem> doneList, EditText text, ITfsService tfsService){
        _todoView = todoView;
        _inProgressView = inProgressView;
        _doneView = doneView;
        _todoList = todoList;
        _inProgressList = inProgressList;
        _doneList = doneList;
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

//                if (wi.getFields().getSystemState().equals(Fields.STATUS_TASK_TODO)){
//                    _inProgressList.remove(wi);
//                    _todoList.add(wi);
//                    ((BaseAdapter)_todoView.getAdapter()).notifyDataSetChanged();
//                    ((BaseAdapter)_inProgressView.getAdapter()).notifyDataSetChanged();
//                }else if(wi.getFields().getSystemState().equals(Fields.STATUS_TASK_INPROGRESS)){
//                    for (WorkItem listWI: _todoList) {
//                        if (wi.getId().equals(listWI.getId())){
//                            _todoList.remove(listWI);
//                            _inProgressList.add(listWI);
//                            ((BaseAdapter)_todoView.getAdapter()).notifyDataSetChanged();
//                            ((BaseAdapter)_inProgressView.getAdapter()).notifyDataSetChanged();
//                            return;
//                        }
//                    }
//                    for (WorkItem listWI: _doneList) {
//                        if (wi.getId().equals(listWI.getId())){
//                            _doneList.remove(listWI);
//                            _inProgressList.add(listWI);
//
//                            ((BaseAdapter)_doneView.getAdapter()).notifyDataSetChanged();
//                            ((BaseAdapter)_inProgressView.getAdapter()).notifyDataSetChanged();
//                            return;
//                        }
//                    }
//                }else if(wi.getFields().getSystemState().equals(Fields.STATUS_TASK_DONE)){
//                    _inProgressList.remove(wi);
//                    _doneList.add(wi);
//
//                    ((BaseAdapter)_doneView.getAdapter()).notifyDataSetChanged();
//                    ((BaseAdapter)_inProgressView.getAdapter()).notifyDataSetChanged();
//                }

                for (WorkItem listWI: _todoList) {
                    if (wi.getId().equals(listWI.getId())){
                        _todoList.remove(listWI);
                        _inProgressList.add(listWI);
                        ((BaseAdapter)_todoView.getAdapter()).notifyDataSetChanged();
                        ((BaseAdapter)_inProgressView.getAdapter()).notifyDataSetChanged();
                        return;
                    }
                }
                for (WorkItem listWI: _inProgressList) {
                    if (wi.getId().equals(listWI.getId())){
                        _inProgressList.remove(listWI);
                        ((BaseAdapter)_inProgressView.getAdapter()).notifyDataSetChanged();
                        if (wi.getFields().getSystemState().equals(Fields.STATUS_TASK_TODO)){
                            _todoList.add(listWI);
                            ((BaseAdapter)_todoView.getAdapter()).notifyDataSetChanged();
                        }else{
                            _doneList.add(listWI);
                            ((BaseAdapter)_doneView.getAdapter()).notifyDataSetChanged();
                        }
                        return;
                    }
                }
                for (WorkItem listWI: _doneList) {
                    if (wi.getId().equals(listWI.getId())){
                        _doneList.remove(listWI);
                        _inProgressList.add(listWI);

                        ((BaseAdapter)_doneView.getAdapter()).notifyDataSetChanged();
                        ((BaseAdapter)_inProgressView.getAdapter()).notifyDataSetChanged();
                        return;
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                String text = "Failure: " + t.getMessage();
                addMessage(text);
            }
        });
    }

    private void addMessage(String message){
        String text = _text.getText().toString();
        text += "\r\n" + message;
        _text.setText(text);
    }
}


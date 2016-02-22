package com.x8.mobile.mytfs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.x8.mobile.mytfs.Tfs.Models.Fields;
import com.x8.mobile.mytfs.Tfs.Models.WorkItem;

import java.util.ArrayList;

public class RowAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<WorkItem> list = new ArrayList<WorkItem>();
    private Context context;
    private WorkItemChangedEventSource _workItemChangedEventSource;


    public RowAdapter(ArrayList<WorkItem> list, Context context) {
        this.list = list;
        this.context = context;
        _workItemChangedEventSource = new WorkItemChangedEventSource();
    }

    public void addListener(WorkItemChangedEventListener eventListener){
        _workItemChangedEventSource.addEventListener(eventListener);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
//        return list.get(pos).getId();
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.rowlayout, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.list_item_string);
        WorkItem wi = list.get(position);
        listItemText.setText(wi.getId() + ":" + wi.getTitle());

        //Handle buttons and add onClickListeners
        Button btn_NextState = (Button)view.findViewById(R.id.btn_nextState);
        Button btn_PreviousState = (Button)view.findViewById(R.id.btn_previousState);

        if (wi.isToDo()){
            btn_PreviousState.setVisibility(Button.INVISIBLE);
            btn_NextState.setVisibility(Button.VISIBLE);
            btn_NextState.setText("In Progress ->");
        }else if (wi.isInProgress()){
            btn_PreviousState.setVisibility(Button.VISIBLE);
            btn_NextState.setVisibility(Button.VISIBLE);
            btn_NextState.setText("Done ->");
            btn_PreviousState.setText("<- To Do");
        }else if (wi.isDone()){
            btn_PreviousState.setVisibility(Button.VISIBLE);
            btn_NextState.setVisibility(Button.INVISIBLE);
            btn_PreviousState.setText("<- In Progress");
        }

        View.OnClickListener nextListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkItem wi = list.get(position);

                if (wi.moveToNextState()){
                    _workItemChangedEventSource.fireEvent(wi);
                    notifyDataSetChanged();
                }
            }
        };

        View.OnClickListener previousListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkItem wi = list.get(position);

                if (wi.moveToPreviousState()){
                    _workItemChangedEventSource.fireEvent(wi);
                    notifyDataSetChanged();
                }
            }
        };

        btn_NextState.setOnClickListener(nextListener);
        btn_PreviousState.setOnClickListener(previousListener);

//        btn_NextState.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                WorkItem wi = list.get(position);
//
//                if (wi.moveToNextState()){
//                    _workItemChangedEventSource.fireEvent(wi);
//                    notifyDataSetChanged();
//                }
//            }
//        });

        return view;
    }
}
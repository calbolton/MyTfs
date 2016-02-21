package com.x8.mobile.mytfs;

import com.x8.mobile.mytfs.Tfs.Models.WorkItem;

public class WorkItemChangedEvent extends java.util.EventObject {

    private WorkItem _workItem;

    public WorkItemChangedEvent(Object source, WorkItem workItem) {
        super(source);
        _workItem = workItem;
    }

    public WorkItem getWorkItem(){
        return _workItem;
    }
}

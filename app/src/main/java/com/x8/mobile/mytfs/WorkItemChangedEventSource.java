package com.x8.mobile.mytfs;

import com.x8.mobile.mytfs.Tfs.Models.WorkItem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WorkItemChangedEventSource {
    private List _listeners = new ArrayList();
    public synchronized void addEventListener(WorkItemChangedEventListener listener)  {
        _listeners.add(listener);
    }
    public synchronized void removeEventListener(WorkItemChangedEventListener listener)   {
        _listeners.remove(listener);
    }

    // call this method whenever you want to notify
    //the event listeners of the particular event
    public synchronized void fireEvent(WorkItem workItem) {
        WorkItemChangedEvent event = new WorkItemChangedEvent(this, workItem);
        Iterator i = _listeners.iterator();
        while(i.hasNext())  {
            ((WorkItemChangedEventListener) i.next()).workItemChanged(event);
        }
    }
}

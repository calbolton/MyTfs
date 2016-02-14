package com.x8.mobile.mytfs.Tfs.CurrentSprint;

import java.util.List;

import java.util.ArrayList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentSprint {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("value")
    @Expose
    private List<Value> value = new ArrayList<>();

    /**
     *
     * @return
     * The count
     */
    public Integer getCount() {
        return count;
    }

    /**
     *
     * @param count
     * The count
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     *
     * @return
     * The value
     */
    public List<Value> getValue() {
        return value;
    }

    /**
     *
     * @param value
     * The value
     */
    public void setValue(List<Value> value) {
        this.value = value;
    }

    @Override
    public String toString() {
        if (count == 0){
            return "No current sprint";
        }

        return value.get(0).getName();
    }

    public Value getCurrentSprint(){
        if (value.size() == 0){
            return null;
        }

        return value.get(0);
    }
}
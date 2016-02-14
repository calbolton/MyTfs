package com.x8.mobile.mytfs.Tfs.Query;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SortColumn {

    @SerializedName("field")
    @Expose
    private Field field;
    @SerializedName("descending")
    @Expose
    private Boolean descending;

    /**
     *
     * @return
     * The field
     */
    public Field getField() {
        return field;
    }

    /**
     *
     * @param field
     * The field
     */
    public void setField(Field field) {
        this.field = field;
    }

    /**
     *
     * @return
     * The descending
     */
    public Boolean getDescending() {
        return descending;
    }

    /**
     *
     * @param descending
     * The descending
     */
    public void setDescending(Boolean descending) {
        this.descending = descending;
    }

}
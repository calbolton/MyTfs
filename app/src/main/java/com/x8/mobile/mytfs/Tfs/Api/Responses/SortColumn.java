package com.x8.mobile.mytfs.Tfs.Api.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.x8.mobile.mytfs.Tfs.Models.FieldReference;

public class SortColumn {

    @SerializedName("field")
    @Expose
    private FieldReference field;
    @SerializedName("descending")
    @Expose
    private Boolean descending;

    /**
     *
     * @return
     * The field
     */
    public FieldReference getField() {
        return field;
    }

    /**
     *
     * @param field
     * The field
     */
    public void setField(FieldReference field) {
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
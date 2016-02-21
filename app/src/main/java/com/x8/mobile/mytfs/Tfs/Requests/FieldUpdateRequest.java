package com.x8.mobile.mytfs.Tfs.Requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FieldUpdateRequest {

    public static final String OP_ADD = "add";
    public static final String OP_REPLACE = "replace";
    public static final String OP_REMOVE = "remove";
    public static final String OP_TEST = "add";

    public FieldUpdateRequest(String p_op, String p_fieldName, String p_value){
        op = p_op;
        path = "/fields/" + p_fieldName;
        value = p_value;
    }

    @SerializedName("op")
    @Expose
    private String op;
    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("value")
    @Expose
    private String value;

    /**
     *
     * @return
     * The op
     */
    public String getOp() {
        return op;
    }

    /**
     *
     * @param op
     * The op
     */
    public void setOp(String op) {
        this.op = op;
    }

    /**
     *
     * @return
     * The path
     */
    public String getPath() {
        return path;
    }

    /**
     *
     * @param path
     * The path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     *
     * @return
     * The value
     */
    public String getValue() {
        return value;
    }

    /**
     *
     * @param value
     * The value
     */
    public void setValue(String value) {
        this.value = value;
    }

}
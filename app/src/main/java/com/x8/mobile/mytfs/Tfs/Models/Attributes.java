package com.x8.mobile.mytfs.Tfs.Models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Attributes {

    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("finishDate")
    @Expose
    private String finishDate;

    /**
     *
     * @return
     * The startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     *
     * @param startDate
     * The startDate
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     *
     * @return
     * The finishDate
     */
    public String getFinishDate() {
        return finishDate;
    }

    /**
     *
     * @param finishDate
     * The finishDate
     */
    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

}
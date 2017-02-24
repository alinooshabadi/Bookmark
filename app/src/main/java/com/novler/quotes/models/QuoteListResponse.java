package com.novler.quotes.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

/**
 * Created by P on 2/14/2017.
 */

@Generated("org.jsonschema2pojo")
public class QuoteListResponse {

    @SerializedName("Data")
    @Expose
    private List<QuoteListData> data = new ArrayList<QuoteListData>();
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Status")
    @Expose
    private int status;

    /**
     *
     * @return
     * The data
     */
    public List<QuoteListData> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<QuoteListData> data) {
        this.data = data;
    }

    /**
     *
     * @return
     * The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *
     * @return
     * The status
     */
    public int getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(int status) {
        this.status = status;
    }
}

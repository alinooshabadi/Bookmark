package com.novler.quotes.networking;

import com.google.gson.annotations.SerializedName;

/**
 * Created by P on 2/14/2017.
 */

public class Response {
    @SerializedName("status")
    public String status;

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @SuppressWarnings({"unused", "used by Retrofit"})
    public Response() {
    }

    public Response(String status) {
        this.status = status;
    }
}

package com.novler.quotes.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

/**
 * Created by P on 2/14/2017.
 */

@Generated("org.jsonschema2pojo")
public class QuoteListData {
    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("User")
    @Expose
    private String user;
    @SerializedName("Text")
    @Expose
    private String text;
    @SerializedName("Novel")
    @Expose
    private String novel;
    @SerializedName("Author")
    @Expose
    private String author;

    public String getNovelmage() {
        return novelmage;
    }

    public void setNovelmage(String novelmage) {
        this.novelmage = novelmage;
    }

    @SerializedName("Novelmage")
    @Expose
    private String novelmage;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @SerializedName("DateShamsi")
    @Expose
    private String date;

    public String getNovel() {
        return novel;
    }

    public void setNovel(String novel) {
        this.novel = novel;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }



    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The name
     */
    public String getUser() {
        return user;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     *
     * @return
     * The description
     */
    public String getText() {
        return text;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setText(String text) {
        this.text = text;
    }


}

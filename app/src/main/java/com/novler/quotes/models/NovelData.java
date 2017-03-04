package com.novler.quotes.models;


import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class NovelData {

  @SerializedName("id")
  @Expose
  private Integer id;
  @SerializedName("title")
  @Expose
  private String title;
  @SerializedName("originalTitle")
  @Expose
  private String originalTitle;
  @SerializedName("author")
  @Expose
  private String author;
  @SerializedName("authorId")
  @Expose
  private @Nullable Integer authorId;
  @SerializedName("translator")
  @Expose
  private @Nullable String translator;
  @SerializedName("publisher")
  @Expose
  private @Nullable String publisher;
  @SerializedName("cover")
  @Expose
  private String cover;
  @SerializedName("quotes")
  @Expose
  private List<QuoteListData> quotes = new ArrayList<QuoteListData>();
  @SerializedName("rate")
  @Expose
  private Double rate;
  @SerializedName("views")
  @Expose
  private Integer views;
  @SerializedName("url")
  @Expose
  private String url;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }



  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getOriginalTitle() {
    return originalTitle;
  }

  public void setOriginalTitle(String originalTitle) {
    this.originalTitle = originalTitle;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public Integer getAuthorId() {
    return authorId;
  }

  public void setAuthorId(Integer authorId) {
    this.authorId = authorId;
  }

  public String getTranslator() {
    return translator;
  }

  public void setTranslator(String translator) {
    this.translator = translator;
  }

  public String getPublisher() {
    return publisher;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  public String getCover() {
    return cover;
  }

  public void setCover(String cover) {
    this.cover = cover;
  }

  public List<QuoteListData> getQuotes() {
    return quotes;
  }

  public void setQuotes(List<QuoteListData> quotes) {
    this.quotes = quotes;
  }

  public Double getRate() {
    return rate;
  }

  public void setRate(Double rate) {
    this.rate = rate;
  }

  public Integer getViews() {
    return views;
  }

  public void setViews(Integer views) {
    this.views = views;
  }


}

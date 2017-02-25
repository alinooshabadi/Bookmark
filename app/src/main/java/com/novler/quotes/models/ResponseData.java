package com.novler.quotes.models;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

/**
 * Created by P on 2/14/2017.
 */

@Generated("org.jsonschema2pojo")
public class ResponseData {

  @Expose
  private String message;

  @Expose
  private int status;

  @Expose
  private NovelData novel = new NovelData();

  @Expose
  private List<QuoteListData> quotes = new ArrayList<QuoteListData>();

  public NovelData getNovel() {
    return novel;
  }

  public void setNovel(NovelData novel) {
    this.novel = novel;
  }

  public List<QuoteListData> getQuotes() {
    return quotes;
  }

  public void setQuotes(List<QuoteListData> quotes) {
    this.quotes = quotes;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }
}

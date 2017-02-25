package com.novler.quotes.models;

import com.google.gson.annotations.Expose;

import javax.annotation.Generated;

/**
 * Created by P on 2/14/2017.
 */

@Generated("org.jsonschema2pojo")
public class QuoteListData {

  @Expose
  private String id;

  @Expose
  private String quoteNovlerId;

  @Expose
  private String user;

  @Expose
  private String text;

  @Expose
  private String novel;

  @Expose
  private int novelId;

  @Expose
  private String novelNovlerId;

  @Expose
  private String author;

  @Expose
  private String novelImage;

  @Expose
  private String dateShamsi;

  @Expose
  private String translator;

  @Expose
  private String authorNovlerId;

  @Expose
  private int authorId;

  @Expose
  private int page;

  @Expose
  private int likes;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getQuoteNovlerId() {
    return quoteNovlerId;
  }

  public void setQuoteNovlerId(String quoteNovlerId) {
    this.quoteNovlerId = quoteNovlerId;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getNovel() {
    return novel;
  }

  public void setNovel(String novel) {
    this.novel = novel;
  }

  public int getNovelId() {
    return novelId;
  }

  public void setNovelId(int novelId) {
    this.novelId = novelId;
  }

  public String getNovelNovlerId() {
    return novelNovlerId;
  }

  public void setNovelNovlerId(String novelNovlerId) {
    this.novelNovlerId = novelNovlerId;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getNovelImage() {
    return novelImage;
  }

  public void setNovelImage(String novelImage) {
    this.novelImage = novelImage;
  }

  public String getDateShamsi() {
    return dateShamsi;
  }

  public void setDateShamsi(String dateShamsi) {
    this.dateShamsi = dateShamsi;
  }

  public String getTranslator() {
    return translator;
  }

  public void setTranslator(String translator) {
    this.translator = translator;
  }

  public String getAuthorNovlerId() {
    return authorNovlerId;
  }

  public void setAuthorNovlerId(String authorNovlerId) {
    this.authorNovlerId = authorNovlerId;
  }

  public int getAuthorId() {
    return authorId;
  }

  public void setAuthorId(int authorId) {
    this.authorId = authorId;
  }

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public int getLikes() {
    return likes;
  }

  public void setLikes(int likes) {
    this.likes = likes;
  }




}

package com.novler.quotes.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AuthorData {

  @SerializedName("id")
  @Expose
  private Integer id;
  @SerializedName("birthDate")
  @Expose
  private String birthDate;
  @SerializedName("deathDate")
  @Expose
  private String deathDate;
  @SerializedName("novlerId")
  @Expose
  private String novlerId;
  @SerializedName("imageUrl")
  @Expose
  private String imageUrl;
  @SerializedName("title")
  @Expose
  private String title;
  @SerializedName("originalTitle")
  @Expose
  private String originalTitle;
  @SerializedName("quotes")
  @Expose
  private List<QuoteListData> quotes = null;
  @SerializedName("bio")
  @Expose
  private String bio;
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

  public String getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(String birthDate) {
    this.birthDate = birthDate;
  }

  public String getDeathDate() {
    return deathDate;
  }

  public void setDeathDate(String deathDate) {
    this.deathDate = deathDate;
  }

  public String getNovlerId() {
    return novlerId;
  }

  public void setNovlerId(String novlerId) {
    this.novlerId = novlerId;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
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

  public List<QuoteListData> getQuotes() {
    return quotes;
  }

  public void setQuotes(List<QuoteListData> quotes) {
    this.quotes = quotes;
  }

  public String getBio() {
    return bio;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }

  public Integer getViews() {
    return views;
  }

  public void setViews(Integer views) {
    this.views = views;
  }

}


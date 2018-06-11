package org.sunbird.user.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @desc Education entity class.
 * @author Amit Kumar
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class Education {

  private String id;
  private String addressId;
  private String boardOrUniversity;
  private String courseName;
  private String createdBy;
  private String createdDate;
  private String degree;
  private Integer duration;
  private String grade;
  private Boolean isDeleted;
  private String name;
  private Double percentage;
  private String updatedBy;
  private String updatedDate;
  private String userId;
  private Integer yearOfPassing;
  private Address address;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getAddressId() {
    return addressId;
  }

  public void setAddressId(String addressId) {
    this.addressId = addressId;
  }

  public String getBoardOrUniversity() {
    return boardOrUniversity;
  }

  public void setBoardOrUniversity(String boardOrUniversity) {
    this.boardOrUniversity = boardOrUniversity;
  }

  public String getCourseName() {
    return courseName;
  }

  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public String getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(String createdDate) {
    this.createdDate = createdDate;
  }

  public String getDegree() {
    return degree;
  }

  public void setDegree(String degree) {
    this.degree = degree;
  }

  public Integer getDuration() {
    return duration;
  }

  public void setDuration(Integer duration) {
    this.duration = duration;
  }

  public String getGrade() {
    return grade;
  }

  public void setGrade(String grade) {
    this.grade = grade;
  }

  @JsonProperty(value = "isDeleted")
  public Boolean isDeleted() {
    return isDeleted;
  }

  public void setDeleted(Boolean isDeleted) {
    this.isDeleted = isDeleted;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Double getPercentage() {
    return percentage;
  }

  public void setPercentage(Double percentage) {
    this.percentage = percentage;
  }

  public String getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  public String getUpdatedDate() {
    return updatedDate;
  }

  public void setUpdatedDate(String updatedDate) {
    this.updatedDate = updatedDate;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Integer getYearOfPassing() {
    return yearOfPassing;
  }

  public void setYearOfPassing(Integer yearOfPassing) {
    this.yearOfPassing = yearOfPassing;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }
}

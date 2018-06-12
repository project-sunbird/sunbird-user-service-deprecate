package org.sunbird.user.models.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * @desc Job Profile entity class.
 * @author Amit Kumar
 */
public class JobProfile {

  private String id;
  private String addressId;
  private String boardName;
  private String createdBy;
  private String createdDate;
  private String endDate;
  private Boolean isCurrentJob;
  private Boolean isDeleted;
  private Boolean isRejected;
  private Boolean isVerified;
  private String jobName;
  private String joiningDate;
  private String orgId;
  private String orgName;
  private String role;
  private List<String> subject;
  private String updatedBy;
  private String updatedDate;
  private String userId;
  private String verifiedBy;
  private String verifiedDate;
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

  public String getBoardName() {
    return boardName;
  }

  public void setBoardName(String boardName) {
    this.boardName = boardName;
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

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  @JsonProperty(value = "isCurrentJob")
  public Boolean isCurrentJob() {
    return isCurrentJob;
  }

  public void setCurrentJob(Boolean isCurrentJob) {
    this.isCurrentJob = isCurrentJob;
  }

  @JsonProperty(value = "isDeleted")
  public Boolean isDeleted() {
    return isDeleted;
  }

  public void setDeleted(Boolean isDeleted) {
    this.isDeleted = isDeleted;
  }

  @JsonProperty(value = "isRejected")
  public Boolean isRejected() {
    return isRejected;
  }

  public void setRejected(Boolean isRejected) {
    this.isRejected = isRejected;
  }

  @JsonProperty(value = "isVerified")
  public Boolean isVerified() {
    return isVerified;
  }

  public void setVerified(Boolean isVerified) {
    this.isVerified = isVerified;
  }

  public String getJobName() {
    return jobName;
  }

  public void setJobName(String jobName) {
    this.jobName = jobName;
  }

  public String getJoiningDate() {
    return joiningDate;
  }

  public void setJoiningDate(String joiningDate) {
    this.joiningDate = joiningDate;
  }

  public String getOrgId() {
    return orgId;
  }

  public void setOrgId(String orgId) {
    this.orgId = orgId;
  }

  public String getOrgName() {
    return orgName;
  }

  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public List<String> getSubject() {
    return subject;
  }

  public void setSubject(List<String> subject) {
    this.subject = subject;
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

  public String getVerifiedBy() {
    return verifiedBy;
  }

  public void setVerifiedBy(String verifiedBy) {
    this.verifiedBy = verifiedBy;
  }

  public String getVerifiedDate() {
    return verifiedDate;
  }

  public void setVerifiedDate(String verifiedDate) {
    this.verifiedDate = verifiedDate;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }
}

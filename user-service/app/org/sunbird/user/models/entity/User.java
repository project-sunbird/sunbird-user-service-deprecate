package org.sunbird.user.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

/**
 * @desc User entity class
 * @author Amit Kumar
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class User {

  private String id;
  private String avatar;
  private String countryCode;
  private String createdBy;
  private String createdDate;
  private String dob;
  private String email;
  private Boolean emailVerified;
  private String firstName;
  private String lastName;
  private String gender;
  private List<String> grade;
  private Boolean isDeleted;
  private List<String> language;
  private String lastLoginTime;
  private String location;
  private String phone;
  private Boolean phoneVerified;
  private String profileSummary;
  private Map<String, String> profileVisibility;
  private List<String> roles;
  private String rootOrgId;
  private Integer status;
  private List<String> subject;
  private String updatedBy;
  private String updatedDate;
  private String userId;
  private String userName;
  private List<Map<String, Object>> webPages;
  private String channel;
  private String loginId;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
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

  public String getDob() {
    return dob;
  }

  public void setDob(String dob) {
    this.dob = dob;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @JsonProperty(value = "emailVerified")
  public Boolean getEmailVerified() {
    return emailVerified;
  }

  public void setEmailVerified(Boolean emailVerified) {
    this.emailVerified = emailVerified;
  }

  @JsonProperty(value = "phoneVerified")
  public Boolean getPhoneVerified() {
    return phoneVerified;
  }

  public void setPhoneVerified(Boolean phoneVerified) {
    this.phoneVerified = phoneVerified;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public List<String> getGrade() {
    return grade;
  }

  public void setGrade(List<String> grade) {
    this.grade = grade;
  }

  @JsonProperty(value = "isDeleted")
  public Boolean isDeleted() {
    return isDeleted;
  }

  public void setDeleted(Boolean isDeleted) {
    this.isDeleted = isDeleted;
  }

  public List<String> getLanguage() {
    return language;
  }

  public void setLanguage(List<String> language) {
    this.language = language;
  }

  public String getLastLoginTime() {
    return lastLoginTime;
  }

  public void setLastLoginTime(String lastLoginTime) {
    this.lastLoginTime = lastLoginTime;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getProfileSummary() {
    return profileSummary;
  }

  public void setProfileSummary(String profileSummary) {
    this.profileSummary = profileSummary;
  }

  public Map<String, String> getProfileVisibility() {
    return profileVisibility;
  }

  public void setProfileVisibility(Map<String, String> profileVisibility) {
    this.profileVisibility = profileVisibility;
  }

  public List<String> getRoles() {
    return roles;
  }

  public void setRoles(List<String> roles) {
    this.roles = roles;
  }

  public String getRootOrgId() {
    return rootOrgId;
  }

  public void setRootOrgId(String rootOrgId) {
    this.rootOrgId = rootOrgId;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
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

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public List<Map<String, Object>> getWebPages() {
    return webPages;
  }

  public void setWebPages(List<Map<String, Object>> webPages) {
    this.webPages = webPages;
  }

  public String getChannel() {
    return channel;
  }

  public void setChannel(String channel) {
    this.channel = channel;
  }

  public String getLoginId() {
    return loginId;
  }

  public void setLoginId(String loginId) {
    this.loginId = loginId;
  }
}

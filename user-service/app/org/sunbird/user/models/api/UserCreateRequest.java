package org.sunbird.user.models.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

/**
 * @desc User Create API request class
 * @author Amit Kumar
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class UserCreateRequest {
  private String id;
  private String avatar;
  private String countryCode;
  private String createdBy;
  private String dob;
  private String email;
  private Boolean emailVerified;
  private String firstName;
  private String lastName;
  private String gender;
  private List<String> grade;
  private List<String> language;
  private String location;
  private String phone;
  private Boolean phoneVerified;
  private String profileSummary;
  private Map<String, String> profileVisibility;
  private List<String> roles;
  private List<String> subject;
  private String updatedBy;
  private String userName;
  private String userId;
  private List<Map<String, Object>> webPages;
  private String channel;
  private List<Map<String, String>> externalIds;
  private List<Map<String, String>> address;

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

  public List<String> getLanguage() {
    return language;
  }

  public void setLanguage(List<String> language) {
    this.language = language;
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

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
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

  public List<Map<String, String>> getExternalIds() {
    return externalIds;
  }

  public void setExternalIds(List<Map<String, String>> externalIds) {
    this.externalIds = externalIds;
  }

  public List<Map<String, String>> getAddress() {
    return address;
  }

  public void setAddress(List<Map<String, String>> address) {
    this.address = address;
  }
}

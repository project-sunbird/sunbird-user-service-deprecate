package org.sunbird.user.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.sql.Timestamp;

/**
 * @desc User External Identity entity class.
 * @author Amit Kumar
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class UserExternalIdentity {

  private String externalId;
  private String provider;
  private String idType;
  private String userId;
  private Timestamp createdOn;
  private Timestamp lastUpdatedOn;
  private String createdBy;
  private String lastUpdatedBy;

  public String getExternalId() {
    return externalId;
  }

  public void setExternalId(String externalId) {
    this.externalId = externalId;
  }

  public String getProvider() {
    return provider;
  }

  public void setProvider(String provider) {
    this.provider = provider;
  }

  public String getIdType() {
    return idType;
  }

  public void setIdType(String idType) {
    this.idType = idType;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Timestamp getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(Timestamp createdOn) {
    this.createdOn = createdOn;
  }

  public Timestamp getLastUpdatedOn() {
    return lastUpdatedOn;
  }

  public void setLastUpdatedOn(Timestamp lastUpdatedOn) {
    this.lastUpdatedOn = lastUpdatedOn;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public String getLastUpdatedBy() {
    return lastUpdatedBy;
  }

  public void setLastUpdatedBy(String lastUpdatedBy) {
    this.lastUpdatedBy = lastUpdatedBy;
  }
}

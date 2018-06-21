package org.sunbird.user.services;

import java.util.List;
import java.util.Map;

/** @author Amit Kumar */
public interface UserService {

  /**
   * Encrypt given data.
   *
   * @param value Data to encrypt
   * @return Encrypted value
   */
  String getEncryptedData(String value);

  /**
   * Check if user exists or not.
   *
   * @param userLoginId User login ID
   * @param True, if user exists. Otherwise, returns false.
   */
  boolean isUserExists(String userLoginId);

  /**
   * Get external ID details by provider, id type and id.
   *
   * @param provider Provider name
   * @param idType Type of external ID
   * @param id External ID
   * @return External id details
   */
  Map<String, Object> getExternalIdDetails(String provider, String idType, String id);

  /**
   * Get email and phone fields uniqueness configuration settings.
   *
   * @return Email and phone fields uniqueness configuration settings.
   */
  Map<String, Boolean> getConfigSettings();

  /**
   * Get login ID for given user name and channel in expected format (userName@channel or userName).
   *
   * @param userName User name
   * @param channel Channel to which user belongs
   * @return userName@channel if channel is not blank. Otherwise, return userName as is.
   */
  String getLoginId(String userName, String channel);

  /**
   * Get user details corresponding to given phone number.
   *
   * @param phoneNumber Phone number
   * @return User details corresponding to given phone number.
   */
  List<Map<String, Object>> getUserDetailsByPhone(String phoneNumber);

  /**
   * Get user details corresponding to given email address.
   *
   * @param email Email ID
   * @return User details corresponding to given email address.
   */
  List<Map<String, Object>> getUserDetailsByEmail(String email);

  /**
   * Get default channel (from env or configuration).
   *
   * @return Default configured channel
   */
  String getDefaultChannel();
}

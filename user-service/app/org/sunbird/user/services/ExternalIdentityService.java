package org.sunbird.user.services;

import java.util.Map;

public interface ExternalIdentityService {

  /**
   * This method will save user external identity.
   *
   * @param userMap User details.
   */
  void saveUserExternalIdentity(Map<String, Object> userMap);
}

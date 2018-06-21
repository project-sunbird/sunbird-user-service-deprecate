package org.sunbird.user.services;

import java.util.Map;

public interface JobProfileService {

  /**
   * This method will save user job profile details.
   *
   * @param userMap User details.
   */
  void saveJobProfileDetails(Map<String, Object> userMap);
}

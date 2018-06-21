package org.sunbird.user.services;

import java.util.Map;

/** @author Amit Kumar */
public interface EducationService {

  /**
   * This method will save education details of given user.
   *
   * @param userMap User details
   */
  void saveEducationDetails(Map<String, Object> userMap);
}

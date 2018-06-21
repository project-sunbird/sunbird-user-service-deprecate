package org.sunbird.user.services;

import java.util.Map;

public interface OnBoardingEmailService {

  /**
   * This method is used for sending on-boarding mail
   *
   * @param emailTemplateMap
   */
  void sendOnboardingMail(Map<String, Object> emailTemplateMap);
}

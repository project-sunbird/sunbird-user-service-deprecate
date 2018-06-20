package org.sunbird.user.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.sunbird.actor.background.BackgroundOperations;
import org.sunbird.common.models.util.JsonKey;
import org.sunbird.common.models.util.ProjectUtil;
import org.sunbird.common.request.Request;
import org.sunbird.learner.util.Util;
import org.sunbird.user.utils.Constant;

public class OnBoardingEmailService {

  private OnBoardingEmailService() {}

  public static void sendOnboardingMail(Map<String, Object> emailTemplateMap) {
    if (!(StringUtils.isBlank((String) emailTemplateMap.get(JsonKey.EMAIL)))) {
      String envName = ProjectUtil.getConfigValue(JsonKey.SUNBIRD_INSTALLATION);
      String welcomeSubject = ProjectUtil.getConfigValue(Constant.MAIL_SUBJECT);
      emailTemplateMap.put(JsonKey.SUBJECT, ProjectUtil.formatMessage(welcomeSubject, envName));
      List<String> reciptientsMail = new ArrayList<>();
      reciptientsMail.add((String) emailTemplateMap.get(JsonKey.EMAIL));
      emailTemplateMap.put(JsonKey.RECIPIENT_EMAILS, reciptientsMail);
      String webUrl = Util.getSunbirdWebUrlPerTenent(emailTemplateMap);
      if ((!StringUtils.isBlank(webUrl)) && (!Constant.SUNBIRD_WEB_URL.equalsIgnoreCase(webUrl))) {
        emailTemplateMap.put(JsonKey.WEB_URL, webUrl);
      }
      String appUrl = ProjectUtil.getConfigValue(Constant.SUNBIRD_APP_URL);
      if ((!StringUtils.isBlank(appUrl)) && (!Constant.SUNBIRD_APP_URL.equalsIgnoreCase(appUrl))) {
        emailTemplateMap.put(JsonKey.APP_URL, appUrl);
      }
      emailTemplateMap.put(
          JsonKey.BODY, ProjectUtil.getConfigValue(JsonKey.ONBOARDING_WELCOME_MAIL_BODY));
      emailTemplateMap.put(JsonKey.NOTE, ProjectUtil.getConfigValue(JsonKey.MAIL_NOTE));
      emailTemplateMap.put(JsonKey.ORG_NAME, envName);
      String welcomeMessage = ProjectUtil.getConfigValue(Constant.WELCOME_MESSAGE);
      emailTemplateMap.put(
          JsonKey.WELCOME_MESSAGE, ProjectUtil.formatMessage(welcomeMessage, envName));
      emailTemplateMap.put(JsonKey.EMAIL_TEMPLATE_TYPE, Constant.EMAIL_TEMPLATE_TYPE);
      Request request = new Request();
      request.setOperation(BackgroundOperations.emailService.name());
      request.put(JsonKey.EMAIL_REQUEST, emailTemplateMap);
      // tellToAnother(request);
    }
  }
}

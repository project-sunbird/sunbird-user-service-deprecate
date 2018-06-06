package org.sunbird.user.services.service;

import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.sunbird.common.exception.ProjectCommonException;
import org.sunbird.common.models.util.JsonKey;
import org.sunbird.common.responsecode.ResponseCode;
import org.sunbird.services.sso.SSOManager;
import org.sunbird.services.sso.SSOServiceFactory;

/** @author Amit Kumar */
public class KeycloakService {
  private KeycloakService() {}

  private static SSOManager ssoManager = SSOServiceFactory.getInstance();

  public static String insertUserData(Map<String, Object> userMap) {
    String userId = "";
    Map<String, String> responseMap = ssoManager.createUser(userMap);
    userId = responseMap.get(JsonKey.USER_ID);
    if (StringUtils.isNotBlank(userId)) {
      return userId;
    } else {
      throw new ProjectCommonException(
          ResponseCode.userRegUnSuccessfull.getErrorCode(),
          ResponseCode.userRegUnSuccessfull.getErrorMessage(),
          ResponseCode.SERVER_ERROR.getResponseCode());
    }
  }
}

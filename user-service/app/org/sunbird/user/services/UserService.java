package org.sunbird.user.services;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.sunbird.common.exception.ProjectCommonException;
import org.sunbird.common.models.response.Response;
import org.sunbird.common.models.util.JsonKey;
import org.sunbird.common.models.util.ProjectUtil;
import org.sunbird.common.models.util.StringFormatter;
import org.sunbird.common.models.util.datasecurity.EncryptionService;
import org.sunbird.common.responsecode.ResponseCode;
import org.sunbird.user.services.dao.BaseDao;
import org.sunbird.user.services.dao.DaoFactory;
import org.sunbird.user.services.dao.UserDao;
import org.sunbird.user.utils.Constant;

/**
 * This class will provide helper methods for user apis.
 *
 * @author Amit Kumar
 */
public class UserService {

  private UserService() {}

  /**
   * This method will encrypt given data.
   *
   * @param value to encrypt
   * @return encrypted value
   */
  public static String getEncryptedData(String value) {
    try {
      EncryptionService encryptionService =
          org.sunbird.common.models.util.datasecurity.impl.ServiceFactory
              .getEncryptionServiceInstance(null);
      return encryptionService.encryptData(value);
    } catch (Exception e) {
      ProjectCommonException.throwServerErrorException(ResponseCode.SERVER_ERROR, null);
    }
    return value;
  }

  /**
   * This method will check for user in our application with userName@channel i.e loginId value.
   *
   * @param userLoginId User login id.
   */
  public static void isUserExists(String userLoginId) {
    // loginId is encrypted in our application
    String encryptedLoginId = getEncryptedData(userLoginId);
    UserDao userDao = (UserDao) DaoFactory.getDao(Constant.USER);
    Response response =
        userDao.getRecordsByIndexedProperty(
            Constant.KEY_SPACE, Constant.USER_TABLE, JsonKey.LOGIN_ID, encryptedLoginId);

    if (null != response
        && CollectionUtils.isNotEmpty((List<Map<String, Object>>) response.get(JsonKey.RESPONSE))) {
      ProjectCommonException.throwClientErrorException(
          ResponseCode.userAlreadyExists,
          MessageFormat.format(
              ResponseCode.userAlreadyExists.getErrorMessage(),
              StringFormatter.joinByAnd(JsonKey.USERNAME, JsonKey.CHANNEL)));
    }
  }

  /**
   * This method will fetch user external identity details by primary key.
   *
   * @param externalId External id details
   */
  public static Map<String, Object> getExternalIdDetails(Map<String, Object> externalId) {
    if (StringUtils.isNotBlank((String) externalId.get(JsonKey.ID))
        && StringUtils.isNotBlank((String) externalId.get(JsonKey.PROVIDER))
        && StringUtils.isNotBlank((String) externalId.get(Constant.ID_TYPE))) {
      UserDao userDao = (UserDao) DaoFactory.getDao(Constant.USER);
      Response response =
          userDao.getRecordsByCompositeKey(
              JsonKey.SUNBIRD, Constant.USER_EXTERNAL_IDENTITY_TABLE, externalId);
      return ((List<Map<String, Object>>) response.get(JsonKey.RESPONSE)).get(0);
    }
    return Collections.emptyMap();
  }

  /**
   * This method will check whether phone and email should be unique in our system or not.
   *
   * @return System config details about phone and email.
   */
  public static Map<String, Boolean> getConfigSettings() {
    Map<String, Boolean> configSettings = new HashMap<>();
    List<String> paramValueList = Arrays.asList(JsonKey.PHONE_UNIQUE, JsonKey.EMAIL_UNIQUE);
    Response response =
        BaseDao.getRecordsByProperty(
            JsonKey.SUNBIRD, JsonKey.SYSTEM_SETTINGS_DB, JsonKey.ID, paramValueList);
    List<Map<String, Object>> responseList =
        (List<Map<String, Object>>) response.get(JsonKey.RESPONSE);
    if (CollectionUtils.isNotEmpty(responseList)) {
      for (Map<String, Object> resultMap : responseList) {
        configSettings.put(
            ((String) resultMap.get(JsonKey.FIELD)),
            Boolean.valueOf((String) resultMap.get(JsonKey.VALUE)));
      }
    } else {
      // By default both phone and email will be unique.
      configSettings.put(JsonKey.PHONE_UNIQUE, true);
      configSettings.put(JsonKey.EMAIL_UNIQUE, true);
    }
    return configSettings;
  }

  public static String getLoginId(String userName, String channel) {
    String loginId = null;
    if (StringUtils.isNotBlank(channel)) {
      loginId = (userName + "@" + channel);
    } else {
      loginId = userName;
    }
    return loginId;
  }

  public static List<Map<String, Object>> getUserDetailsByPhone(String phoneNumber) {
    Map<String, Object> filters = new HashMap<>();
    if (StringUtils.isNotBlank(phoneNumber)) {
      filters.put(JsonKey.ENC_PHONE, getEncryptedData(phoneNumber));
      return ElasticService.getContentByFiltersFromES(filters);
    }
    return Collections.emptyList();
  }

  public static List<Map<String, Object>> getUserDetailsByEmail(String email) {
    Map<String, Object> filters = new HashMap<>();
    if (StringUtils.isNotBlank(email)) {
      filters.put(JsonKey.ENC_EMAIL, getEncryptedData(email));
      return ElasticService.getContentByFiltersFromES(filters);
    }
    return Collections.emptyList();
  }

  public static String getDefaultChannel() {
    if (StringUtils.isNotBlank(ProjectUtil.getConfigValue(JsonKey.SUNBIRD_DEFAULT_CHANNEL))) {
      return ProjectUtil.getConfigValue(JsonKey.SUNBIRD_DEFAULT_CHANNEL);
    } else {
      ProjectCommonException.throwClientErrorException(
          ResponseCode.mandatoryParamsMissing,
          MessageFormat.format(
              ResponseCode.mandatoryParamsMissing.getErrorMessage(), JsonKey.CHANNEL));
    }
    return "";
  }
}

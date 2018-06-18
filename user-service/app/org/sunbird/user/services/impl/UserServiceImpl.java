package org.sunbird.user.services.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.sunbird.common.ElasticSearchUtil;
import org.sunbird.common.exception.ProjectCommonException;
import org.sunbird.common.models.response.Response;
import org.sunbird.common.models.util.JsonKey;
import org.sunbird.common.models.util.ProjectUtil;
import org.sunbird.common.models.util.datasecurity.EncryptionService;
import org.sunbird.common.responsecode.ResponseCode;
import org.sunbird.user.services.UserService;
import org.sunbird.user.services.dao.BaseDao;
import org.sunbird.user.services.dao.DaoFactory;
import org.sunbird.user.services.dao.UserDao;
import org.sunbird.user.utils.Constant;
import org.sunbird.util.ConfigUtil;

/**
 * This class provides implementation of UserService interface.
 *
 * @author Amit Kumar
 */
public class UserServiceImpl implements UserService {

  private UserServiceImpl() {}

  private static UserServiceImpl userServiceImpl = null;

  public static synchronized UserServiceImpl getInstance() {
    if (null == userServiceImpl) {
      userServiceImpl = new UserServiceImpl();
    }
    return userServiceImpl;
  }

  @Override
  public String getEncryptedData(String value) {
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

  @Override
  public boolean isUserExists(String userLoginId) {
    // loginId is encrypted in our application
    String encryptedLoginId = getEncryptedData(userLoginId);
    UserDao userDao = (UserDao) DaoFactory.getDao(Constant.USER);
    Response response =
        userDao.getRecordsByIndexedProperty(
            Constant.KEY_SPACE, Constant.USER_TABLE, JsonKey.LOGIN_ID, encryptedLoginId);
    return (null != response
        && CollectionUtils.isNotEmpty((List<Map<String, Object>>) response.get(JsonKey.RESPONSE)));
  }

  @Override
  public Map<String, Object> getExternalIdDetails(String provider, String idType, String id) {
    if (StringUtils.isNotBlank(provider)
        && StringUtils.isNotBlank(idType)
        && StringUtils.isNotBlank(id)) {
      UserDao userDao = (UserDao) DaoFactory.getDao(Constant.USER);
      Map<String, Object> externalId = new HashMap<>();
      externalId.put(JsonKey.PROVIDER, provider);
      externalId.put(Constant.ID_TYPE, idType);
      externalId.put(JsonKey.ID, id);
      Response response =
          userDao.getRecordsByCompositeKey(
              JsonKey.SUNBIRD, Constant.USER_EXTERNAL_IDENTITY_TABLE, externalId);
      return ((List<Map<String, Object>>) response.get(JsonKey.RESPONSE)).get(0);
    }
    return Collections.emptyMap();
  }

  @Override
  public Map<String, Boolean> getConfigSettings() {
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
            (StringUtils.isNotBlank((String) resultMap.get(JsonKey.VALUE))
                ? Boolean.valueOf((String) resultMap.get(JsonKey.VALUE))
                : true));
      }
    } else {
      // By default both phone and email will be unique.
      configSettings.put(JsonKey.PHONE_UNIQUE, true);
      configSettings.put(JsonKey.EMAIL_UNIQUE, true);
    }
    return configSettings;
  }

  @Override
  public String getLoginId(String userName, String channel) {
    String loginId = null;
    if (StringUtils.isNotBlank(channel)) {
      loginId = (userName + "@" + channel);
    } else {
      loginId = userName;
    }
    return loginId;
  }

  @Override
  public List<Map<String, Object>> getUserDetailsByPhone(String phoneNumber) {
    Map<String, Object> filters = new HashMap<>();
    if (StringUtils.isNotBlank(phoneNumber)) {
      filters.put(JsonKey.ENC_PHONE, getEncryptedData(phoneNumber));
      return ElasticSearchUtil.searchByFilters(
          ProjectUtil.EsIndex.sunbird.getIndexName(),
          ProjectUtil.EsType.user.getTypeName(),
          filters);
    }
    return Collections.emptyList();
  }

  @Override
  public List<Map<String, Object>> getUserDetailsByEmail(String email) {
    Map<String, Object> filters = new HashMap<>();
    if (StringUtils.isNotBlank(email)) {
      filters.put(JsonKey.ENC_EMAIL, getEncryptedData(email));
      return ElasticSearchUtil.searchByFilters(
          ProjectUtil.EsIndex.sunbird.getIndexName(),
          ProjectUtil.EsType.user.getTypeName(),
          filters);
    }
    return Collections.emptyList();
  }

  @Override
  public String getDefaultChannel() {
    return StringUtils.isNotBlank(ConfigUtil.getConfig().getString(JsonKey.SUNBIRD_DEFAULT_CHANNEL))
        ? ConfigUtil.getConfig().getString(JsonKey.SUNBIRD_DEFAULT_CHANNEL)
        : "";
  }
}

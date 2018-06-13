package org.sunbird.user.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.sunbird.common.ElasticSearchUtil;
import org.sunbird.common.exception.ProjectCommonException;
import org.sunbird.common.models.response.Response;
import org.sunbird.common.models.util.JsonKey;
import org.sunbird.common.models.util.ProjectUtil;
import org.sunbird.common.models.util.ProjectUtil.EsIndex;
import org.sunbird.common.models.util.ProjectUtil.EsType;
import org.sunbird.common.models.util.StringFormatter;
import org.sunbird.common.models.util.datasecurity.EncryptionService;
import org.sunbird.common.responsecode.ResponseCode;
import org.sunbird.dto.SearchDTO;
import org.sunbird.user.services.dao.BaseDao;
import org.sunbird.user.services.dao.DaoFactory;
import org.sunbird.user.services.dao.UserDao;

public class UserUtil {

  private static EncryptionService encryptionService =
      org.sunbird.common.models.util.datasecurity.impl.ServiceFactory.getEncryptionServiceInstance(
          null);

  private UserUtil() {}

  /**
   * This method will encrypt given data.
   *
   * @param value to encrypt
   * @return encrypted value
   */
  public static String getEncryptedData(String value) {
    try {
      return encryptionService.encryptData(value);
    } catch (Exception e) {
      throw new ProjectCommonException(
          ResponseCode.userDataEncryptionError.getErrorCode(),
          ResponseCode.userDataEncryptionError.getErrorMessage(),
          ResponseCode.SERVER_ERROR.getResponseCode());
    }
  }

  /**
   * This method will check for user in our application with userName@channel i.e loginId value.
   *
   * @param user User object.
   */
  public static void checkUserExistOrNot(String userLoginId) {
    // loginId is encrypted in our application
    String encryptedLoginId = getEncryptedData(userLoginId);
    UserDao userDao = (UserDao) DaoFactory.getDao(Constant.USER);
    Response response =
        userDao.getRecordsByIndexedProperty(
            Constant.KEY_SPACE, Constant.USER_TABLE, JsonKey.LOGIN_ID, encryptedLoginId);

    if (null != response
        && CollectionUtils.isNotEmpty((List<Map<String, Object>>) response.get(JsonKey.RESPONSE))) {
      throwUserAlreadyExistsException(
          ProjectUtil.formatMessage(
              ResponseCode.userAlreadyExists.getErrorMessage(),
              StringFormatter.joinByAnd(JsonKey.USERNAME, JsonKey.CHANNEL)));
    }
  }

  /**
   * This method will validate channel and return the id of organization associated with this
   * channel.
   *
   * @param channel value of channel of an organization
   * @return Id of Root organization.
   */
  public static String getRootOrgIdFromChannel(String channel) {
    Map<String, Object> filters = new HashMap<>();
    filters.put(JsonKey.IS_ROOT_ORG, true);
    if (StringUtils.isNotBlank(channel)) {
      filters.put(JsonKey.CHANNEL, channel);
    } else {
      if (StringUtils.isNotBlank(ProjectUtil.getConfigValue(JsonKey.SUNBIRD_DEFAULT_CHANNEL))) {
        filters.put(JsonKey.CHANNEL, ProjectUtil.getConfigValue(JsonKey.SUNBIRD_DEFAULT_CHANNEL));
      } else {
        throwMandatoryParamMissingException(JsonKey.CHANNEL);
      }
    }
    List<Map<String, Object>> esContent = getContentByFiltersFromES(filters);
    if (CollectionUtils.isNotEmpty(esContent)) {
      return (String) esContent.get(0).get(JsonKey.ID);
    } else {
      if (StringUtils.isNotBlank(channel)) {
        throwInvalidParamValueException(JsonKey.CHANNEL, channel);
      } else {
        throwMandatoryParamMissingException(JsonKey.CHANNEL);
      }
    }
    return channel;
  }

  private static List<Map<String, Object>> getContentByFiltersFromES(Map<String, Object> filters) {
    SearchDTO searchDTO = new SearchDTO();
    searchDTO.getAdditionalProperties().put(JsonKey.FILTERS, filters);
    Map<String, Object> esResult =
        ElasticSearchUtil.complexSearch(
            searchDTO, EsIndex.sunbird.getIndexName(), EsType.organisation.getTypeName());
    if (MapUtils.isNotEmpty(esResult)
        && CollectionUtils.isNotEmpty((List) esResult.get(JsonKey.CONTENT))) {
      return ((List<Map<String, Object>>) esResult.get(JsonKey.CONTENT));
    }
    return Collections.emptyList();
  }

  /**
   * This method will check the uniqueness for externalId and provider combination.
   *
   * @param userRequest User details
   */
  public static void checkExternalIdUniqueness(
      List<Map<String, String>> externalIds, String requestedUserId, String operation) {
    if (CollectionUtils.isNotEmpty(externalIds)) {
      for (Map<String, String> externalId : externalIds) {
        if (StringUtils.isNotBlank(externalId.get(JsonKey.ID))
            && StringUtils.isNotBlank(externalId.get(JsonKey.PROVIDER))
            && StringUtils.isNotBlank(externalId.get(Constant.ID_TYPE))) {
          UserDao userDao = (UserDao) DaoFactory.getDao(Constant.USER);
          Response response =
              userDao.getRecordsByIndexedProperty(
                  JsonKey.SUNBIRD,
                  Constant.USER_EXTERNAL_IDENTITY_TABLE,
                  JsonKey.SLUG,
                  externalId.get(JsonKey.SLUG));
          String dbUserId = null;
          Map<String, Object> externalIdDbRecord =
              ((List<Map<String, Object>>) response.get(JsonKey.RESPONSE)).get(0);
          if (MapUtils.isNotEmpty(externalIdDbRecord)) {
            dbUserId = (String) externalIdDbRecord.get(JsonKey.USER_ID);
          }
          validateExternalId(requestedUserId, operation, externalId, dbUserId);
        }
      }
    }
  }

  private static void validateExternalId(
      String requestedUserId, String operation, Map<String, String> externalId, String dbUserId) {
    // in case of update operation requestedUserId is not blank.
    if (StringUtils.isNotBlank(dbUserId)) {
      // If dbUserId is not empty and operation is CREATE
      if (JsonKey.CREATE.equalsIgnoreCase(operation)) {
        throwUserAlreadyExistsException(
            ProjectUtil.formatMessage(
                ResponseCode.externalIdNotFound.getErrorMessage(),
                externalId.get(JsonKey.ID),
                externalId.get(Constant.ID_TYPE),
                externalId.get(JsonKey.PROVIDER)));
      } else if (JsonKey.UPDATE.equalsIgnoreCase(operation)
          && (!(requestedUserId.equalsIgnoreCase(dbUserId)))) {
        // If end user will try to add,edit or remove other user extIds throw exception
        if (Constant.ADD.equalsIgnoreCase(externalId.get(JsonKey.OPERATION))) {
          throwExternalIdAssignedToOtherException(externalId);
        } else {
          throwExternalIDNotFoundException(
              externalId.get(JsonKey.ID),
              externalId.get(Constant.ID_TYPE),
              externalId.get(JsonKey.PROVIDER));
        }
      }
    } else {
      // if user will try to delete non existing extIds
      if (JsonKey.UPDATE.equalsIgnoreCase(operation)
          && Constant.REMOVE.equalsIgnoreCase(externalId.get(JsonKey.OPERATION))) {
        throwExternalIDNotFoundException(
            externalId.get(JsonKey.ID),
            externalId.get(Constant.ID_TYPE),
            externalId.get(JsonKey.PROVIDER));
      }
    }
  }

  public static void checkPhoneUniqueness(String phone, String userId, String opType) {
    if (!StringUtils.isBlank(phone)) {
      String phoneSetting = getConfigSettings().get(JsonKey.PHONE_UNIQUE);
      if (null != phoneSetting && Boolean.parseBoolean(phoneSetting)) {
        checkUniqueness(phone, userId, opType, JsonKey.PHONE);
      }
    }
  }

  public static void checkEmailUniqueness(String email, String userId, String opType) {
    if (!StringUtils.isBlank(email)) {
      String emailSetting = getConfigSettings().get(JsonKey.EMAIL_UNIQUE);
      if (null != emailSetting && Boolean.parseBoolean(emailSetting)) {
        checkUniqueness(email, userId, opType, JsonKey.EMAIL);
      }
    }
  }

  /**
   * This method will check whether phone or email is unique or not.
   *
   * @param paramValue Value will be either user email or phone
   * @param userId user Id
   * @param opType create or update
   * @param param phone or email
   */
  private static void checkUniqueness(
      String paramValue, String userId, String opType, String param) {
    Map<String, Object> filters = new HashMap<>();
    if (JsonKey.PHONE.equalsIgnoreCase(param)) {
      filters.put(JsonKey.ENC_PHONE, getEncryptedData(paramValue));
    } else {
      filters.put(JsonKey.ENC_EMAIL, getEncryptedData(paramValue));
    }
    List<Map<String, Object>> userMapList = getContentByFiltersFromES(filters);
    String excMessage =
        ProjectUtil.formatMessage(ResponseCode.userAlreadyExists.getErrorMessage(), param);
    if (!userMapList.isEmpty()) {
      if (opType.equalsIgnoreCase(JsonKey.CREATE)) {
        throwUserAlreadyExistsException(excMessage);
      } else {
        Map<String, Object> user = userMapList.get(0);
        if (!(((String) user.get(JsonKey.ID)).equalsIgnoreCase(userId))) {
          throwUserAlreadyExistsException(excMessage);
        }
      }
    }
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

  private static Map<String, String> getConfigSettings() {
    Map<String, String> configSettings = new HashMap<>();
    List<Object> paramValueList = Arrays.asList(JsonKey.PHONE_UNIQUE, JsonKey.EMAIL_UNIQUE);
    Response response =
        BaseDao.getRecordsByProperty(
            JsonKey.SUNBIRD, JsonKey.SYSTEM_SETTINGS_DB, JsonKey.ID, paramValueList);
    List<Map<String, Object>> responseList =
        (List<Map<String, Object>>) response.get(JsonKey.RESPONSE);
    if (CollectionUtils.isNotEmpty(responseList)) {
      for (Map<String, Object> resultMap : responseList) {
        configSettings.put(
            ((String) resultMap.get(JsonKey.FIELD)), (String) resultMap.get(JsonKey.VALUE));
      }
    } else {
      // By default both phone and email will be unique.
      configSettings.put(JsonKey.PHONE_UNIQUE, String.valueOf(true));
      configSettings.put(JsonKey.EMAIL_UNIQUE, String.valueOf(true));
    }
    return configSettings;
  }

  public static List<Map<String, String>> convertExternalIdsValueToLowerCase(
      List<Map<String, String>> externalIds) {
    ConvertValuesToLowerCase mapper =
        s -> {
          s.put(JsonKey.ID, s.get(JsonKey.ID).toLowerCase());
          s.put(JsonKey.PROVIDER, s.get(JsonKey.PROVIDER).toLowerCase());
          s.put(Constant.ID_TYPE, s.get(Constant.ID_TYPE).toLowerCase());
          return s;
        };
    return externalIds.stream().map(mapper::convertToLowerCase).collect(Collectors.toList());
  }

  public enum UserDaoType {
    USER("user"),
    EDUCATION("education"),
    JOB_PROFILE("jobProfile"),
    USER_EXTERNAL_IDENTITY("userExternalIdentity"),
    USER_ORG("userOrg");

    private String value;

    UserDaoType(String value) {
      this.value = value;
    }

    public String getValue() {
      return this.value;
    }
  }

  private static void throwExternalIdAssignedToOtherException(Map<String, String> externalId) {
    throw new ProjectCommonException(
        ResponseCode.externalIdAssignedToOtherUser.getErrorCode(),
        ProjectUtil.formatMessage(
            ResponseCode.externalIdAssignedToOtherUser.getErrorMessage(),
            externalId.get(JsonKey.ID),
            externalId.get(Constant.ID_TYPE),
            externalId.get(JsonKey.PROVIDER)),
        ResponseCode.CLIENT_ERROR.getResponseCode());
  }

  private static void throwExternalIDNotFoundException(
      String externalId, String idType, String provider) {
    throw new ProjectCommonException(
        ResponseCode.externalIdNotFound.getErrorCode(),
        ProjectUtil.formatMessage(
            ResponseCode.externalIdNotFound.getErrorMessage(), externalId, idType, provider),
        ResponseCode.CLIENT_ERROR.getResponseCode());
  }

  private static void throwUserAlreadyExistsException(String exceptionMessage) {
    throw new ProjectCommonException(
        ResponseCode.userAlreadyExists.getErrorCode(),
        ProjectUtil.formatMessage(
            ResponseCode.userAlreadyExists.getErrorMessage(), exceptionMessage),
        ResponseCode.CLIENT_ERROR.getResponseCode());
  }

  private static void throwMandatoryParamMissingException(String param) {
    throw new ProjectCommonException(
        ResponseCode.mandatoryParamsMissing.getErrorCode(),
        ProjectUtil.formatMessage(ResponseCode.mandatoryParamsMissing.getErrorMessage(), param),
        ResponseCode.CLIENT_ERROR.getResponseCode());
  }

  private static void throwInvalidParamValueException(String param, String paramValue) {
    throw new ProjectCommonException(
        ResponseCode.invalidParameterValue.getErrorCode(),
        ProjectUtil.formatMessage(
            ResponseCode.invalidParameterValue.getErrorMessage(), paramValue, param),
        ResponseCode.CLIENT_ERROR.getResponseCode());
  }
}

@FunctionalInterface
interface ConvertValuesToLowerCase {
  Map<String, String> convertToLowerCase(Map<String, String> map);
}

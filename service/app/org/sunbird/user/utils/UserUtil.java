package org.sunbird.user.utils;

import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.sunbird.common.exception.ProjectCommonException;
import org.sunbird.common.models.response.Response;
import org.sunbird.common.models.util.JsonKey;
import org.sunbird.common.models.util.ProjectUtil;
import org.sunbird.common.models.util.datasecurity.EncryptionService;
import org.sunbird.common.responsecode.ResponseCode;
import org.sunbird.common.responsecode.ResponseMessage;
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
      throw new ProjectCommonException(
          ResponseCode.userAlreadyExists.getErrorCode(),
          ProjectUtil.formatMessage(
              ResponseCode.userAlreadyExists.getErrorMessage(),
              ProjectUtil.formatMessage(
                  ResponseMessage.Message.AND_FORMAT, JsonKey.USERNAME, JsonKey.CHANNEL)),
          ResponseCode.CLIENT_ERROR.getResponseCode());
    }
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
}

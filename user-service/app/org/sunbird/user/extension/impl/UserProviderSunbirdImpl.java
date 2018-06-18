package org.sunbird.user.extension.impl;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.sunbird.common.exception.ProjectCommonException;
import org.sunbird.common.models.util.JsonKey;
import org.sunbird.common.responsecode.ResponseCode;
import org.sunbird.common.responsecode.ResponseMessage;
import org.sunbird.extension.user.UserExtension;
import org.sunbird.user.services.UserService;
import org.sunbird.user.services.impl.UserServiceImpl;
import org.sunbird.user.utils.Constant;
import org.sunbird.user.validators.UserRequestValidator;

/**
 * This class implements UserExtension interface for sunbird.
 *
 * @author Amit Kumar
 */
public class UserProviderSunbirdImpl implements UserExtension {

  private UserRequestValidator userRequestValidator = new UserRequestValidator();
  private UserService userService = UserServiceImpl.getInstance();

  @Override
  public void preCreate(Map<String, Object> userMap) {
    userRequestValidator.validateCreateUser(userMap);
    if (StringUtils.isBlank((String) userMap.get(JsonKey.CHANNEL))) {
      userMap.put(JsonKey.CHANNEL, userService.getDefaultChannel());
    }

    userService.isUserExists(
        userService.getLoginId(
            (String) userMap.get(JsonKey.USERNAME), (String) userMap.get(JsonKey.CHANNEL)));

    if (StringUtils.isNotBlank((String) userMap.get(JsonKey.PHONE))
        && CollectionUtils.isNotEmpty(
            userService.getUserDetailsByPhone((String) userMap.get(JsonKey.PHONE)))) {
      ProjectCommonException.throwClientErrorException(
          ResponseCode.userAlreadyExists,
          MessageFormat.format(
              ResponseCode.userAlreadyExists.getErrorMessage(),
              (String) userMap.get(JsonKey.PHONE)));
    }

    if (StringUtils.isNotBlank((String) userMap.get(JsonKey.EMAIL))
        && CollectionUtils.isNotEmpty(
            userService.getUserDetailsByEmail((String) userMap.get(JsonKey.EMAIL)))) {
      ProjectCommonException.throwClientErrorException(
          ResponseCode.userAlreadyExists,
          MessageFormat.format(
              ResponseCode.userAlreadyExists.getErrorMessage(),
              (String) userMap.get(JsonKey.EMAIL)));
    }

    List<Map<String, Object>> externalIds =
        (List<Map<String, Object>>) userMap.get(Constant.EXTERNAL_IDS);
    if (CollectionUtils.isNotEmpty(externalIds)) {
      for (Map<String, Object> externalId : externalIds) {
        if (MapUtils.isNotEmpty(
            userService.getExternalIdDetails(
                (String) externalId.get(JsonKey.PROVIDER),
                (String) externalId.get(Constant.ID_TYPE),
                (String) externalId.get(JsonKey.ID)))) {
          ProjectCommonException.throwClientErrorException(
              ResponseCode.userAlreadyExists,
              MessageFormat.format(
                  ResponseMessage.Message.EXTERNAL_ID_FORMAT,
                  externalId.get(JsonKey.ID),
                  externalId.get(JsonKey.PROVIDER),
                  externalId.get(Constant.ID_TYPE)));
        }
      }
    }
  }

  @Override
  public void create(Map<String, Object> userMap) {}

  @Override
  public void preUpdate(Map<String, Object> userMap) {}

  @Override
  public void update(Map<String, Object> userMap) {}
}

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
import org.sunbird.user.utils.Constant;
import org.sunbird.user.validators.UserRequestValidator;

/**
 * This class implements UserExtension interface for sunbird.
 *
 * @author Amit Kumar
 */
public class UserProviderSunbirdImpl implements UserExtension {

  private UserRequestValidator userRequestValidator = new UserRequestValidator();

  @Override
  public void preCreate(Map<String, Object> userMap) {
    userRequestValidator.validateCreateUser(userMap);
    if (StringUtils.isBlank((String) userMap.get(JsonKey.CHANNEL))) {
      userMap.put(JsonKey.CHANNEL, UserService.getDefaultChannel());
    }

    UserService.isUserExist(
        UserService.getLoginId(
            (String) userMap.get(JsonKey.USERNAME), (String) userMap.get(JsonKey.CHANNEL)));

    if (StringUtils.isNotBlank((String) userMap.get(JsonKey.PHONE))
        && CollectionUtils.isNotEmpty(
            UserService.getUserDetailsByPhone((String) userMap.get(JsonKey.PHONE)))) {
      ProjectCommonException.throwClientErrorException(
          ResponseCode.userAlreadyExists,
          MessageFormat.format(
              ResponseCode.userAlreadyExists.getErrorMessage(),
              (String) userMap.get(JsonKey.PHONE)));
    }

    if (StringUtils.isNotBlank((String) userMap.get(JsonKey.EMAIL))
        && CollectionUtils.isNotEmpty(
            UserService.getUserDetailsByEmail((String) userMap.get(JsonKey.EMAIL)))) {
      ProjectCommonException.throwClientErrorException(
          ResponseCode.userAlreadyExists,
          MessageFormat.format(
              ResponseCode.userAlreadyExists.getErrorMessage(),
              (String) userMap.get(JsonKey.EMAIL)));
    }

    List<Map<String, Object>> externalIds =
        (List<Map<String, Object>>) userMap.get(Constant.EXTERNAL_IDS);
    if (CollectionUtils.isNotEmpty(
        (List<Map<String, Object>>) userMap.get(Constant.EXTERNAL_IDS))) {
      for (Map<String, Object> externalId : externalIds) {
        if (MapUtils.isNotEmpty(UserService.getExternalIdDetails(externalId))) {
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
  public void preUpdate(Map<String, Object> userMap) {
    userRequestValidator.validateUpdateUser(userMap);
    List<Map<String, Object>> externalIds =
        (List<Map<String, Object>>) userMap.get(Constant.EXTERNAL_IDS);
    if (CollectionUtils.isNotEmpty(
        (List<Map<String, Object>>) userMap.get(Constant.EXTERNAL_IDS))) {
      for (Map<String, Object> externalId : externalIds) {
        Map<String, Object> userExternalId = UserService.getExternalIdDetails(externalId);
        if (MapUtils.isNotEmpty(userExternalId)) {
          if (!(((String) userMap.get(JsonKey.USER_ID))
              .equalsIgnoreCase((String) userExternalId.get(JsonKey.USER_ID)))) {
            // If end user will try to add,edit or remove other user extIds throw exception
            ProjectCommonException.throwClientErrorException(
                ResponseCode.externalIdAssignedToOtherUser,
                MessageFormat.format(
                    ResponseCode.externalIdAssignedToOtherUser.getErrorMessage(),
                    userExternalId.get(JsonKey.ID),
                    userExternalId.get(Constant.ID_TYPE),
                    userExternalId.get(JsonKey.PROVIDER)));
          }
        } else {
          if (Constant.REMOVE.equalsIgnoreCase((String) externalId.get(JsonKey.OPERATION))) {
            ProjectCommonException.throwClientErrorException(
                ResponseCode.externalIdNotFound,
                MessageFormat.format(
                    ResponseCode.externalIdNotFound.getErrorMessage(),
                    externalId.get(JsonKey.ID),
                    externalId.get(Constant.ID_TYPE),
                    externalId.get(JsonKey.PROVIDER)));
          }
        }
      }
    }
  }

  @Override
  public void update(Map<String, Object> userMap) {}
}

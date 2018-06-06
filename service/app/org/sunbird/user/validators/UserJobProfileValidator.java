package org.sunbird.user.validators;

import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.sunbird.common.exception.ProjectCommonException;
import org.sunbird.common.models.util.JsonKey;
import org.sunbird.common.models.util.ProjectUtil;
import org.sunbird.common.responsecode.ResponseCode;
import org.sunbird.common.responsecode.ResponseMessage;

public class UserJobProfileValidator {

  private static final int ERROR_CODE = ResponseCode.CLIENT_ERROR.getResponseCode();

  private UserJobProfileValidator() {}

  /**
   * Method to validate jobProfile of a user
   *
   * @param userRequest
   */
  public static void jobProfileValidation(Map<String, Object> userRequest) {
    if (userRequest.containsKey(JsonKey.JOB_PROFILE)
        && null != userRequest.get(JsonKey.JOB_PROFILE)) {
      if (!(userRequest.get(JsonKey.JOB_PROFILE) instanceof List)) {
        throw new ProjectCommonException(
            ResponseCode.dataTypeError.getErrorCode(),
            ProjectUtil.formatMessage(
                ResponseCode.dataTypeError.getErrorMessage(), JsonKey.JOB_PROFILE, JsonKey.LIST),
            ERROR_CODE);
      } else if (userRequest.get(JsonKey.JOB_PROFILE) instanceof List) {
        validateJob(userRequest);
      }
    }
  }

  public static void validateJob(Map<String, Object> userRequest) {
    Map<String, Object> reqMap = null;
    List<Map<String, Object>> reqList =
        (List<Map<String, Object>>) userRequest.get(JsonKey.JOB_PROFILE);
    if (CollectionUtils.isNotEmpty(reqList))
      for (int i = 0; i < reqList.size(); i++) {
        reqMap = reqList.get(i);
        if (null != reqMap.get(JsonKey.JOINING_DATE)) {
          boolean bool =
              ProjectUtil.isDateValidFormat(
                  ProjectUtil.YEAR_MONTH_DATE_FORMAT, (String) reqMap.get(JsonKey.JOINING_DATE));
          if (!bool) {
            throwDateFormatError();
          }
        }
        if (null != reqMap.get(JsonKey.END_DATE)) {
          boolean bool =
              ProjectUtil.isDateValidFormat(
                  ProjectUtil.YEAR_MONTH_DATE_FORMAT, (String) reqMap.get(JsonKey.END_DATE));
          if (!bool) {
            throwDateFormatError();
          }
        }
        throwMandatoryParamMissingException(
            ProjectUtil.formatMessage(
                ResponseMessage.Message.DOT_FORMAT, JsonKey.JOB_PROFILE, JsonKey.JOB_NAME),
            (String) reqMap.get(JsonKey.JOB_NAME));
        throwMandatoryParamMissingException(
            ProjectUtil.formatMessage(
                ResponseMessage.Message.DOT_FORMAT, JsonKey.JOB_PROFILE, JsonKey.ORG_NAME),
            (String) reqMap.get(JsonKey.ORG_NAME));
        if (reqMap.containsKey(JsonKey.ADDRESS) && null != reqMap.get(JsonKey.ADDRESS)) {
          UserAddressValidator.validateAddress(
              (Map<String, Object>) reqMap.get(JsonKey.ADDRESS), JsonKey.JOB_PROFILE);
        }
      }
  }

  private static void throwDateFormatError() {
    throw new ProjectCommonException(
        ResponseCode.dateFormatError.getErrorCode(),
        ResponseCode.dateFormatError.getErrorMessage(),
        ERROR_CODE);
  }

  public static void validateUpdateUserJobProfile(Map<String, Object> userRequest) {
    List<Map<String, Object>> reqList =
        (List<Map<String, Object>>) userRequest.get(JsonKey.JOB_PROFILE);
    if (CollectionUtils.isNotEmpty(reqList))
      for (int i = 0; i < reqList.size(); i++) {
        Map<String, Object> reqMap = reqList.get(i);
        if (reqMap.containsKey(JsonKey.IS_DELETED)
            && null != reqMap.get(JsonKey.IS_DELETED)
            && ((boolean) reqMap.get(JsonKey.IS_DELETED))) {
          throwMandatoryParamMissingException(
              ProjectUtil.formatMessage(
                  ResponseMessage.Message.DOT_FORMAT, JsonKey.JOB_PROFILE, JsonKey.ID),
              (String) reqMap.get(JsonKey.ID));
        }
        if (!reqMap.containsKey(JsonKey.IS_DELETED)
            || (reqMap.containsKey(JsonKey.IS_DELETED)
                && (null == reqMap.get(JsonKey.IS_DELETED)
                    || !(boolean) reqMap.get(JsonKey.IS_DELETED)))) {
          jobProfileValidation(userRequest);
        }
      }
  }

  private static void throwMandatoryParamMissingException(String paramName, String paramValue) {
    if (StringUtils.isBlank(paramValue))
      throw new ProjectCommonException(
          ResponseCode.mandatoryParamsMissing.getErrorCode(),
          ProjectUtil.formatMessage(
              ResponseCode.mandatoryParamsMissing.getErrorMessage(), paramName),
          ERROR_CODE);
  }
}

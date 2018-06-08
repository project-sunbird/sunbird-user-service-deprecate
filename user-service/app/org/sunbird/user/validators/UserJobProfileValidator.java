package org.sunbird.user.validators;

import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.sunbird.common.exception.ProjectCommonException;
import org.sunbird.common.models.util.JsonKey;
import org.sunbird.common.models.util.ProjectUtil;
import org.sunbird.common.responsecode.ResponseCode;
import org.sunbird.common.responsecode.ResponseMessage;

/**
 * This class provide helper methods to validate user job profile request.
 *
 * @author Amit Kumar
 */
public class UserJobProfileValidator extends BaseValidator {

  private UserJobProfileValidator() {}

  /**
   * Method to validate job profile of user
   *
   * @param userRequest
   */
  public static void jobProfileValidation(Map<String, Object> userRequest, String operation) {
    if (userRequest.containsKey(JsonKey.JOB_PROFILE)
        && null != userRequest.get(JsonKey.JOB_PROFILE)) {
      if (!(userRequest.get(JsonKey.JOB_PROFILE) instanceof List)) {
        throw new ProjectCommonException(
            ResponseCode.dataTypeError.getErrorCode(),
            ProjectUtil.formatMessage(
                ResponseCode.dataTypeError.getErrorMessage(), JsonKey.JOB_PROFILE, JsonKey.LIST),
            ERROR_CODE);
      } else if (userRequest.get(JsonKey.JOB_PROFILE) instanceof List) {
        List<Map<String, Object>> reqList =
            (List<Map<String, Object>>) userRequest.get(JsonKey.JOB_PROFILE);
        if (CollectionUtils.isNotEmpty(reqList))
          for (int i = 0; i < reqList.size(); i++) {
            validateJobProfile(reqList.get(i), operation);
          }
      }
    }
  }

  /**
   * Method to validate individual job profile request.
   *
   * @param jobProfile Job profile details.
   */
  public static void validateJobProfile(Map<String, Object> jobProfile, String operation) {
    if (MapUtils.isNotEmpty(jobProfile)) {
      validateDeletedEntity(jobProfile, operation, JsonKey.JOB_PROFILE);
      validateJoiningAndJobEndDateFormat(jobProfile);
      throwMandatoryParamMissingException(
          ProjectUtil.formatMessage(
              ResponseMessage.Message.DOT_FORMAT, JsonKey.JOB_PROFILE, JsonKey.JOB_NAME),
          (String) jobProfile.get(JsonKey.JOB_NAME));
      throwMandatoryParamMissingException(
          ProjectUtil.formatMessage(
              ResponseMessage.Message.DOT_FORMAT, JsonKey.JOB_PROFILE, JsonKey.ORG_NAME),
          (String) jobProfile.get(JsonKey.ORG_NAME));
      if (jobProfile.containsKey(JsonKey.ADDRESS) && null != jobProfile.get(JsonKey.ADDRESS)) {
        UserAddressValidator.validateAddress(
            (Map<String, Object>) jobProfile.get(JsonKey.ADDRESS), JsonKey.JOB_PROFILE, operation);
      }
    }
  }

  private static void validateJoiningAndJobEndDateFormat(Map<String, Object> reqMap) {
    if (null != reqMap.get(JsonKey.JOINING_DATE)) {
      boolean isValidJoiningDate =
          ProjectUtil.isDateValidFormat(
              ProjectUtil.YEAR_MONTH_DATE_FORMAT, (String) reqMap.get(JsonKey.JOINING_DATE));
      if (!isValidJoiningDate) {
        throwDateFormatError();
      }
    }
    if (null != reqMap.get(JsonKey.END_DATE)) {
      boolean isValidJobEndDate =
          ProjectUtil.isDateValidFormat(
              ProjectUtil.YEAR_MONTH_DATE_FORMAT, (String) reqMap.get(JsonKey.END_DATE));
      if (!isValidJobEndDate) {
        throwDateFormatError();
      }
    }
  }

  private static void throwDateFormatError() {
    throw new ProjectCommonException(
        ResponseCode.dateFormatError.getErrorCode(),
        ResponseCode.dateFormatError.getErrorMessage(),
        ERROR_CODE);
  }
}

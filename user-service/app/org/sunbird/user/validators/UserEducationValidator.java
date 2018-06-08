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
 * This class provides helper methods to validate user education request.
 *
 * @author Amit Kumar
 */
public class UserEducationValidator extends BaseValidator {

  private UserEducationValidator() {}

  /**
   * Method to validate educational details of the user
   *
   * @param userRequest
   */
  @SuppressWarnings("unchecked")
  public static void educationValidation(Map<String, Object> userRequest, String operation) {
    if (userRequest.containsKey(JsonKey.EDUCATION) && null != userRequest.get(JsonKey.EDUCATION)) {
      if (!(userRequest.get(JsonKey.EDUCATION) instanceof List)) {
        throw new ProjectCommonException(
            ResponseCode.dataTypeError.getErrorCode(),
            ProjectUtil.formatMessage(
                ResponseCode.dataTypeError.getErrorMessage(), JsonKey.EDUCATION, JsonKey.LIST),
            ERROR_CODE);
      } else if (userRequest.get(JsonKey.EDUCATION) instanceof List) {
        List<Map<String, Object>> reqList =
            (List<Map<String, Object>>) userRequest.get(JsonKey.EDUCATION);
        if (CollectionUtils.isNotEmpty(reqList))
          for (int i = 0; i < reqList.size(); i++) {
            validateEducation(reqList.get(i), operation);
          }
      }
    }
  }

  /**
   * Method to validate individual education request
   *
   * @param education Education details
   */
  private static void validateEducation(Map<String, Object> education, String operation) {
    if (MapUtils.isNotEmpty(education)) {
      validateDeletedEntity(education, operation, JsonKey.EDUCATION);
      throwMandatoryParamMissingException(
          ProjectUtil.formatMessage(
              ResponseMessage.Message.DOT_FORMAT, JsonKey.EDUCATION, JsonKey.NAME),
          (String) education.get(JsonKey.NAME));
      throwMandatoryParamMissingException(
          ProjectUtil.formatMessage(
              ResponseMessage.Message.DOT_FORMAT, JsonKey.EDUCATION, JsonKey.DEGREE),
          (String) education.get(JsonKey.DEGREE));
      if (education.containsKey(JsonKey.ADDRESS) && null != education.get(JsonKey.ADDRESS)) {
        UserAddressValidator.validateAddress(
            (Map<String, Object>) education.get(JsonKey.ADDRESS), JsonKey.EDUCATION, operation);
      }
    }
  }
}

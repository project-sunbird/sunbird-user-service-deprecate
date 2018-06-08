package org.sunbird.user.validators;

import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.sunbird.common.exception.ProjectCommonException;
import org.sunbird.common.models.util.JsonKey;
import org.sunbird.common.models.util.PhoneValidator;
import org.sunbird.common.responsecode.ResponseCode;

/**
 * This class contains method to validate basic user fields like firstName,DOB,email,phone...
 *
 * @author Amit Kumar
 */
public class BasicUserValidator extends BaseValidator {

  /**
   * Validate phone number details in user request.
   *
   * @param User details
   */
  public void validatePhone(Map<String, Object> userRequest) {
    if (StringUtils.isNotBlank((String) userRequest.get(JsonKey.PHONE))) {
      PhoneValidator.validatePhoneNumber(
          (String) userRequest.get(JsonKey.PHONE), (String) userRequest.get(JsonKey.COUNTRY_CODE));
      Object phoneVerified = userRequest.get(JsonKey.PHONE_VERIFIED);
      if (null == phoneVerified
          || (!(phoneVerified instanceof Boolean))
          || (!((Boolean) phoneVerified))) {
        createExceptionByResponseCode(ResponseCode.phoneVerifiedError, ERROR_CODE);
      }
    }
  }

  /**
   * This method will do basic validation for user request object.
   *
   * @param userRequest
   */
  public void userBasicValidation(Map<String, Object> userRequest, String operation) {

    if (operation.equalsIgnoreCase(JsonKey.CREATE)) {
      checkMandatoryFieldsPresent(userRequest, JsonKey.USERNAME, JsonKey.FIRST_NAME);
      if ((StringUtils.isBlank((String) userRequest.get(JsonKey.EMAIL))
          && StringUtils.isBlank((String) userRequest.get(JsonKey.PHONE)))) {
        throw new ProjectCommonException(
            ResponseCode.emailorPhoneRequired.getErrorCode(),
            ResponseCode.emailorPhoneRequired.getErrorMessage(),
            ERROR_CODE);
      }
    }
    validateDateParam((String) userRequest.get(JsonKey.DOB));
    validateListParam(userRequest, JsonKey.ROLES, JsonKey.LANGUAGE);
  }
}

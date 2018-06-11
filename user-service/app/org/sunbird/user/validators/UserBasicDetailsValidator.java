package org.sunbird.user.validators;

import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.sunbird.common.exception.ProjectCommonException;
import org.sunbird.common.models.util.JsonKey;
import org.sunbird.common.models.util.PhoneValidator;
import org.sunbird.common.responsecode.ResponseCode;

/**
 * Validates basic details (e.g. phone, date of birth) of User.
 *
 * @author Amit Kumar
 */
public class UserBasicDetailsValidator extends UserBaseRequestValidator {

  /**
   * Validates phone in user request.
   *
   * @param userRequest User details
   */
  private void validatePhone(Map<String, Object> userRequest) {
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
   * Validates basic details of user.
   *
   * @param userRequest User details
   */
  public void validateBasicDetails(Map<String, Object> userRequest, String operation) {

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
    validatePhone(userRequest);
    validateDateParam((String) userRequest.get(JsonKey.DOB));
    validateListParam(userRequest, JsonKey.ROLES, JsonKey.LANGUAGE);
  }
}

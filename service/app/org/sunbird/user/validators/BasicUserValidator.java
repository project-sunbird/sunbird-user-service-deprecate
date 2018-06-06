package org.sunbird.user.validators;

import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.sunbird.common.exception.ProjectCommonException;
import org.sunbird.common.models.util.JsonKey;
import org.sunbird.common.models.util.ProjectUtil;
import org.sunbird.common.responsecode.ResponseCode;

/**
 * This class contains method to validate basic user fields like firstName,DOB,email,phone...
 *
 * @author Amit Kumar
 */
public class BasicUserValidator {

  private static final int ERROR_CODE = ResponseCode.CLIENT_ERROR.getResponseCode();

  private BasicUserValidator() {}
  /**
   * Helper method which throws an exception if request contains some parameter which is not
   * allowed.
   *
   * @param fields List of requested fields which are not allowed for request processing.
   * @param request Request parameter and its value as a Map.
   */
  public static void fieldsNotAllowed(List<String> fields, Map<String, Object> request) {
    for (String field : fields) {
      if (StringUtils.isNotBlank((String) request.get(field))) {
        throw new ProjectCommonException(
            ResponseCode.invalidRequestParameter.getErrorCode(),
            ProjectUtil.formatMessage(
                ResponseCode.invalidRequestParameter.getErrorMessage(), field),
            ERROR_CODE);
      }
    }
  }

  /**
   * This method will validate phone number and its country code using google phone library
   *
   * @param userRequest
   */
  public static void phoneValidation(Map<String, Object> userRequest) {
    if (StringUtils.isNotBlank((String) userRequest.get(JsonKey.PHONE))) {
      validatePhoneNo(
          (String) userRequest.get(JsonKey.PHONE), (String) userRequest.get(JsonKey.COUNTRY_CODE));
    }
    if (!StringUtils.isBlank((String) userRequest.get(JsonKey.PHONE))) {
      if (null != userRequest.get(JsonKey.PHONE_VERIFIED)) {
        if (userRequest.get(JsonKey.PHONE_VERIFIED) instanceof Boolean) {
          if (!((boolean) userRequest.get(JsonKey.PHONE_VERIFIED))) {
            throwPhoneVerifiedException();
          }
        } else {
          throwPhoneVerifiedException();
        }
      } else {
        throwPhoneVerifiedException();
      }
    }
  }

  private static void throwPhoneVerifiedException() {
    throwPhoneVerifiedException();
  }

  private static boolean validatePhoneNo(String phone, String countryCode) {
    if (phone.contains("+")) {
      throw new ProjectCommonException(
          ResponseCode.invalidPhoneNumber.getErrorCode(),
          ResponseCode.invalidPhoneNumber.getErrorMessage(),
          ERROR_CODE);
    }
    if (StringUtils.isNotBlank(countryCode)) {
      boolean bool = ProjectUtil.validateCountryCode(countryCode);
      if (!bool) {
        throw new ProjectCommonException(
            ResponseCode.invalidCountryCode.getErrorCode(),
            ResponseCode.invalidCountryCode.getErrorMessage(),
            ERROR_CODE);
      }
    }
    if (ProjectUtil.validatePhone(phone, countryCode)) {
      return true;
    } else {
      throw new ProjectCommonException(
          ResponseCode.phoneNoFormatError.getErrorCode(),
          ResponseCode.phoneNoFormatError.getErrorMessage(),
          ERROR_CODE);
    }
  }

  /**
   * This method will do basic validation for user request object.
   *
   * @param userRequest
   */
  public static void userBasicValidation(Map<String, Object> userRequest, String operation) {

    if (operation.equalsIgnoreCase(JsonKey.CREATE)
        && StringUtils.isBlank((String) userRequest.get(JsonKey.USERNAME))) {
      throwMandatoryParamMissingException(JsonKey.USERNAME);
    }

    if (operation.equalsIgnoreCase(JsonKey.CREATE)
        && StringUtils.isBlank((String) userRequest.get(JsonKey.FIRST_NAME))) {
      throwMandatoryParamMissingException(JsonKey.FIRST_NAME);
    }

    if (userRequest.containsKey(JsonKey.ROLES)
        && null != userRequest.get(JsonKey.ROLES)
        && !(userRequest.get(JsonKey.ROLES) instanceof List)) {
      throwInvalidDataTypeException(JsonKey.ROLES);
    }
    if (userRequest.containsKey(JsonKey.LANGUAGE)
        && null != userRequest.get(JsonKey.LANGUAGE)
        && !(userRequest.get(JsonKey.LANGUAGE) instanceof List)) {
      throwInvalidDataTypeException(JsonKey.LANGUAGE);
    }

    if (null != userRequest.get(JsonKey.DOB)) {
      boolean bool =
          ProjectUtil.isDateValidFormat(
              ProjectUtil.YEAR_MONTH_DATE_FORMAT, (String) userRequest.get(JsonKey.DOB));
      if (!bool) {
        throw new ProjectCommonException(
            ResponseCode.dateFormatError.getErrorCode(),
            ResponseCode.dateFormatError.getErrorMessage(),
            ERROR_CODE);
      }
    }

    if (operation.equalsIgnoreCase(JsonKey.CREATE)
        && (StringUtils.isBlank((String) userRequest.get(JsonKey.EMAIL))
            && StringUtils.isBlank((String) userRequest.get(JsonKey.PHONE)))) {
      throw new ProjectCommonException(
          ResponseCode.emailorPhoneRequired.getErrorCode(),
          ResponseCode.emailorPhoneRequired.getErrorMessage(),
          ERROR_CODE);
    }

    if (StringUtils.isNotBlank((String) userRequest.get(JsonKey.EMAIL))
        && !ProjectUtil.isEmailvalid((String) userRequest.get(JsonKey.EMAIL))) {
      throw new ProjectCommonException(
          ResponseCode.emailFormatError.getErrorCode(),
          ResponseCode.emailFormatError.getErrorMessage(),
          ERROR_CODE);
    }
  }

  private static void throwInvalidDataTypeException(String parameter) {
    throw new ProjectCommonException(
        ResponseCode.dataTypeError.getErrorCode(),
        ProjectUtil.formatMessage(
            ResponseCode.dataTypeError.getErrorMessage(), parameter, JsonKey.LIST),
        ERROR_CODE);
  }

  private static void throwMandatoryParamMissingException(String paramName) {
    throw new ProjectCommonException(
        ResponseCode.mandatoryParamsMissing.getErrorCode(),
        ProjectUtil.formatMessage(ResponseCode.mandatoryParamsMissing.getErrorMessage(), paramName),
        ERROR_CODE);
  }
}

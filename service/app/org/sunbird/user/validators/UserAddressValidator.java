package org.sunbird.user.validators;

import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.sunbird.common.exception.ProjectCommonException;
import org.sunbird.common.models.util.JsonKey;
import org.sunbird.common.models.util.ProjectUtil;
import org.sunbird.common.models.util.ProjectUtil.AddressType;
import org.sunbird.common.responsecode.ResponseCode;
import org.sunbird.common.responsecode.ResponseMessage;

public class UserAddressValidator {

  private static final int ERROR_CODE = ResponseCode.CLIENT_ERROR.getResponseCode();

  private UserAddressValidator() {}

  /**
   * Method to validate Address
   *
   * @param userRequest
   */
  @SuppressWarnings("unchecked")
  public static void addressValidation(Map<String, Object> userRequest) {
    Map<String, Object> addrReqMap;
    if (userRequest.containsKey(JsonKey.ADDRESS) && null != userRequest.get(JsonKey.ADDRESS)) {
      if (!(userRequest.get(JsonKey.ADDRESS) instanceof List)) {
        throw new ProjectCommonException(
            ResponseCode.dataTypeError.getErrorCode(),
            ProjectUtil.formatMessage(
                ResponseCode.dataTypeError.getErrorMessage(), JsonKey.ADDRESS, JsonKey.LIST),
            ERROR_CODE);
      } else if (userRequest.get(JsonKey.ADDRESS) instanceof List) {
        List<Map<String, Object>> reqList =
            (List<Map<String, Object>>) userRequest.get(JsonKey.ADDRESS);
        for (int i = 0; i < reqList.size(); i++) {
          addrReqMap = reqList.get(i);
          validateAddress(addrReqMap, JsonKey.USER);
        }
      }
    }
  }

  public static void validateAddress(Map<String, Object> address, String type) {
    throwAddressException(JsonKey.ADDRESS_LINE1, (String) address.get(JsonKey.ADDRESS_LINE1), type);
    throwAddressException(JsonKey.CITY, (String) address.get(JsonKey.CITY), type);

    if (address.containsKey(JsonKey.ADD_TYPE) && type.equals(JsonKey.USER)) {
      throwAddressException(JsonKey.ADD_TYPE, (String) address.get(JsonKey.ADD_TYPE), type);

      if (!StringUtils.isBlank((String) address.get(JsonKey.ADD_TYPE))
          && !checkAddressType((String) address.get(JsonKey.ADD_TYPE))) {
        throw new ProjectCommonException(
            ResponseCode.addressTypeError.getErrorCode(),
            ResponseCode.addressTypeError.getErrorMessage(),
            ERROR_CODE);
      }
    }
  }

  private static void throwAddressException(String paramName, String paramValue, String type) {
    if (StringUtils.isEmpty(paramValue))
      throw new ProjectCommonException(
          ResponseCode.addressError.getErrorCode(),
          ProjectUtil.formatMessage(ResponseCode.addressError.getErrorMessage(), type, paramName),
          ERROR_CODE);
  }

  public static void validateUpdateUserAddress(Map<String, Object> userRequest) {
    List<Map<String, Object>> reqList =
        (List<Map<String, Object>>) userRequest.get(JsonKey.ADDRESS);
    if (CollectionUtils.isNotEmpty(reqList))
      for (int i = 0; i < reqList.size(); i++) {
        Map<String, Object> reqMap = reqList.get(i);

        if (reqMap.containsKey(JsonKey.IS_DELETED)
            && null != reqMap.get(JsonKey.IS_DELETED)
            && ((boolean) reqMap.get(JsonKey.IS_DELETED))) {
          throwMandatoryParamMissingException(
              ProjectUtil.formatMessage(
                  ResponseMessage.Message.DOT_FORMAT, JsonKey.ADDRESS, JsonKey.ID),
              (String) reqMap.get(JsonKey.ID));
        }
        if (!reqMap.containsKey(JsonKey.IS_DELETED)
            || (reqMap.containsKey(JsonKey.IS_DELETED)
                && (null == reqMap.get(JsonKey.IS_DELETED)
                    || !(boolean) reqMap.get(JsonKey.IS_DELETED)))) {
          validateAddress(reqMap, JsonKey.USER);
        }
      }
  }

  private static boolean checkAddressType(String addrType) {
    for (AddressType type : AddressType.values()) {
      if (type.getTypeName().equals(addrType)) {
        return true;
      }
    }
    return false;
  }

  private static void throwMandatoryParamMissingException(String paramName, String paramValue) {
    throw new ProjectCommonException(
        ResponseCode.mandatoryParamsMissing.getErrorCode(),
        ProjectUtil.formatMessage(ResponseCode.mandatoryParamsMissing.getErrorMessage(), paramName),
        ERROR_CODE);
  }
}

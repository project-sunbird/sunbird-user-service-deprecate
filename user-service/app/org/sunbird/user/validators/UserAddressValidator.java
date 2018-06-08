package org.sunbird.user.validators;

import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.sunbird.common.exception.ProjectCommonException;
import org.sunbird.common.models.util.JsonKey;
import org.sunbird.common.models.util.ProjectUtil;
import org.sunbird.common.models.util.ProjectUtil.AddressType;
import org.sunbird.common.responsecode.ResponseCode;
import org.sunbird.common.responsecode.ResponseMessage;

/**
 * This class provide helper method to validate user address request.
 *
 * @author Amit Kumar
 */
public class UserAddressValidator extends BaseValidator {

  private UserAddressValidator() {}

  /**
   * Method to validate user Address
   *
   * @param userRequest
   */
  @SuppressWarnings("unchecked")
  public static void addressValidation(Map<String, Object> userRequest, String operation) {
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
        if (CollectionUtils.isNotEmpty(reqList))
          for (int i = 0; i < reqList.size(); i++) {
            validateAddress(reqList.get(i), JsonKey.USER, operation);
          }
      }
    }
  }

  /**
   * Method to validate individual address request
   *
   * @param address
   * @param type
   */
  public static void validateAddress(Map<String, Object> address, String type, String operation) {
    if (MapUtils.isNotEmpty(address)) {
      // if isDeleted flag is true and addressId is missing throw exception
      validateDeletedEntity(address, operation, JsonKey.ADDRESS);
      throwAddressException(
          JsonKey.ADDRESS_LINE1, (String) address.get(JsonKey.ADDRESS_LINE1), type);
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
  }

  private static void throwAddressException(String paramName, String paramValue, String type) {
    String param =
        ProjectUtil.formatMessage(
            ResponseMessage.Message.DOT_FORMAT,
            (ProjectUtil.formatMessage(ResponseMessage.Message.DOT_FORMAT, type, JsonKey.ADDRESS)),
            paramName);
    throwMandatoryParamMissingException(param, paramValue);
  }

  private static boolean checkAddressType(String addrType) {
    for (AddressType type : AddressType.values()) {
      if (type.getTypeName().equals(addrType)) {
        return true;
      }
    }
    return false;
  }
}

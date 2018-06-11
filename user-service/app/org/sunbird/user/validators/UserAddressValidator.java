package org.sunbird.user.validators;

import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.sunbird.common.exception.ProjectCommonException;
import org.sunbird.common.models.util.JsonKey;
import org.sunbird.common.models.util.ProjectUtil;
import org.sunbird.common.responsecode.ResponseCode;
import org.sunbird.common.responsecode.ResponseMessage;
import org.sunbird.user.models.entity.Address.AddressType;

/**
 * Validates each address mentioned in user request.
 *
 * @author Amit Kumar
 */
public class UserAddressValidator extends UserBaseRequestValidator {

  /**
   * Validates each address in user request.
   *
   * @param userRequest User details.
   */
  public void validateAddress(Map<String, Object> userRequest, String operation) {
    validateListParam(userRequest, JsonKey.ADDRESS);
    List<Map<String, Object>> addressList =
        (List<Map<String, Object>>) userRequest.get(JsonKey.ADDRESS);
    if (CollectionUtils.isNotEmpty(addressList))
      for (int i = 0; i < addressList.size(); i++) {
        validateAddressField(addressList.get(i), JsonKey.USER, operation);
      }
  }

  /**
   * Validate given address.
   *
   * @param address Address details
   * @param type Entity type (e.g. user, education) containing given address
   * @param operation Type of operation (e.g. CREATE, UPDATE)
   */
  public void validateAddressField(Map<String, Object> address, String type, String operation) {
    if (MapUtils.isNotEmpty(address)) {
      validateDeletion(address, operation, JsonKey.ADDRESS);
      throwAddressException(address, JsonKey.ADDRESS_LINE1, type);
      throwAddressException(address, JsonKey.CITY, type);
      validateAddressType(address, type);
    }
  }

  private void throwAddressException(Map<String, Object> address, String paramName, String type) {
    String exceptionMsg =
        ProjectUtil.formatMessage(
            ResponseMessage.Message.DOT_FORMAT,
            (ProjectUtil.formatMessage(ResponseMessage.Message.DOT_FORMAT, type, JsonKey.ADDRESS)),
            paramName);
    checkMandatoryParamsPresent(address, exceptionMsg, paramName);
  }

  /**
   * Validate address type.
   *
   * @param address Address details.
   * @param entityType Entity type (e.g. user, education) containing given address
   */
  private void validateAddressType(Map<String, Object> address, String entityType) {
    if (address.containsKey(JsonKey.ADD_TYPE) && entityType.equals(JsonKey.USER)) {
      throwAddressException(address, JsonKey.ADD_TYPE, entityType);
      if (!StringUtils.isBlank((String) address.get(JsonKey.ADD_TYPE))) {
        for (AddressType addressType : AddressType.values()) {
          if (!(addressType.getTypeName().equals(address.get(JsonKey.ADD_TYPE)))) {
            throw new ProjectCommonException(
                ResponseCode.addressTypeError.getErrorCode(),
                ResponseCode.addressTypeError.getErrorMessage(),
                ERROR_CODE);
          }
        }
      }
    }
  }
}

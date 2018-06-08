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

/**
 * This class provide helper method to validate user address request.
 *
 * @author Amit Kumar
 */
public class UserAddressValidator extends BaseValidator {

  /**
   * Method to validate user Address
   *
   * @param userRequest
   */
  @SuppressWarnings("unchecked")
  public void validateAddress(Map<String, Object> userRequest, String operation) {
    validateListParam(userRequest, JsonKey.ADDRESS);
    List<Map<String, Object>> reqList =
        (List<Map<String, Object>>) userRequest.get(JsonKey.ADDRESS);
    if (CollectionUtils.isNotEmpty(reqList))
      for (int i = 0; i < reqList.size(); i++) {
        validateAddressElement(reqList.get(i), JsonKey.USER, operation);
      }
  }

  /**
   * Method to validate individual address request
   *
   * @param address Address details
   * @param type For which entity address belongs to i.e for education , job_profile,etc
   */
  public void validateAddressElement(Map<String, Object> address, String type, String operation) {
    if (MapUtils.isNotEmpty(address)) {
      // if isDeleted flag is true and addressId is missing throw exception
      validateDeletedEntity(address, operation, JsonKey.ADDRESS);
      throwAddressException(address, JsonKey.ADDRESS_LINE1, type);
      throwAddressException(address, JsonKey.CITY, type);

      if (address.containsKey(JsonKey.ADD_TYPE) && type.equals(JsonKey.USER)) {
        throwAddressException(address, JsonKey.ADD_TYPE, type);

        if (!StringUtils.isBlank((String) address.get(JsonKey.ADD_TYPE))
            && !validateAddressType((String) address.get(JsonKey.ADD_TYPE))) {
          throw new ProjectCommonException(
              ResponseCode.addressTypeError.getErrorCode(),
              ResponseCode.addressTypeError.getErrorMessage(),
              ERROR_CODE);
        }
      }
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

  private boolean validateAddressType(String addrType) {
    for (AddressType type : AddressType.values()) {
      if (type.getTypeName().equals(addrType)) {
        return true;
      }
    }
    return false;
  }

  /** This ENUM will hold all the Address type name. */
  public enum AddressType {
    PERMANENT("permanent"),
    CURRENT("current"),
    OFFICE("office"),
    HOME("home");
    private String typeName;

    private AddressType(String name) {
      this.typeName = name;
    }

    public String getTypeName() {
      return typeName;
    }
  }
}

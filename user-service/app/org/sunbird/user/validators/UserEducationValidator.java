package org.sunbird.user.validators;

import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.sunbird.common.models.util.JsonKey;
import org.sunbird.common.models.util.ProjectUtil;
import org.sunbird.common.responsecode.ResponseMessage;

/**
 * This class provides helper methods to validate user education request.
 *
 * @author Amit Kumar
 */
public class UserEducationValidator extends BaseValidator {
  private UserAddressValidator userAddressValidator = null;

  public UserEducationValidator() {
    userAddressValidator = new UserAddressValidator();
  }
  /**
   * Method to validate educational details of the user
   *
   * @param userRequest
   */
  @SuppressWarnings("unchecked")
  public void validateEducation(Map<String, Object> userRequest, String operation) {
    validateListParam(userRequest, JsonKey.EDUCATION);
    List<Map<String, Object>> reqList =
        (List<Map<String, Object>>) userRequest.get(JsonKey.EDUCATION);
    if (CollectionUtils.isNotEmpty(reqList))
      for (int i = 0; i < reqList.size(); i++) {
        validateEducationEntity(reqList.get(i), operation);
      }
  }

  /**
   * Method to validate individual education request
   *
   * @param education Education details
   * @param operation Operation type(create/update)
   */
  private void validateEducationEntity(Map<String, Object> education, String operation) {
    if (MapUtils.isNotEmpty(education)) {
      validateDeletedEntity(education, operation, JsonKey.EDUCATION);
      checkMandatoryParamsPresent(
          education,
          ProjectUtil.formatMessage(
              ResponseMessage.Message.DOT_FORMAT, JsonKey.EDUCATION, JsonKey.NAME),
          JsonKey.NAME);
      checkMandatoryParamsPresent(
          education,
          ProjectUtil.formatMessage(
              ResponseMessage.Message.DOT_FORMAT, JsonKey.EDUCATION, JsonKey.DEGREE),
          JsonKey.DEGREE);
      if (education.containsKey(JsonKey.ADDRESS) && null != education.get(JsonKey.ADDRESS)) {
        userAddressValidator.validateAddressElement(
            (Map<String, Object>) education.get(JsonKey.ADDRESS), JsonKey.EDUCATION, operation);
      }
    }
  }
}

package org.sunbird.user.validators;

import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.sunbird.common.models.util.JsonKey;
import org.sunbird.common.models.util.ProjectUtil;
import org.sunbird.common.responsecode.ResponseMessage;

/**
 * Validates education details of User.
 *
 * @author Amit Kumar
 */
public class UserEducationValidator extends UserBaseRequestValidator {
  private UserAddressValidator userAddressValidator = null;

  public UserEducationValidator() {
    userAddressValidator = new UserAddressValidator();
  }
  /**
   * Validates each education details in user request.
   *
   * @param userRequest User details.
   */
  @SuppressWarnings("unchecked")
  public void validateEducation(Map<String, Object> userRequest, String operation) {
    validateListParam(userRequest, JsonKey.EDUCATION);
    List<Map<String, Object>> reqList =
        (List<Map<String, Object>>) userRequest.get(JsonKey.EDUCATION);
    if (CollectionUtils.isNotEmpty(reqList))
      for (int i = 0; i < reqList.size(); i++) {
        validateEducationElement(reqList.get(i), operation);
      }
  }

  /**
   * Validates given education details.
   *
   * @param education Education details
   * @param operation Type of operation (e.g. CREATE, UPDATE)
   */
  private void validateEducationElement(Map<String, Object> education, String operation) {
    if (MapUtils.isNotEmpty(education)) {
      validateDeletion(education, operation, JsonKey.EDUCATION);
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

      userAddressValidator.validateAddressField(
          (Map<String, Object>) education.get(JsonKey.ADDRESS), JsonKey.EDUCATION, operation);
    }
  }
}

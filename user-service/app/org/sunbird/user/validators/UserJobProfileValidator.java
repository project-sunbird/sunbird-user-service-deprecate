package org.sunbird.user.validators;

import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.sunbird.common.models.util.JsonKey;
import org.sunbird.common.models.util.ProjectUtil;
import org.sunbird.common.responsecode.ResponseMessage;

/**
 * This class provide helper methods to validate user job profile request.
 *
 * @author Amit Kumar
 */
public class UserJobProfileValidator extends UserBaseRequestValidator {
  private UserAddressValidator userAddressValidator = null;

  public UserJobProfileValidator() {
    userAddressValidator = new UserAddressValidator();
  }
  /**
   * Method to validate job profile of user
   *
   * @param userRequest
   */
  public void validateJobProfile(Map<String, Object> userRequest, String operation) {
    validateListParam(userRequest, JsonKey.JOB_PROFILE);
    List<Map<String, Object>> reqList =
        (List<Map<String, Object>>) userRequest.get(JsonKey.JOB_PROFILE);
    if (CollectionUtils.isNotEmpty(reqList))
      for (int i = 0; i < reqList.size(); i++) {
        validateJobProfileEntity(reqList.get(i), operation);
      }
  }

  /**
   * Method to validate individual job profile request.
   *
   * @param jobProfile Job profile details.
   */
  public void validateJobProfileEntity(Map<String, Object> jobProfile, String operation) {
    if (MapUtils.isNotEmpty(jobProfile)) {
      validateDeletion(jobProfile, operation, JsonKey.JOB_PROFILE);
      validateJoiningAndJobEndDateFormat(jobProfile);
      checkMandatoryParamsPresent(
          jobProfile,
          ProjectUtil.formatMessage(
              ResponseMessage.Message.DOT_FORMAT, JsonKey.JOB_PROFILE, JsonKey.JOB_NAME),
          JsonKey.JOB_NAME);
      checkMandatoryParamsPresent(
          jobProfile,
          ProjectUtil.formatMessage(
              ResponseMessage.Message.DOT_FORMAT, JsonKey.JOB_PROFILE, JsonKey.ORG_NAME),
          JsonKey.ORG_NAME);

      userAddressValidator.validateAddressField(
          (Map<String, Object>) jobProfile.get(JsonKey.ADDRESS), JsonKey.JOB_PROFILE, operation);
    }
  }

  private void validateJoiningAndJobEndDateFormat(Map<String, Object> reqMap) {
    validateDateParam((String) reqMap.get(JsonKey.JOINING_DATE));
    validateDateParam((String) reqMap.get(JsonKey.END_DATE));
  }
}

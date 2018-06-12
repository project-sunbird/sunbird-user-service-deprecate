package org.sunbird.user.validators;

import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.sunbird.common.models.util.JsonKey;
import org.sunbird.common.models.util.StringFormatter;

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
   * Validates each job profile details.
   *
   * @param userRequest User details.
   * @param operation Type of operation (e.g. CREATE, UPDATE)
   */
  public void validateJobProfile(Map<String, Object> userRequest, String operation) {
    validateListParam(userRequest, JsonKey.JOB_PROFILE);
    List<Map<String, Object>> jobProfiles =
        (List<Map<String, Object>>) userRequest.get(JsonKey.JOB_PROFILE);
    if (CollectionUtils.isNotEmpty(jobProfiles))
      for (int i = 0; i < jobProfiles.size(); i++) {
        validateJobProfileEntity(jobProfiles.get(i), operation);
      }
  }

  /**
   * Validates given job profile details.
   *
   * @param jobProfile Job profile details.
   * @param operation Type of operation (e.g. CREATE, UPDATE)
   */
  public void validateJobProfileEntity(Map<String, Object> jobProfile, String operation) {
    if (MapUtils.isNotEmpty(jobProfile)) {
      validateDeletion(jobProfile, operation, JsonKey.JOB_PROFILE);
      validateJoiningAndJobEndDateFormat(jobProfile);
      checkMandatoryParamsPresent(
          jobProfile,
          StringFormatter.joinByDot(JsonKey.JOB_PROFILE, JsonKey.JOB_NAME),
          JsonKey.JOB_NAME);
      checkMandatoryParamsPresent(
          jobProfile,
          StringFormatter.joinByDot(JsonKey.JOB_PROFILE, JsonKey.ORG_NAME),
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

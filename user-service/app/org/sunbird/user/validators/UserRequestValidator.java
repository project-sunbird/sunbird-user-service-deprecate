package org.sunbird.user.validators;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.sunbird.common.models.util.JsonKey;

/**
 * This class contains method to validate create/update user request.
 *
 * @author Amit Kumar
 */
public class UserRequestValidator extends UserBaseRequestValidator {

  private UserBasicDetailsValidator userBasicDetailsValidator = null;
  private UserExternalIdentityValidator userExternalIdentityValidator = null;
  private UserEducationValidator userEducationValidator = null;
  private UserAddressValidator userAddressValidator = null;
  private UserJobProfileValidator userJobProfileValidator = null;
  private UserBaseRequestValidator userBaseRequestValidator = null;

  public UserRequestValidator() {
    userBaseRequestValidator = new UserBaseRequestValidator();
    userBasicDetailsValidator = new UserBasicDetailsValidator();
    userExternalIdentityValidator = new UserExternalIdentityValidator();
    userEducationValidator = new UserEducationValidator();
    userAddressValidator = new UserAddressValidator();
    userJobProfileValidator = new UserJobProfileValidator();
  }
  /**
   * This method will validate create user data.
   *
   * @param userRequest create user request.
   */
  public void validateCreateUser(Map<String, Object> userRequest) {
    List<String> notAllowedFields = Arrays.asList(JsonKey.REGISTERED_ORG_ID, JsonKey.ROOT_ORG_ID);
    userBaseRequestValidator.checkForFieldsNotAllowed(userRequest, notAllowedFields);
    commonValidation(userRequest, JsonKey.CREATE);
  }

  /**
   * This method will validate update user data.
   *
   * @param userRequest update user request.
   */
  public void validateUpdateUser(Map<String, Object> userRequest) {
    List<String> notAllowedFields =
        Arrays.asList(JsonKey.REGISTERED_ORG_ID, JsonKey.ROOT_ORG_ID, JsonKey.CHANNEL);
    userBaseRequestValidator.checkForFieldsNotAllowed(userRequest, notAllowedFields);
    commonValidation(userRequest, JsonKey.UPDATE);
  }

  private void commonValidation(Map<String, Object> userRequest, String operation) {
    userExternalIdentityValidator.validateExternalIds(userRequest, operation);
    userBasicDetailsValidator.validateBasicDetails(userRequest, operation);
    userAddressValidator.validateAddress(userRequest, operation);
    userEducationValidator.validateEducation(userRequest, operation);
    userJobProfileValidator.validateJobProfile(userRequest, operation);
  }
}

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
public class UserRequestValidator {

  private UserRequestValidator() {}

  public static void validateUser(Map<String, Object> user, String operation) {
    if (JsonKey.CREATE.equalsIgnoreCase(operation)) {
      validateCreateUser(user);
    } else {
      validateUpdateUser(user);
    }
  }

  /**
   * This method will validate create user data.
   *
   * @param userRequest create user request.
   */
  public static void validateCreateUser(Map<String, Object> userRequest) {
    List<String> notAllowedFields = Arrays.asList(JsonKey.REGISTERED_ORG_ID, JsonKey.ROOT_ORG_ID);
    BasicUserValidator.fieldsNotAllowed(notAllowedFields, userRequest);
    UserExternalIdentityValidator.externalIdsValidation(userRequest, JsonKey.CREATE);
    BasicUserValidator.userBasicValidation(userRequest, JsonKey.CREATE);
    BasicUserValidator.phoneValidation(userRequest);
    UserAddressValidator.addressValidation(userRequest);
    UserEducationValidator.educationValidation(userRequest);
    UserJobProfileValidator.jobProfileValidation(userRequest);
  }

  /**
   * This method will validate update user data.
   *
   * @param userRequest update user request.
   */
  public static void validateUpdateUser(Map<String, Object> userRequest) {
    List<String> notAllowedFields = Arrays.asList(JsonKey.REGISTERED_ORG_ID, JsonKey.ROOT_ORG_ID);
    BasicUserValidator.fieldsNotAllowed(notAllowedFields, userRequest);
    UserExternalIdentityValidator.externalIdsValidation(userRequest, JsonKey.UPDATE);
    BasicUserValidator.phoneValidation(userRequest);
    BasicUserValidator.userBasicValidation(userRequest, JsonKey.UPDATE);
    UserAddressValidator.validateUpdateUserAddress(userRequest);
    UserEducationValidator.validateUpdateUserEducation(userRequest);
    UserJobProfileValidator.validateUpdateUserJobProfile(userRequest);
  }
}

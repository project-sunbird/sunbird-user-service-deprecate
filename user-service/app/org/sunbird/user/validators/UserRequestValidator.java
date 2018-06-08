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
  private static void validateCreateUser(Map<String, Object> userRequest) {
    List<String> notAllowedFields = Arrays.asList(JsonKey.REGISTERED_ORG_ID, JsonKey.ROOT_ORG_ID);
    BasicUserValidator.fieldsNotAllowed(notAllowedFields, userRequest);
    commonValidation(userRequest, JsonKey.CREATE);
  }

  private static void commonValidation(Map<String, Object> userRequest, String operation) {
    UserExternalIdentityValidator.externalIdsValidation(userRequest, operation);
    BasicUserValidator.phoneValidation(userRequest);
    BasicUserValidator.userBasicValidation(userRequest, operation);
    UserAddressValidator.addressValidation(userRequest, operation);
    UserEducationValidator.educationValidation(userRequest);
    UserJobProfileValidator.jobProfileValidation(userRequest);
  }

  /**
   * This method will validate update user data.
   *
   * @param userRequest update user request.
   */
  private static void validateUpdateUser(Map<String, Object> userRequest) {
    List<String> notAllowedFields =
        Arrays.asList(JsonKey.REGISTERED_ORG_ID, JsonKey.ROOT_ORG_ID, JsonKey.CHANNEL);
    BasicUserValidator.fieldsNotAllowed(notAllowedFields, userRequest);
    commonValidation(userRequest, JsonKey.UPDATE);
  }
}

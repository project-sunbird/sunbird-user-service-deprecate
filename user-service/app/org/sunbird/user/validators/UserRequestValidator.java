package org.sunbird.user.validators;

import com.mashape.unirest.request.BaseRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.sunbird.common.models.util.JsonKey;
import org.sunbird.common.request.BaseRequestValidator;

/**
 * This class contains method to validate create/update user request.
 *
 * @author Amit Kumar
 */
public class UserRequestValidator extends BaseRequest {

  private BaseRequestValidator baseRequestValidator = null;
  private BasicUserValidator basicUserValidator = null;
  private UserExternalIdentityValidator userExternalIdentityValidator = null;
  private UserEducationValidator userEducationValidator = null;
  private UserAddressValidator userAddressValidator = null;
  private UserJobProfileValidator userJobProfileValidator = null;

  public UserRequestValidator() {
    baseRequestValidator = new BaseRequestValidator();
    basicUserValidator = new BasicUserValidator();
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
    baseRequestValidator.checkForFieldsNotAllowed(userRequest, notAllowedFields);
    commonValidation(userRequest, JsonKey.CREATE);
  }

  private void commonValidation(Map<String, Object> userRequest, String operation) {
    userExternalIdentityValidator.externalIdsValidation(userRequest, operation);
    basicUserValidator.validatePhone(userRequest);
    basicUserValidator.userBasicValidation(userRequest, operation);
    userAddressValidator.validateAddress(userRequest, operation);
    userEducationValidator.validateEducation(userRequest, operation);
    userJobProfileValidator.validateJobProfile(userRequest, operation);
  }

  /**
   * This method will validate update user data.
   *
   * @param userRequest update user request.
   */
  public void validateUpdateUser(Map<String, Object> userRequest) {
    List<String> notAllowedFields =
        Arrays.asList(JsonKey.REGISTERED_ORG_ID, JsonKey.ROOT_ORG_ID, JsonKey.CHANNEL);
    baseRequestValidator.checkForFieldsNotAllowed(userRequest, notAllowedFields);
    commonValidation(userRequest, JsonKey.UPDATE);
  }
}

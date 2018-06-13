package org.sunbird.user.extension.impl;

import java.util.Map;
import org.sunbird.extension.user.UserExtension;
import org.sunbird.user.validators.UserRequestValidator;

/**
 * This class implements UserExtension interface for sunbird.
 *
 * @author Amit Kumar
 */
public class UserProviderSunbirdImpl implements UserExtension {

  UserRequestValidator userRequestValidator = new UserRequestValidator();

  @Override
  public void preCreate(Map<String, Object> userProfileMap) {
    userRequestValidator.validateCreateUser(userProfileMap);
  }

  @Override
  public void create(Map<String, Object> userProfileMap) {}
}

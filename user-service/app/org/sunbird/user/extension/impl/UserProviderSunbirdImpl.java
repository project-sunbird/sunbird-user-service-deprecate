package org.sunbird.user.extension.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.sunbird.common.models.util.JsonKey;
import org.sunbird.common.models.util.ProjectUtil;
import org.sunbird.extension.user.UserExtension;
import org.sunbird.user.models.entity.User;
import org.sunbird.user.utils.Constant;
import org.sunbird.user.utils.UserUtil;
import org.sunbird.user.validators.UserRequestValidator;

/**
 * This class implements UserExtension interface for sunbird.
 *
 * @author Amit Kumar
 */
public class UserProviderSunbirdImpl implements UserExtension {

  private UserRequestValidator userRequestValidator = new UserRequestValidator();
  private ObjectMapper mapper = new ObjectMapper();

  @Override
  public void preCreate(Map<String, Object> userMap) {
    userRequestValidator.validateCreateUser(userMap);
  }

  @Override
  public void create(Map<String, Object> userMap) {
    if (StringUtils.isBlank((String) userMap.get(JsonKey.CHANNEL))) {
      userMap.put(JsonKey.CHANNEL, ProjectUtil.getConfigValue(JsonKey.SUNBIRD_DEFAULT_CHANNEL));
    }
    User user = mapper.convertValue(userMap, User.class);
    UserUtil.checkUserExistOrNot(UserUtil.getLoginId(user.getUserName(), user.getChannel()));
    UserUtil.checkExternalIdUniqueness(
        (List<Map<String, String>>) userMap.get(Constant.EXTERNAL_IDS), "", JsonKey.CREATE);
    UserUtil.checkPhoneUniqueness(user.getPhone(), "", JsonKey.CREATE);
    UserUtil.checkEmailUniqueness(user.getEmail(), "", JsonKey.CREATE);
  }
}

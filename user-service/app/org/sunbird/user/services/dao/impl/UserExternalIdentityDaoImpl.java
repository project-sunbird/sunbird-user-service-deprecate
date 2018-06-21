package org.sunbird.user.services.dao.impl;

import java.util.HashMap;
import java.util.Map;
import org.sunbird.common.models.util.JsonKey;
import org.sunbird.user.models.entity.UserExternalIdentity;
import org.sunbird.user.services.dao.UserExternalIdentityDao;
import org.sunbird.user.utils.Constant;

/**
 * DAO implementation class for user external identity entity.
 *
 * @author Amit Kumar
 */
public class UserExternalIdentityDaoImpl implements UserExternalIdentityDao {

  public static final String TABLE_NAME = "user_external_identity";

  private UserExternalIdentityDaoImpl() {}

  private static UserExternalIdentityDaoImpl userExternalIdentityDaoImpl = null;

  public static synchronized UserExternalIdentityDaoImpl getInstance() {
    if (null == userExternalIdentityDaoImpl) {
      userExternalIdentityDaoImpl = new UserExternalIdentityDaoImpl();
    }
    return userExternalIdentityDaoImpl;
  }

  @Override
  public void create(UserExternalIdentity userExtId) {
    create(KEY_SPACE, TABLE_NAME, userExtId);
  }

  @Override
  public void update(UserExternalIdentity userExtId) {
    update(KEY_SPACE, TABLE_NAME, userExtId);
  }

  @Override
  public void delete(UserExternalIdentity userExtId) {
    Map<String, String> compositeKeyMap = new HashMap<>();
    compositeKeyMap.put(JsonKey.PROVIDER, userExtId.getProvider());
    compositeKeyMap.put(Constant.ID_TYPE, userExtId.getIdType());
    compositeKeyMap.put(JsonKey.EXTERNAL_ID, userExtId.getUserId());
    deleteByCompositeKey(KEY_SPACE, TABLE_NAME, compositeKeyMap);
  }
}

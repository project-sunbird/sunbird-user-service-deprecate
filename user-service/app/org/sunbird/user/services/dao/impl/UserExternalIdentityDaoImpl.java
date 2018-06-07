package org.sunbird.user.services.dao.impl;

import java.util.HashMap;
import java.util.Map;
import org.sunbird.common.models.util.JsonKey;
import org.sunbird.user.models.UserExternalIdentity;
import org.sunbird.user.services.dao.UserExternalIdentityDao;
import org.sunbird.user.utils.Constant;

/** @author Amit Kumar */
public class UserExternalIdentityDaoImpl implements UserExternalIdentityDao {

  public static final String TABLE_NAME = "user_external_identity";

  @Override
  public void create(UserExternalIdentity userExtId) {

    create(KEY_SPACE, TABLE_NAME, mapper.convertValue(userExtId, Map.class));
  }

  @Override
  public void update(UserExternalIdentity userExtId) {

    update(KEY_SPACE, TABLE_NAME, mapper.convertValue(userExtId, Map.class));
  }

  @Override
  public void delete(UserExternalIdentity userExtId) {
    Map<String, String> compositeKeyMap = new HashMap<>();
    compositeKeyMap.put(JsonKey.PROVIDER, userExtId.getProvider());
    compositeKeyMap.put(Constant.ID_TYPE, userExtId.getIdType());
    compositeKeyMap.put(JsonKey.USER_ID, userExtId.getUserId());
    deleteByCompositeKey(KEY_SPACE, TABLE_NAME, compositeKeyMap);
  }
}

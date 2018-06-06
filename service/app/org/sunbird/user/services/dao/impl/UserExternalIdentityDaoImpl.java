package org.sunbird.user.services.dao.impl;

import java.util.Map;
import org.sunbird.user.models.UserExternalIdentity;
import org.sunbird.user.services.dao.UserExternalIdentityDao;

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
  public void delete(String userExtId) {

    delete(KEY_SPACE, TABLE_NAME, userExtId);
  }
}

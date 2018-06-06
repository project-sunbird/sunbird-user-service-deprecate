package org.sunbird.user.services.dao.impl;

import java.util.Map;
import org.sunbird.common.models.response.Response;
import org.sunbird.user.models.UserExternalIdentity;
import org.sunbird.user.services.dao.UserExternalIdentityDao;

/** @author Amit Kumar */
public class UserExternalIdentityDaoImpl implements UserExternalIdentityDao {

  public static final String TABLE_NAME = "user_external_identity";

  @Override
  public Response create(UserExternalIdentity userExtId) {

    return create(KEY_SPACE, TABLE_NAME, mapper.convertValue(userExtId, Map.class));
  }

  @Override
  public Response update(UserExternalIdentity userExtId) {

    return update(KEY_SPACE, TABLE_NAME, mapper.convertValue(userExtId, Map.class));
  }

  @Override
  public void delete(String userExtId) {

    delete(KEY_SPACE, TABLE_NAME, userExtId);
  }
}

package org.sunbird.user.services.dao.impl;

import java.util.Map;
import org.sunbird.common.models.response.Response;
import org.sunbird.user.models.UserOrg;
import org.sunbird.user.services.dao.UserOrgDao;

/** @author Amit Kumar */
public class UserOrgDaoImpl implements UserOrgDao {

  public static final String TABLE_NAME = "user_org";

  @Override
  public Response create(UserOrg userOrg) {

    return create(KEY_SPACE, TABLE_NAME, mapper.convertValue(userOrg, Map.class));
  }

  @Override
  public Response update(UserOrg userOrg) {

    return update(KEY_SPACE, TABLE_NAME, mapper.convertValue(userOrg, Map.class));
  }

  @Override
  public void delete(String userOrgId) {

    delete(KEY_SPACE, TABLE_NAME, userOrgId);
  }
}

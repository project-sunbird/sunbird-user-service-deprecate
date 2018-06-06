package org.sunbird.user.services.dao.impl;

import java.util.Map;
import org.sunbird.common.models.response.Response;
import org.sunbird.user.models.User;
import org.sunbird.user.services.dao.UserDao;

/** @author Amit Kumar */
public class UserDaoImpl implements UserDao {

  public static final String TABLE_NAME = "user";

  @Override
  public Response create(User user) {

    return create(KEY_SPACE, TABLE_NAME, mapper.convertValue(user, Map.class));
  }

  @Override
  public Response update(User user) {

    return update(KEY_SPACE, TABLE_NAME, mapper.convertValue(user, Map.class));
  }
}

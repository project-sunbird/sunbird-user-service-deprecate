package org.sunbird.user.services.dao.impl;

import java.util.Map;
import org.sunbird.user.models.User;
import org.sunbird.user.services.dao.UserDao;

/** @author Amit Kumar */
public class UserDaoImpl implements UserDao {

  public static final String TABLE_NAME = "user";

  @Override
  public void create(User user) {

    create(KEY_SPACE, TABLE_NAME, mapper.convertValue(user, Map.class));
  }

  @Override
  public void update(User user) {

    update(KEY_SPACE, TABLE_NAME, mapper.convertValue(user, Map.class));
  }
}

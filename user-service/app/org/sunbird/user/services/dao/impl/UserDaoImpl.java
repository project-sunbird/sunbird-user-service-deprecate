package org.sunbird.user.services.dao.impl;

import org.sunbird.user.models.entity.User;
import org.sunbird.user.services.dao.UserDao;

/**
 * DAO implementation class for user entity.
 *
 * @author Amit Kumar
 */
public class UserDaoImpl implements UserDao {

  public static final String TABLE_NAME = "user";

  @Override
  public void create(User user) {

    create(KEY_SPACE, TABLE_NAME, user);
  }

  @Override
  public void update(User user) {

    update(KEY_SPACE, TABLE_NAME, user);
  }
}

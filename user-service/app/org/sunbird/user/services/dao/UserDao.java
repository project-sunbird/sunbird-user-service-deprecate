package org.sunbird.user.services.dao;

import org.sunbird.user.models.entity.User;

/**
 * UserDao is an interface for accessing user database entity.
 *
 * @author Amit Kumar
 */
public interface UserDao extends BaseDao {

  /**
   * Creating an user entity in database.
   *
   * @param user User details.
   */
  void create(User user);

  /**
   * Updates user entity database.
   *
   * @param user User details.
   */
  void update(User user);
}

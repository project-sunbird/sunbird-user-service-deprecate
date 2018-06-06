package org.sunbird.user.services.dao;

import org.sunbird.user.models.User;

/**
 * This interface will provide method to interact with user table.
 *
 * @author Amit Kumar
 */
public interface UserDao extends BaseDao {

  /**
   * This method will insert an user entry to DB.
   *
   * @param user User details.
   */
  void create(User user);

  /**
   * This method will update an user entry to DB.
   *
   * @param user User details.
   */
  void update(User user);
}

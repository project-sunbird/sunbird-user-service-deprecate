package org.sunbird.user.services.dao;

import org.sunbird.common.models.response.Response;
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
   * @param user User Object.
   * @return Response
   */
  Response create(User user);

  /**
   * This method will update an user entry to DB.
   *
   * @param user User Object.
   * @return Response
   */
  Response update(User user);
}

package org.sunbird.user.services.dao;

import org.sunbird.common.models.response.Response;
import org.sunbird.user.models.UserExternalIdentity;

/**
 * This interface will provide method to interact with user external identity table.
 *
 * @author Amit Kumar
 */
public interface UserExternalIdentityDao extends BaseDao {

  /**
   * This method will insert an user external identity entry to DB.
   *
   * @param userExtId UserExternalIdentity Object.
   * @return Response
   */
  Response create(UserExternalIdentity userExtId);

  /**
   * This method will update an user external identity entry to DB.
   *
   * @param userExtId UserExternalIdentity Object.
   * @return Response
   */
  Response update(UserExternalIdentity userExtId);

  /**
   * This method will delete the existing user external identity entry from DB.
   *
   * @param userExtId UserExternalIdentity Identifier.
   */
  void delete(String userExtId);
}

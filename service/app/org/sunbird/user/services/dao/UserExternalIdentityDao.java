package org.sunbird.user.services.dao;

import org.sunbird.user.models.UserExternalIdentity;

/**
 * This interface will provide method to interact with user external identity table.
 *
 * @author Amit Kumar
 */
public interface UserExternalIdentityDao extends BaseDao {

  /**
   * This method will insert an external identity entry for given user to DB.
   *
   * @param userExtId UserExternalIdentity details of given user.
   */
  void create(UserExternalIdentity userExtId);

  /**
   * This method will update an external identity entry for given user to DB.
   *
   * @param userExtId UserExternalIdentity details of given user.
   */
  void update(UserExternalIdentity userExtId);

  /**
   * This method will delete the existing external identity entry for given user from DB.
   *
   * @param userExtId UserExternalIdentity Identifier.
   */
  void delete(String userExtId);
}

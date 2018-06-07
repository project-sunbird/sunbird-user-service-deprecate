package org.sunbird.user.services.dao;

import org.sunbird.user.models.entity.UserExternalIdentity;

/**
 * UserExternalIdentityDao is an interface for accessing user external identity database entity.
 *
 * @author Amit Kumar
 */
public interface UserExternalIdentityDao extends BaseDao {

  /**
   * Creating an userExternalIdentity entity for given user in database.
   *
   * @param userExtId UserExternalIdentity details of given user.
   */
  void create(UserExternalIdentity userExtId);

  /**
   * Updates userExternalIdentity entity for given user in database.
   *
   * @param userExtId UserExternalIdentity details of given user.
   */
  void update(UserExternalIdentity userExtId);

  /**
   * Deletes userExternalIdentity entity for given user from database.
   *
   * @param userExtId UserExternalIdentity details of given user.
   */
  void delete(UserExternalIdentity userExtId);
}

package org.sunbird.user.services.dao;

import org.sunbird.user.models.entity.UserExternalIdentity;

/**
 * UserExternalIdentityDao is an interface for accessing user external identity database entity.
 *
 * @author Amit Kumar
 */
public interface UserExternalIdentityDao extends BaseDao {

  /**
   * Creating a user external identity entity for given user in database.
   *
   * @param userExtId User external identity details.
   */
  void create(UserExternalIdentity userExtId);

  /**
   * Updates user external identity entity for given user in database.
   *
   * @param userExtId User external identity details.
   */
  void update(UserExternalIdentity userExtId);

  /**
   * Deletes user external identity entity for given user from database.
   *
   * @param userExtId User external identity details.
   */
  void delete(UserExternalIdentity userExtId);
}

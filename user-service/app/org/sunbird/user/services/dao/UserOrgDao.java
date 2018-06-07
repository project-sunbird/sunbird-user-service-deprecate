package org.sunbird.user.services.dao;

import org.sunbird.user.models.entity.UserOrg;

/**
 * UserOrgDao is an interface for accessing user org database entity.
 *
 * @author Amit Kumar
 */
public interface UserOrgDao extends BaseDao {

  /**
   * Creating a user org entity for given user in database.
   *
   * @param userOrg User org details.
   */
  void create(UserOrg userOrg);

  /**
   * Updates user org entity for given user in database.
   *
   * @param userOrg User org details.
   */
  void update(UserOrg userOrg);

  /**
   * Deletes user org entity for given user from database.
   *
   * @param userOrgId User org Id.
   */
  void delete(String userOrgId);
}

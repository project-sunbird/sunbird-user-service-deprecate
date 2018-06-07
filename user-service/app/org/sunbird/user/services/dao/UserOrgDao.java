package org.sunbird.user.services.dao;

import org.sunbird.user.models.entity.UserOrg;

/**
 * UserOrgDao is an interface for accessing user org database entity.
 *
 * @author Amit Kumar
 */
public interface UserOrgDao extends BaseDao {

  /**
   * Creating an userOrg entity for given user in database.
   *
   * @param userOrg UserOrg details of given user.
   */
  void create(UserOrg userOrg);

  /**
   * Updates userOrg entity for given user in database.
   *
   * @param userOrg UserOrg details of given user.
   */
  void update(UserOrg userOrg);

  /**
   * Deletes userOrg entity for given user from database.
   *
   * @param userOrgId User Org Id.
   */
  void delete(String userOrgId);
}

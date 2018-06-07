package org.sunbird.user.services.dao;

import org.sunbird.user.models.UserOrg;

/**
 * This interface will provide method to interact with user Org table.
 *
 * @author Amit Kumar
 */
public interface UserOrgDao extends BaseDao {

  /**
   * This method will insert an org entry for given user to DB.
   *
   * @param userOrg UserOrg details of given user.
   */
  void create(UserOrg userOrg);

  /**
   * This method will update an org entry for given user to DB.
   *
   * @param userOrg UserOrg details of given user.
   */
  void update(UserOrg userOrg);

  /**
   * This method will delete the existing org entry for given user from DB.
   *
   * @param userOrgId User Org Id.
   */
  void delete(String userOrgId);
}

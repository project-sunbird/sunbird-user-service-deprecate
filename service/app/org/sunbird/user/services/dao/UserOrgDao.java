package org.sunbird.user.services.dao;

import org.sunbird.common.models.response.Response;
import org.sunbird.user.models.UserOrg;

/**
 * This interface will provide method to interact with user Org table.
 *
 * @author Amit Kumar
 */
public interface UserOrgDao extends BaseDao {

  /**
   * This method will insert an user org entry to DB.
   *
   * @param userOrg UserOrg Object.
   * @return Response
   */
  Response create(UserOrg userOrg);

  /**
   * This method will update an user org entry to DB.
   *
   * @param userOrg UserOrg Object.
   * @return Response
   */
  Response update(UserOrg userOrg);

  /**
   * This method will delete the existing user org entry from DB.
   *
   * @param userOrgId User Org Id.
   */
  void delete(String userOrgId);
}

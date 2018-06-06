package org.sunbird.user.services.dao;

import org.sunbird.user.models.Education;

/**
 * This interface will provide method to interact with user education table.
 *
 * @author Amit Kumar
 */
public interface EducationDao extends BaseDao {

  /**
   * This method will insert an education entry for given user to DB.
   *
   * @param education Education details of given user.
   */
  void create(Education education);

  /**
   * This method will update the existing education entry for given user to DB.
   *
   * @param education Education details of given user.
   */
  void update(Education education);

  /**
   * This method will delete the existing education entry for given user from DB.
   *
   * @param educationId Education Id.
   */
  void delete(String educationId);
}

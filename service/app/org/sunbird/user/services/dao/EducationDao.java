package org.sunbird.user.services.dao;

import org.sunbird.common.models.response.Response;
import org.sunbird.user.models.Education;

/**
 * This interface will provide method to interact with user education table.
 *
 * @author Amit Kumar
 */
public interface EducationDao extends BaseDao {

  /**
   * This method will insert an education entry to DB.
   *
   * @param education Education Object.
   * @return Response
   */
  Response create(Education education);

  /**
   * This method will update the existing education entry to DB.
   *
   * @param education Education Object.
   * @return Response
   */
  Response update(Education education);

  /**
   * This method will delete the existing education entry from DB.
   *
   * @param educationId Education Id.
   */
  void delete(String educationId);
}

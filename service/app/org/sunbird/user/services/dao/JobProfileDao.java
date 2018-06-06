package org.sunbird.user.services.dao;

import org.sunbird.common.models.response.Response;
import org.sunbird.user.models.JobProfile;

/**
 * This interface will provide method to interact with user job profile table.
 *
 * @author Amit Kumar
 */
public interface JobProfileDao extends BaseDao {

  /**
   * This method will insert an jobProfile entry to DB.
   *
   * @param jobProfile JobProfile Object.
   * @return Response
   */
  Response create(JobProfile jobProfile);

  /**
   * This method will update an jobProfile entry to DB.
   *
   * @param jobProfile JobProfile Object.
   * @return Response
   */
  Response update(JobProfile jobProfile);

  /**
   * This method will delete the existing jobProfile entry from DB.
   *
   * @param jobProfileId JobProfileId Id.
   */
  void delete(String jobProfileId);
}

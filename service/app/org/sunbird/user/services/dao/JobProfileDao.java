package org.sunbird.user.services.dao;

import org.sunbird.user.models.JobProfile;

/**
 * This interface will provide method to interact with user job profile table.
 *
 * @author Amit Kumar
 */
public interface JobProfileDao extends BaseDao {

  /**
   * This method will insert an jobProfile entry for given user to DB.
   *
   * @param jobProfile JobProfile details of given user.
   */
  void create(JobProfile jobProfile);

  /**
   * This method will update an jobProfile entry for given user to DB.
   *
   * @param jobProfile JobProfile details of given user.
   */
  void update(JobProfile jobProfile);

  /**
   * This method will delete the existing jobProfile entry for given user from DB.
   *
   * @param jobProfileId JobProfileId Id.
   */
  void delete(String jobProfileId);
}

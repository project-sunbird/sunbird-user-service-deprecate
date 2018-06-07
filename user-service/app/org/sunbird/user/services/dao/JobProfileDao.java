package org.sunbird.user.services.dao;

import org.sunbird.user.models.entity.JobProfile;

/**
 * JobProfileDao is an interface for accessing user job profile database entity.
 *
 * @author Amit Kumar
 */
public interface JobProfileDao extends BaseDao {

  /**
   * Creating a job Profile entity for given user in database.
   *
   * @param job Profile JobProfile details.
   */
  void create(JobProfile jobProfile);

  /**
   * Updates job Profile entity for given user in database.
   *
   * @param job Profile JobProfile details.
   */
  void update(JobProfile jobProfile);

  /**
   * Deletes job Profile entity for given user from database.
   *
   * @param jobProfileId Job profile id.
   */
  void delete(String jobProfileId);
}

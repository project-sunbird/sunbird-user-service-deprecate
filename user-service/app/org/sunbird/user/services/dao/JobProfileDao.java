package org.sunbird.user.services.dao;

import org.sunbird.user.models.entity.JobProfile;

/**
 * JobProfileDao is an interface for accessing user job profile database entity.
 *
 * @author Amit Kumar
 */
public interface JobProfileDao extends BaseDao {

  /**
   * Creating an jobProfile entity for given user in database.
   *
   * @param jobProfile JobProfile details of given user.
   */
  void create(JobProfile jobProfile);

  /**
   * Updates jobProfile entity for given user in database.
   *
   * @param jobProfile JobProfile details of given user.
   */
  void update(JobProfile jobProfile);

  /**
   * Deletes jobProfile entity for given user from database.
   *
   * @param jobProfileId JobProfileId Id.
   */
  void delete(String jobProfileId);
}

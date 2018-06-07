package org.sunbird.user.services.dao;

import org.sunbird.user.models.entity.Education;

/**
 * EducationDao is an interface for accessing user education database entity.
 *
 * @author Amit Kumar
 */
public interface EducationDao extends BaseDao {

  /**
   * Creating an education entity for given user in database.
   *
   * @param education Education details of given user.
   */
  void create(Education education);

  /**
   * Updates education entity for given user in database.
   *
   * @param education Education details of given user.
   */
  void update(Education education);

  /**
   * Deletes education entity for given user from database.
   *
   * @param educationId Identifier for education entity.
   */
  void delete(String educationId);
}

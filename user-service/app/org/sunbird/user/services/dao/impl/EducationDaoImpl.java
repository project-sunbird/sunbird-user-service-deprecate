package org.sunbird.user.services.dao.impl;

import org.sunbird.user.models.entity.Education;
import org.sunbird.user.services.dao.EducationDao;

/**
 * DAO implementation class for user education entity.
 *
 * @author Amit Kumar
 */
public class EducationDaoImpl implements EducationDao {

  public static final String TABLE_NAME = "education";

  @Override
  public void create(Education education) {
    create(KEY_SPACE, TABLE_NAME, education);
  }

  @Override
  public void update(Education education) {
    update(KEY_SPACE, TABLE_NAME, education);
  }

  @Override
  public void delete(String educationId) {
    deleteById(KEY_SPACE, TABLE_NAME, educationId);
  }
}

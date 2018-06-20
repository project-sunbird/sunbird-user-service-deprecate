package org.sunbird.user.services.dao.impl;

import org.sunbird.user.models.entity.JobProfile;
import org.sunbird.user.services.dao.JobProfileDao;

/**
 * DAO implementation class for user job profile entity.
 *
 * @author Amit Kumar
 */
public class JobProfileDaoImpl implements JobProfileDao {

  public static final String TABLE_NAME = "job_profile";

  private JobProfileDaoImpl() {}

  private static JobProfileDaoImpl jobProfileDaoImpl = null;

  public static synchronized JobProfileDaoImpl getInstance() {
    if (null == jobProfileDaoImpl) {
      jobProfileDaoImpl = new JobProfileDaoImpl();
    }
    return jobProfileDaoImpl;
  }

  @Override
  public void create(JobProfile jobProfile) {
    create(KEY_SPACE, TABLE_NAME, jobProfile);
  }

  @Override
  public void update(JobProfile jobProfile) {
    update(KEY_SPACE, TABLE_NAME, jobProfile);
  }

  @Override
  public void delete(String jobProfileId) {
    deleteById(KEY_SPACE, TABLE_NAME, jobProfileId);
  }
}

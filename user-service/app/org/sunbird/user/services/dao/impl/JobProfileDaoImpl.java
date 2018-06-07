package org.sunbird.user.services.dao.impl;

import java.util.Map;
import org.sunbird.user.models.JobProfile;
import org.sunbird.user.services.dao.JobProfileDao;

/** @author Amit Kumar */
public class JobProfileDaoImpl implements JobProfileDao {

  public static final String TABLE_NAME = "job_profile";

  @Override
  public void create(JobProfile jobProfile) {

    create(KEY_SPACE, TABLE_NAME, mapper.convertValue(jobProfile, Map.class));
  }

  @Override
  public void update(JobProfile jobProfile) {

    update(KEY_SPACE, TABLE_NAME, mapper.convertValue(jobProfile, Map.class));
  }

  @Override
  public void delete(String jobProfileId) {

    delete(KEY_SPACE, TABLE_NAME, jobProfileId);
  }
}

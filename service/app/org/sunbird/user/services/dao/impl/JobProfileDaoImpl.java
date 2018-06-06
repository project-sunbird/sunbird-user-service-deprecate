package org.sunbird.user.services.dao.impl;

import java.util.Map;
import org.sunbird.common.models.response.Response;
import org.sunbird.user.models.JobProfile;
import org.sunbird.user.services.dao.JobProfileDao;

/** @author Amit Kumar */
public class JobProfileDaoImpl implements JobProfileDao {

  public static final String TABLE_NAME = "job_profile";

  @Override
  public Response create(JobProfile jobProfile) {

    return create(KEY_SPACE, TABLE_NAME, mapper.convertValue(jobProfile, Map.class));
  }

  @Override
  public Response update(JobProfile jobProfile) {

    return update(KEY_SPACE, TABLE_NAME, mapper.convertValue(jobProfile, Map.class));
  }

  @Override
  public void delete(String jobProfileId) {

    delete(KEY_SPACE, TABLE_NAME, jobProfileId);
  }
}

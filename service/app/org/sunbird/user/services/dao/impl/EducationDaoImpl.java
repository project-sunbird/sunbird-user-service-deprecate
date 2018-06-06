package org.sunbird.user.services.dao.impl;

import java.util.Map;
import org.sunbird.common.models.response.Response;
import org.sunbird.user.models.Education;
import org.sunbird.user.services.dao.EducationDao;

/** @author Amit Kumar */
public class EducationDaoImpl implements EducationDao {

  public static final String TABLE_NAME = "education";

  @Override
  public Response create(Education education) {

    return create(KEY_SPACE, TABLE_NAME, mapper.convertValue(education, Map.class));
  }

  @Override
  public Response update(Education education) {

    return update(KEY_SPACE, TABLE_NAME, mapper.convertValue(education, Map.class));
  }

  @Override
  public void delete(String educationId) {

    delete(KEY_SPACE, TABLE_NAME, educationId);
  }
}
